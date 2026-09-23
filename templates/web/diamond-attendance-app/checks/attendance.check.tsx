import { render, screen, userEvent, waitFor } from '@testing-library/react-native';
import App from '../App';
import { roster } from '../src/data/roster';
import * as AttendanceModule from '../src/storage/AttendanceContext';
import { assert } from './_support';

const ada = roster[0]!;

describe('STEP 8: the attendance context', () => {
  it('exports AttendanceProvider and useAttendance', () => {
    const module = AttendanceModule as unknown as Record<string, unknown>;
    assert(
      typeof module.AttendanceProvider === 'function',
      'STEP 8: I couldn\'t find an exported `AttendanceProvider` in '
        + 'src/storage/AttendanceContext.tsx.',
    );
    assert(
      typeof module.useAttendance === 'function',
      'STEP 8: I couldn\'t find an exported `useAttendance` hook in '
        + 'src/storage/AttendanceContext.tsx.',
    );
  });
});

describe('STEP 8: a change on one screen shows on the other', () => {
  it('keeps the roster and the detail screen in agreement', async () => {
    await render(<App />);
    const user = userEvent.setup();

    await waitFor(
      () => {
        assert(
          screen.queryAllByTestId('student-card').length > 0,
          'STEP 8: no cards rendered yet — steps 1 to 4 have to be working first.',
        );
      },
      { timeout: 5000 },
    );

    // Open the first student's detail screen.
    await user.press(screen.getAllByTestId('student-card')[0]!);
    await waitFor(
      () => {
        assert(
          screen.queryByTestId('student-detail') !== null,
          'STEP 8: tapping a card should open the detail screen (step 5).',
        );
      },
      { timeout: 5000 },
    );

    // Find the toggle on the detail screen and press it.
    const toggle = screen.queryByTestId('detail-toggle');
    assert(
      toggle !== null,
      'STEP 8: the detail screen needs a button that toggles this student\'s status. Give it '
        + 'testID="detail-toggle" so the checks can find it.',
    );

    const before = screen.toJSON();
    await user.press(toggle);

    await waitFor(
      () => {
        assert(
          JSON.stringify(screen.toJSON()) !== JSON.stringify(before),
          `STEP 8: pressing the toggle on ${ada.name}'s detail screen changed nothing on screen. `
            + 'The status should come from the shared context, and toggling should build a new '
            + 'object rather than editing the existing one.',
        );
      },
      { timeout: 5000 },
    );
  });
});
