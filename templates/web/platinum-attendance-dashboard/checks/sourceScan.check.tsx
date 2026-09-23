/* ============================================================
 * Checks the things a rendered test can't see.
 * Comments and quoted text are stripped first.
 * ============================================================ */
import { readFileSync } from 'node:fs';
import { describe, expect, it } from 'vitest';

function strip(source: string): string {
  return source
    .replace(/\/\*[\s\S]*?\*\//g, ' ')
    .replace(/(^|[^:])\/\/[^\n]*/g, '$1 ')
    .replace(/`(?:\\.|[^`\\])*`/g, '``')
    .replace(/'(?:\\.|[^'\\])*'/g, "''")
    .replace(/"(?:\\.|[^"\\])*"/g, '""');
}

const FILES = [
  'src/App.tsx',
  'src/hooks/useRoster.ts',
  'src/context/ThemeContext.tsx',
  'src/components/AttendanceCard.tsx',
  'src/components/AttendanceList.tsx',
];

const sources = Object.fromEntries(
  FILES.map((path) => [path, strip(readFileSync(path, 'utf8'))]),
);

describe('STEP 5: the fetch effect cleans up after itself', () => {
  it('useRoster either returns a cleanup function or uses TanStack Query', () => {
    const hook = sources['src/hooks/useRoster.ts'] ?? '';

    // After step 8 the effect is gone entirely, which is also fine — TanStack
    // Query handles the race for you.
    const usesQuery = /useQuery/.test(hook);
    const hasCleanup = /useEffect\s*\([\s\S]*?return\s*(\(\s*\)\s*=>|function)/.test(hook)
      || /AbortController/.test(hook);

    expect(usesQuery || hasCleanup,
      'STEP 5: your useEffect in useRoster should return a cleanup function (or use an '
      + 'AbortController), so a response that arrives after the component has moved on is '
      + 'ignored instead of being written into state.').toBe(true);
  });
});

describe('STEP 6: context, not prop drilling', () => {
  it('ThemeContext uses createContext and useContext', () => {
    const context = sources['src/context/ThemeContext.tsx'] ?? '';
    expect(/createContext/.test(context),
      'STEP 6: I could not find createContext in src/context/ThemeContext.tsx.').toBe(true);
    expect(/useContext/.test(context),
      'STEP 6: I could not find useContext in src/context/ThemeContext.tsx. Your useTheme hook '
      + 'should read the context and throw if there is no provider above it.').toBe(true);
  });
});

describe('STEP 4: the counts are calculated, not stored', () => {
  it('no state variable is named after a count', () => {
    const app = sources['src/App.tsx'] ?? '';
    const suspicious = /useState[^;]{0,80}\b(count|counts|presentCount|total|summary)\b/i.test(app)
      || /\b(setCount|setCounts|setPresentCount|setTotal|setSummary)\b/.test(app);
    expect(suspicious,
      'STEP 4: it looks like the summary counts are being kept in state. Anything you can work '
      + 'out from the statuses you already have should be worked out during render — a stored '
      + 'copy is a second source of truth, and it will go stale.').toBe(false);
  });
});

describe('Types are on', () => {
  it('nothing is typed `any`', () => {
    for (const [path, code] of Object.entries(sources)) {
      expect(/(^|[^\w.$])any\b/.test(code),
        `Found \`any\` in ${path}. Type the error as \`string | null\` and the data as `
        + `\`Student[]\` — the component needs something it can actually show.`).toBe(false);
    }
  });

  it('no errors are silenced', () => {
    for (const path of FILES) {
      expect(/@ts-(ignore|expect-error|nocheck)/.test(readFileSync(path, 'utf8')),
        `Found a @ts- comment in ${path}. Silencing an error does not fix it.`).toBe(false);
    }
  });
});
