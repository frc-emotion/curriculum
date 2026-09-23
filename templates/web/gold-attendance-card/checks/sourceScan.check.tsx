/* ============================================================
 * Checks the things a rendered test can't see.
 *
 * Comments and quoted text are stripped first, so the STEP comments describing
 * what to write never count as the code itself.
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
  'src/components/AttendanceCard.tsx',
  'src/components/AttendanceList.tsx',
];

const sources = FILES.map((path) => ({ path, code: strip(readFileSync(path, 'utf8')) }));

describe('STEP 3: list keys', () => {
  it('the list uses a key', () => {
    const list = sources.find((s) => s.path === 'src/components/AttendanceList.tsx')?.code ?? '';
    expect(/key\s*=/.test(list),
      'STEP 3: I could not find a `key` prop in AttendanceList. React needs one per item to tell '
      + 'them apart between renders.').toBe(true);
  });

  it('the key is not the array index', () => {
    const list = sources.find((s) => s.path === 'src/components/AttendanceList.tsx')?.code ?? '';
    expect(/key\s*=\s*\{\s*(index|i|idx)\s*\}/.test(list),
      'STEP 3: the key is the array index. Use the student\'s id instead. With an index key, '
      + '"item 2" means a different student once the list is filtered, and React reuses the '
      + 'wrong card.').toBe(false);
  });
});

describe('STEP 9: types are on', () => {
  it('nothing is typed `any`', () => {
    for (const { path, code } of sources) {
      expect(/(^|[^\w.$])any\b/.test(code),
        `STEP 1: found \`any\` in ${path}. Props are a contract between components — write the `
        + `type out so the compiler can hold both sides to it.`).toBe(false);
    }
  });

  it('no errors are silenced', () => {
    for (const path of FILES) {
      expect(/@ts-(ignore|expect-error|nocheck)/.test(readFileSync(path, 'utf8')),
        `STEP 1: found a @ts- comment in ${path}. Silencing an error does not fix it.`).toBe(false);
    }
  });
});
