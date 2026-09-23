import AsyncStorage from '@react-native-async-storage/async-storage';
import { ATTENDANCE_STORAGE_KEY, type AttendanceStatus } from '../src/data/roster';
import * as storage from '../src/storage/attendanceStorage';
import { assert, assertDeepEqual, fn } from './_support';

const FILE = 'src/storage/attendanceStorage.ts';
const MODULE = storage as unknown as Record<string, unknown>;

function loadAttendance() {
  return (fn(9, FILE, 'loadAttendance', MODULE) as () => Promise<unknown>)();
}

function saveAttendance(value: Record<number, AttendanceStatus>) {
  return (fn(9, FILE, 'saveAttendance', MODULE) as (v: unknown) => Promise<void>)(value);
}

beforeEach(async () => {
  await AsyncStorage.clear();
});

describe('STEP 9: saving and loading attendance', () => {
  it('gives back an empty object when nothing has been saved', async () => {
    const loaded = await loadAttendance();
    assertDeepEqual(
      loaded,
      {},
      'STEP 9: on a fresh install there is nothing stored, so loadAttendance should resolve to '
        + 'an empty object. Returning null or throwing here means the app crashes on first launch, '
        + 'which is the worst kind of crash — the user cannot get past it.',
    );
  });

  it('gives back exactly what was saved', async () => {
    const attendance: Record<number, AttendanceStatus> = { 1: 'present', 4: 'excused' };
    await saveAttendance(attendance);

    assertDeepEqual(
      await loadAttendance(),
      attendance,
      'STEP 9: saving then loading should give back the same attendance. AsyncStorage only holds '
        + 'strings, so this is a JSON.stringify on the way in and a JSON.parse on the way out.',
    );
  });

  it('uses the shared storage key', async () => {
    await saveAttendance({ 2: 'present' });
    const raw = await AsyncStorage.getItem(ATTENDANCE_STORAGE_KEY);
    assert(
      raw !== null,
      `STEP 9: nothing was written under ATTENDANCE_STORAGE_KEY ("${ATTENDANCE_STORAGE_KEY}"). `
        + 'Use the constant from src/data/roster.ts rather than typing a key of your own, so the '
        + 'loader and the saver can never disagree.',
    );
  });

  it('survives rubbish in storage instead of crashing', async () => {
    // Resolve the function first, so a missing one reports as its own STEP 9
    // failure rather than being wrapped in this test's message.
    const load = fn(9, FILE, 'loadAttendance', MODULE) as () => Promise<unknown>;
    await AsyncStorage.setItem(ATTENDANCE_STORAGE_KEY, 'this is not json{{{');

    let loaded: unknown;
    try {
      loaded = await load();
    } catch (error) {
      throw new Error(
        'STEP 9: loadAttendance threw when the stored value was unreadable ('
          + String((error as Error).message)
          + '). A phone that ran out of battery mid-save can leave exactly this behind. Catch it '
          + 'and start fresh with {} — a lost register is annoying, an app that will not open is '
          + 'worse.',
      );
    }

    assertDeepEqual(
      loaded,
      {},
      'STEP 9: when the stored value cannot be parsed, loadAttendance should fall back to an '
        + 'empty object.',
    );
  });

  it('overwrites cleanly on the second save', async () => {
    await saveAttendance({ 1: 'present' });
    await saveAttendance({ 2: 'excused' });

    assertDeepEqual(
      await loadAttendance(),
      { 2: 'excused' },
      'STEP 9: the second save should replace the first, not merge with it.',
    );
  });
});
