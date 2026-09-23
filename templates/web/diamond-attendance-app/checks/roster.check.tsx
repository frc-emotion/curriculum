// React Native Testing Library 14 renders asynchronously (React 19 concurrent
// rendering), so every render() has to be awaited before the screen can be read.
import { render, screen, userEvent } from '@testing-library/react-native';
import type { ComponentType } from 'react';
import * as CardModule from '../src/components/StudentCard';
import { roster } from '../src/data/roster';
import { assert, assertEqual, component } from './_support';

const StudentCard = () =>
  component(3, 'src/components/StudentCard.tsx', 'StudentCard', CardModule as Record<string, unknown>);

const ada = roster[0]!;

describe('STEP 3: StudentCard', () => {
  it('shows the student name, grade and subteam', async () => {
    const Card = StudentCard() as ComponentType<Record<string, unknown>>;
    await render(<Card student={ada} status="absent" onPress={() => {}} />);

    assert(
      screen.queryByText(ada.name) !== null,
      'STEP 3: the card should show the student name.',
    );
    assert(
      screen.queryByText(/Grade 11/) !== null,
      'STEP 3: the card should show the grade and subteam, in the shape `Grade 11 · Software`.',
    );
    assert(
      screen.queryByText(/Software/) !== null,
      'STEP 3: the card should show the subteam.',
    );
  });

  it('shows the status it is given', async () => {
    const Card = StudentCard() as ComponentType<Record<string, unknown>>;
    await render(<Card student={ada} status="excused" onPress={() => {}} />);

    assert(
      screen.queryByText(/excused/i) !== null,
      'STEP 3: the card was handed status "excused", so that is what it should show. The card '
        + 'displays the status it is given; it does not decide it.',
    );
  });

  it('has a testID so the checks can find it', async () => {
    const Card = StudentCard() as ComponentType<Record<string, unknown>>;
    await render(<Card student={ada} status="absent" onPress={() => {}} />);

    assert(
      screen.queryByTestId('student-card') !== null,
      'STEP 3: add testID="student-card" to the card\'s Pressable. The checks use it to find '
        + 'your cards, the same way a screen reader would.',
    );
  });

  it('calls onPress with the student id when tapped', async () => {
    const Card = StudentCard() as ComponentType<Record<string, unknown>>;
    const pressed: number[] = [];
    await render(<Card student={ada} status="absent" onPress={(id: number) => pressed.push(id)} />);

    const user = userEvent.setup();
    await user.press(screen.getByTestId('student-card'));

    assertEqual(
      pressed.length,
      1,
      'STEP 3: tapping the card should call onPress exactly once.',
    );
    assertEqual(
      pressed[0],
      ada.id,
      "STEP 3: onPress should be called with this student's id.",
    );
  });
});
