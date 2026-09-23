import { render, screen, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, expect, it } from 'vitest';
import App from '../src/App.tsx';
import { roster } from '../src/data/roster.ts';

function statusButtons(): HTMLElement[] {
  // Status buttons are the ones inside the list (step 3 puts each card in an <li>).
  // Picking them by text would also catch the "Present" filter button.
  return Array.from(document.querySelectorAll('li button'));
}

/**
 * The nth status button, with a step-numbered message when there isn't one —
 * otherwise a missing card shows up as an unhelpful "cannot read textContent".
 */
function requireFilter(label: RegExp, step: number): HTMLElement {
  const button = filterButton(label);
  if (!button) {
    throw new Error(
      `STEP ${step}: I couldn't find a filter button matching ${label}. Step 6 needs four of ` +
        'them: All, Present, Absent and Excused.',
    );
  }
  return button;
}

function statusButton(index: number, step: number): HTMLElement {
  const button = statusButtons()[index];
  if (!button) {
    throw new Error(
      `STEP ${step}: no student cards rendered, so there is nothing to click yet. ` +
        'Get steps 3 and 4 working first — App has to pass the roster and the statuses down ' +
        'to AttendanceList.',
    );
  }
  return button;
}

function filterButton(label: RegExp): HTMLElement | undefined {
  // Filter buttons are the ones outside the card list.
  return screen
    .queryAllByRole('button')
    .filter((button) => !button.closest('li'))
    .find((button) => label.test(button.textContent?.trim() ?? ''));
}

describe('STEP 4: App owns the attendance state', () => {
  it('starts every student absent', () => {
    render(<App />);
    const buttons = statusButtons();
    expect(buttons.length,
      `STEP 4: all ${roster.length} students should render. Pass the roster and your statuses `
      + 'down to AttendanceList.').toBe(roster.length);
    expect(buttons.every((button) => /absent/i.test(button.textContent ?? '')),
      'STEP 4: everyone starts on DEFAULT_STATUS, which is "absent".').toBe(true);
  });
});

describe('STEP 5: toggling cycles the status, immutably', () => {
  it('cycles one student through all three statuses', async () => {
    render(<App />);
    const first = statusButton(0, 5);

    expect(first.textContent?.toLowerCase(), 'STEP 5: the first card starts absent.')
      .toContain('absent');

    await userEvent.click(first);
    expect(statusButtons()[0]?.textContent?.toLowerCase(),
      'STEP 5: absent should move to excused (the cycle is present -> absent -> excused -> '
      + 'present). If the text did not change at all, you probably changed the existing state '
      + 'object instead of making a new one — React compares objects by identity, so it saw no '
      + 'change and did not redraw.').toContain('excused');

    await userEvent.click(statusButton(0, 5));
    expect(statusButtons()[0]?.textContent?.toLowerCase(),
      'STEP 5: excused should move to present.').toContain('present');

    await userEvent.click(statusButton(0, 5));
    expect(statusButtons()[0]?.textContent?.toLowerCase(),
      'STEP 5: present should move back to absent.').toContain('absent');
  });

  it('changes only the student that was clicked', async () => {
    render(<App />);
    await userEvent.click(statusButton(0, 5));

    const others = statusButtons().slice(1);
    expect(others.every((button) => /absent/i.test(button.textContent ?? '')),
      'STEP 5: clicking one card changed other cards too. Copy the old statuses and replace one '
      + 'entry, rather than reassigning the whole object.').toBe(true);
  });
});

describe('STEP 6: the filter', () => {
  it('has filter buttons', () => {
    render(<App />);
    expect(filterButton(/^all$/i),
      'STEP 6: there should be an "All" filter button.').toBeTruthy();
    expect(filterButton(/^present$/i),
      'STEP 6: there should be a "Present" filter button.').toBeTruthy();
  });

  it('shows only matching students', async () => {
    render(<App />);
    // Mark the first student present: absent -> excused -> present.
    await userEvent.click(statusButton(0, 6));
    await userEvent.click(statusButton(0, 6));

    await userEvent.click(requireFilter(/^present$/i, 6));

    expect(statusButtons().length,
      'STEP 6: exactly one student is present, so the list should show one card.').toBe(1);
    expect(screen.queryByText(roster[0]?.name ?? ''),
      'STEP 6: the present student should be the one still showing.').toBeInTheDocument();
  });

  it('brings everyone back with All', async () => {
    render(<App />);
    await userEvent.click(requireFilter(/^present$/i, 6));
    await userEvent.click(requireFilter(/^all$/i, 6));

    expect(statusButtons().length,
      'STEP 6: "All" should show the whole roster again.').toBe(roster.length);
  });

  it('marks the active filter with aria-pressed', async () => {
    render(<App />);
    await userEvent.click(requireFilter(/^present$/i, 6));

    expect(filterButton(/^present$/i)?.getAttribute('aria-pressed'),
      'STEP 6: the active filter button should have aria-pressed="true". The CSS uses it to '
      + 'highlight the button, and screen readers use it to say which one is on.').toBe('true');
  });
});

describe('STEP 7: the empty state', () => {
  it('says something when nobody matches', async () => {
    render(<App />);
    await userEvent.click(requireFilter(/^present$/i, 6));

    expect(statusButtons().length,
      'STEP 7: nobody is present on a fresh page, so no cards should show.').toBe(0);

    const page = document.body;
    const text = within(page).queryAllByText(/\w/);
    const hasMessage = text.some((node) =>
      /no\s|nobody|none|empty|nothing/i.test(node.textContent ?? ''));
    expect(hasMessage,
      'STEP 7: when the filter matches nobody, show a short message instead of a blank gap — '
      + 'something like "Nobody is marked present yet." A blank space leaves the user wondering '
      + 'whether the app is broken.').toBe(true);
  });
});
