import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { createElement } from 'react';
import { describe, expect, it, vi } from 'vitest';
import { attendanceList } from './_support.tsx';
import { DEFAULT_STATUS, roster, type AttendanceStatus } from '../src/data/roster.ts';

function allAbsent(): Record<number, AttendanceStatus> {
  return Object.fromEntries(roster.map((student) => [student.id, DEFAULT_STATUS]));
}

function renderList(props: Record<string, unknown>) {
  const List = attendanceList();
  return render(createElement(List, props));
}

describe('STEP 3: AttendanceList renders every student', () => {
  it('renders one card per student', () => {
    renderList({ students: roster, statuses: allAbsent(), onToggle: () => {} });
    expect(screen.queryAllByRole('button').length,
      `STEP 3: the roster has ${roster.length} students, so there should be that many status `
      + 'buttons.').toBe(roster.length);
  });

  it('shows each student by name', () => {
    renderList({ students: roster, statuses: allAbsent(), onToggle: () => {} });
    expect(screen.queryByText('Ada Nwosu'), 'STEP 3: Ada should be in the list.').toBeInTheDocument();
    expect(screen.queryByText('Lena Voss'), 'STEP 3: Lena should be in the list.').toBeInTheDocument();
  });

  it('gives each card the right student\'s status', () => {
    const statuses = allAbsent();
    statuses[1] = 'present';
    statuses[2] = 'excused';
    renderList({ students: roster.slice(0, 3), statuses, onToggle: () => {} });

    const buttons = screen.queryAllByRole('button');
    expect(buttons[0]?.textContent?.toLowerCase(),
      'STEP 3: student 1 is marked present, so their card should say so. Look up each status by '
      + 'the student\'s id.').toContain('present');
    expect(buttons[1]?.textContent?.toLowerCase(),
      'STEP 3: student 2 is marked excused.').toContain('excused');
    expect(buttons[2]?.textContent?.toLowerCase(),
      'STEP 3: student 3 has no status set, so they are absent.').toContain('absent');
  });

  it('renders only the students it is given', () => {
    renderList({ students: roster.slice(0, 3), statuses: allAbsent(), onToggle: () => {} });
    expect(screen.queryAllByRole('button').length,
      'STEP 3: render the `students` prop, not the whole roster. The filter at step 6 depends on '
      + 'this.').toBe(3);
  });

  it('passes clicks up with the right id', async () => {
    const onToggle = vi.fn();
    renderList({ students: roster.slice(0, 3), statuses: allAbsent(), onToggle });

    await userEvent.click(screen.queryAllByRole('button')[1] as HTMLElement);

    expect(onToggle.mock.calls[0]?.[0],
      'STEP 3: clicking the second card should report student id 2. Pass onToggle straight down '
      + 'to each card.').toBe(2);
  });

  it('uses a list element', () => {
    const { container } = renderList({
      students: roster, statuses: allAbsent(), onToggle: () => {},
    });
    expect(container.querySelector('ul'),
      'STEP 3: render the cards inside a <ul>, one <li> each. A list of things should be a list '
      + 'element — screen readers announce how many items there are.').toBeTruthy();
  });
});
