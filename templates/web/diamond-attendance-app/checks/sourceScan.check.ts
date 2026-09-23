/* ============================================================
 * Checks the things a rendered test can't see.
 * Comments and quoted text are stripped first, so the STEP comments describing
 * what to write never count as the code itself.
 * ============================================================ */
import { readFileSync } from 'node:fs';
import { assert } from './_support';

function strip(source: string): string {
  return source
    .replace(/\/\*[\s\S]*?\*\//g, ' ')
    .replace(/(^|[^:])\/\/[^\n]*/g, '$1 ')
    .replace(/`(?:\\.|[^`\\])*`/g, '``')
    .replace(/'(?:\\.|[^'\\])*'/g, "''")
    .replace(/"(?:\\.|[^"\\])*"/g, '""');
}

const FILES = [
  'App.tsx',
  'src/navigation/types.ts',
  'src/navigation/RootNavigator.tsx',
  'src/components/StudentCard.tsx',
  'src/screens/RosterScreen.tsx',
  'src/screens/StudentDetailScreen.tsx',
  'src/screens/SettingsScreen.tsx',
  'src/storage/attendanceStorage.ts',
  'src/storage/AttendanceContext.tsx',
  'src/theme/ThemeContext.tsx',
];

const code = Object.fromEntries(
  FILES.map((path) => [path, strip(readFileSync(path, 'utf8'))]),
);

describe('STEP 1: the navigation types are real types', () => {
  it('declares RootStackParamList with both screens', () => {
    const types = code['src/navigation/types.ts'] ?? '';
    assert(
      /RootStackParamList/.test(types),
      'STEP 1: I could not find `RootStackParamList` in src/navigation/types.ts.',
    );
    assert(
      /\bRoster\b/.test(types) && /\bStudentDetail\b/.test(types),
      'STEP 1: RootStackParamList should name both screens — Roster and StudentDetail.',
    );
    assert(
      /studentId/.test(types),
      'STEP 1: StudentDetail takes a `studentId` param, and the param list is where you say so. '
        + 'Without it, navigate() cannot be type-checked.',
    );
  });

  it('does not use `any` anywhere', () => {
    for (const [path, source] of Object.entries(code)) {
      assert(
        !/(^|[^\w.$])any\b/.test(source),
        `STEP 1: found \`any\` in ${path}. An \`any\` in the navigation types switches off the `
          + `one thing they are for — catching a wrong or missing route param at compile time.`,
      );
    }
  });

  it('does not silence type errors', () => {
    for (const path of FILES) {
      assert(
        !/@ts-(ignore|expect-error|nocheck)/.test(readFileSync(path, 'utf8')),
        `STEP 1: found a @ts- comment in ${path}. Silencing an error does not fix it.`,
      );
    }
  });
});

describe('STEP 4: the roster uses a FlatList', () => {
  it('renders with FlatList rather than .map', () => {
    const rosterScreen = code['src/screens/RosterScreen.tsx'] ?? '';
    assert(
      /FlatList/.test(rosterScreen),
      'STEP 4: I could not find a FlatList in RosterScreen. `.map` builds every row up front; a '
        + 'FlatList only builds the ones on screen.',
    );
    assert(
      /keyExtractor/.test(rosterScreen),
      'STEP 4: a FlatList needs a keyExtractor, the same way a web list needs a key.',
    );
  });
});

describe('STEP 3 and 6: both styling approaches appear', () => {
  it('the card uses StyleSheet', () => {
    const card = code['src/components/StudentCard.tsx'] ?? '';
    assert(
      /StyleSheet\s*\.\s*create/.test(card),
      'STEP 3: StudentCard should be styled with StyleSheet.create. The detail screen uses '
        + 'NativeWind instead, on purpose — you need to be able to read both, because '
        + 'nautilus-frontend has both.',
    );
  });

  it('the detail screen uses NativeWind className', () => {
    const detail = code['src/screens/StudentDetailScreen.tsx'] ?? '';
    assert(
      /className\s*=/.test(detail),
      'STEP 6: StudentDetailScreen should be styled with NativeWind `className` strings rather '
        + 'than StyleSheet.',
    );
  });
});

describe('STEP 9: storage goes through AsyncStorage', () => {
  it('attendanceStorage uses AsyncStorage and the shared key', () => {
    const storage = code['src/storage/attendanceStorage.ts'] ?? '';
    assert(
      /AsyncStorage/.test(storage),
      'STEP 9: I could not find AsyncStorage in src/storage/attendanceStorage.ts.',
    );
    assert(
      /ATTENDANCE_STORAGE_KEY/.test(storage),
      'STEP 9: use ATTENDANCE_STORAGE_KEY from src/data/roster.ts rather than typing the key '
        + 'twice — a loader and a saver that disagree about the key fail silently.',
    );
  });

  it('only the storage module talks to AsyncStorage', () => {
    for (const [path, source] of Object.entries(code)) {
      if (path === 'src/storage/attendanceStorage.ts') {
        continue;
      }
      assert(
        !/AsyncStorage/.test(source),
        `STEP 9: ${path} talks to AsyncStorage directly. Keep storage in one file, the same way `
          + `api.ts owned the network at Iron — then there is one place to change when the shape `
          + `of what you save changes.`,
      );
    }
  });
});
