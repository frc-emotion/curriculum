import { render, screen, userEvent, waitFor } from '@testing-library/react-native';
import App from '../App';
import { roster } from '../src/data/roster';
import { assert } from './_support';

const ada = roster[0]!;

async function openTheApp() {
  await render(<App />);
  await waitFor(
    () => {
      assert(
        screen.queryAllByTestId('student-card').length > 0,
        'STEP 4: no student cards rendered. The app should open on the roster screen, showing '
          + 'every student in a FlatList. Steps 1 to 4 need to be working before this one can.',
      );
    },
    { timeout: 5000 },
  );
}

describe('STEP 4: the roster screen', () => {
  it('renders the roster', async () => {
    await openTheApp();
    const rendered = screen.queryAllByTestId('student-card').length;
    // A FlatList only builds the rows near the screen, and in a test there is no
    // screen to measure — so it stops at its default first batch of 10 rather
    // than all 12. That is the FlatList doing its job, not a bug.
    assert(
      rendered >= 10,
      `STEP 4: only ${rendered} cards rendered. The FlatList should be given the whole roster `
        + `(${roster.length} students) as its data.`,
    );
  });

  it('shows students by name', async () => {
    await openTheApp();
    assert(
      screen.queryByText(ada.name) !== null,
      'STEP 4: the first student should be on screen.',
    );
  });
});

describe('STEP 5 and 6: tapping through to the detail screen', () => {
  it('opens the detail screen for the student that was tapped', async () => {
    await openTheApp();
    const user = userEvent.setup();

    await user.press(screen.getAllByTestId('student-card')[0]!);

    await waitFor(
      () => {
        assert(
          screen.queryByTestId('student-detail') !== null,
          'STEP 5: tapping a card should navigate to StudentDetail. Add '
            + 'testID="student-detail" to the outer View of that screen so the checks can see '
            + 'it, and make sure the card calls navigation.navigate with the studentId.',
        );
      },
      { timeout: 5000 },
    );

    assert(
      screen.queryAllByText(ada.name).length > 0,
      'STEP 6: the detail screen should show the student whose card was tapped. Read '
        + 'route.params.studentId and look them up — if the wrong student appears, the param is '
        + 'not being passed or not being read.',
    );
  });

  it('carries the right student through for a different card', async () => {
    await openTheApp();
    const user = userEvent.setup();
    const third = roster[2]!;

    await user.press(screen.getAllByTestId('student-card')[2]!);

    await waitFor(
      () => {
        assert(
          screen.queryByTestId('student-detail') !== null,
          'STEP 5: tapping the third card should open the detail screen too.',
        );
      },
      { timeout: 5000 },
    );

    assert(
      screen.queryAllByText(third.name).length > 0,
      `STEP 6: tapping the third card should show ${third.name}. Pass the tapped student's id as `
        + 'the route param rather than a fixed one.',
    );
  });
});
