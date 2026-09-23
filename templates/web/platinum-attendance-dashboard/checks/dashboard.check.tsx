import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterAll, afterEach, beforeAll, describe, expect, it } from 'vitest';
import App from '../src/App.tsx';
import * as rosterHook from '../src/hooks/useRoster.ts';
import { ROSTER, respondSlowly, respondWithError, server } from './handlers.ts';

beforeAll(() => server.listen({ onUnhandledRequest: 'error' }));
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

function statusButtons(): HTMLElement[] {
  return Array.from(document.querySelectorAll('li button'));
}

function pageText(): string {
  return document.body.textContent ?? '';
}

async function renderLoaded() {
  render(<App />);
  await waitFor(
    () => {
      if (statusButtons().length === 0) {
        throw new Error(
          'STEP 7: no student cards rendered. App should pass the students from useRoster, plus ' +
            'your statuses, down to AttendanceList — and steps 1 and 7 need to be done first.',
        );
      }
    },
    { timeout: 3000 },
  );
}

describe('STEP 1: useRoster exists', () => {
  it('is exported from src/hooks/useRoster.ts', () => {
    expect(typeof (rosterHook as Record<string, unknown>).useRoster,
      'STEP 1: I couldn\'t find an exported function called `useRoster` in '
      + 'src/hooks/useRoster.ts.').toBe('function');
  });
});

describe('STEP 2: the loading state', () => {
  it('says something while the roster is on its way', async () => {
    respondSlowly();
    render(<App />);

    await waitFor(() => {
      expect(/loading|loading…|fetching|one moment/i.test(pageText()),
        'STEP 2: while useRoster is loading, the dashboard should say so. Right now the page '
        + `shows: "${pageText().trim().slice(0, 120)}"`).toBe(true);
    });
  });

  it('replaces the loading message once the roster arrives', async () => {
    await renderLoaded();
    expect(/loading/i.test(pageText()),
      'STEP 2: the loading message should disappear once the students are in.').toBe(false);
  });
});

describe('STEP 3: the error state', () => {
  it('shows a message when the request fails', async () => {
    respondWithError();
    render(<App />);

    await waitFor(() => {
      expect(/error|could ?n.t|failed|unable|problem|try again/i.test(pageText()),
        'STEP 3: when the roster request fails, show the user a message. Right now the page '
        + `shows: "${pageText().trim().slice(0, 120)}"`).toBe(true);
    }, { timeout: 3000 });
  });

  it('does not show an empty list alongside the error', async () => {
    respondWithError();
    render(<App />);
    await waitFor(() => {
      expect(/error|could ?n.t|failed|unable|problem|try again/i.test(pageText()),
        'STEP 3: the dashboard should show an error message when the roster request fails.')
        .toBe(true);
    }, { timeout: 3000 });

    expect(statusButtons().length,
      'STEP 3: when loading fails there is no roster, so no cards should render.').toBe(0);
  });
});

describe('STEP 4: the summary counts', () => {
  it('shows "N of M present"', async () => {
    await renderLoaded();
    expect(new RegExp(`0\\s+of\\s+${ROSTER.length}\\s+present`, 'i').test(pageText()),
      `STEP 4: the summary should read "0 of ${ROSTER.length} present" before anyone is marked `
      + `in. Right now the page shows: "${pageText().trim().slice(0, 160)}"`).toBe(true);
  });

  it('updates the instant a card is toggled', async () => {
    await renderLoaded();
    const first = statusButtons()[0] as HTMLElement;

    // absent -> excused -> present
    await userEvent.click(first);
    await userEvent.click(statusButtons()[0] as HTMLElement);

    await waitFor(() => {
      expect(new RegExp(`1\\s+of\\s+${ROSTER.length}\\s+present`, 'i').test(pageText()),
        'STEP 4: after marking one student present the summary should say "1 of '
        + `${ROSTER.length} present". If it is stuck at 0, the count is being stored in state `
        + 'rather than worked out during render — calculate it and the problem disappears.')
        .toBe(true);
    });
  });
});

describe('STEP 7: the roster renders and toggles', () => {
  it('renders every student from the server', async () => {
    await renderLoaded();
    expect(statusButtons().length,
      `STEP 7: the server returns ${ROSTER.length} students, so that many cards should render.`)
      .toBe(ROSTER.length);
    expect(screen.queryByText('Ada Nwosu'),
      'STEP 7: the cards should show the students that came back from useRoster.')
      .toBeInTheDocument();
  });

  it('toggling one card leaves the others alone', async () => {
    await renderLoaded();
    await userEvent.click(statusButtons()[0] as HTMLElement);

    const others = statusButtons().slice(1);
    expect(others.every((button) => /absent/i.test(button.textContent ?? '')),
      'STEP 7: toggling one student changed others too. Copy the statuses and replace one entry.')
      .toBe(true);
  });
});
