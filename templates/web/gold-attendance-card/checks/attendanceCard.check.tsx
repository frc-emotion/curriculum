import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { createElement } from 'react';
import { describe, expect, it, vi } from 'vitest';
import { attendanceCard } from './_support.tsx';
import type { Student } from '../src/data/roster.ts';

const ada: Student = { id: 1, name: 'Ada Nwosu', grade: 11, subteam: 'Software' };

function renderCard(props: Record<string, unknown>) {
  const Card = attendanceCard();
  return render(createElement(Card, props));
}

describe('STEP 1: AttendanceCard shows one student', () => {
  it('shows the student name', () => {
    renderCard({ student: ada, status: 'absent', onToggle: () => {} });
    expect(screen.queryByText('Ada Nwosu'),
      'STEP 1: the card should show the student\'s name.').toBeInTheDocument();
  });

  it('shows the grade and subteam', () => {
    renderCard({ student: ada, status: 'absent', onToggle: () => {} });
    expect(screen.queryByText(/Grade 11/),
      'STEP 1: the card should show the grade, in the shape `Grade 11 · Software`.')
      .toBeInTheDocument();
    expect(screen.queryByText(/Software/),
      'STEP 1: the card should show the subteam.').toBeInTheDocument();
  });

  it('shows the current status on a button', () => {
    renderCard({ student: ada, status: 'present', onToggle: () => {} });
    const button = screen.queryByRole('button');
    expect(button,
      'STEP 1: the card needs a <button> for changing the status.').toBeInTheDocument();
    expect(button?.textContent?.toLowerCase(),
      'STEP 1: the button\'s text should be the current status, so you can see it at a glance.')
      .toContain('present');
  });

  it('shows whatever status it is given, rather than deciding for itself', () => {
    renderCard({ student: ada, status: 'excused', onToggle: () => {} });
    expect(screen.queryByRole('button')?.textContent?.toLowerCase(),
      'STEP 1: the card was given status "excused", so that is what it should show. The card does '
      + 'not own the status — it displays the one it is handed.').toContain('excused');
  });
});

describe('STEP 2: AttendanceCard reports clicks', () => {
  it('calls onToggle with the student id', async () => {
    const onToggle = vi.fn();
    renderCard({ student: ada, status: 'absent', onToggle });

    await userEvent.click(screen.getByRole('button'));

    expect(onToggle.mock.calls.length,
      'STEP 2: clicking the button should call onToggle exactly once.').toBe(1);
    expect(onToggle.mock.calls[0]?.[0],
      'STEP 2: onToggle should be called with THIS student\'s id (1), so whoever owns the state '
      + 'knows which student to change.').toBe(1);
  });

  it('reports the right id for a different student', async () => {
    const onToggle = vi.fn();
    const bo: Student = { id: 2, name: 'Bo Tran', grade: 9, subteam: 'Mechanical' };
    renderCard({ student: bo, status: 'absent', onToggle });

    await userEvent.click(screen.getByRole('button'));

    expect(onToggle.mock.calls[0]?.[0],
      'STEP 2: pass the id from the `student` prop, not a hard-coded number.').toBe(2);
  });

  it('does not call onToggle before anybody clicks', () => {
    const onToggle = vi.fn();
    renderCard({ student: ada, status: 'absent', onToggle });
    expect(onToggle.mock.calls.length,
      'STEP 2: onToggle ran during render. In JSX, `onClick={onToggle(id)}` CALLS the function '
      + 'immediately — you want `onClick={() => onToggle(id)}`, which is a function to call later.')
      .toBe(0);
  });
});
