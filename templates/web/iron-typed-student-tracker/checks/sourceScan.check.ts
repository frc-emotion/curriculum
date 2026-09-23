/* ============================================================
 * Checks the things a behaviour test can't see.
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
  'src/types.ts',
  'src/studentUtils.ts',
  'src/groupBy.ts',
  'src/api.ts',
  'src/index.ts',
  'src/data/students.ts',
];

const sources = FILES.map((path) => ({ path, code: strip(readFileSync(path, 'utf8')) }));

describe('STEP 9: TypeScript is switched on everywhere', () => {
  it('nothing is typed `any`', () => {
    for (const { path, code } of sources) {
      expect(/(^|[^\w.$])any\b/.test(code),
        `STEP 9: found \`any\` in ${path}. \`any\` switches the type checker off for that value, `
        + `which is the one thing this rank is about not doing. If you genuinely don't know the `
        + `shape, \`unknown\` is the honest choice — it makes you check before you use it.`)
        .toBe(false);
    }
  });

  it('no errors are silenced with @ts-ignore or @ts-expect-error', () => {
    for (const { path } of sources) {
      const raw = readFileSync(path, 'utf8');
      expect(/@ts-(ignore|expect-error|nocheck)/.test(raw),
        `STEP 9: found a @ts- comment in ${path}. Silencing an error does not fix it, and the next `
        + `person to read this file will believe the type.`).toBe(false);
    }
  });
});

describe('STEP 5: the API response is validated, not cast', () => {
  it('api.ts uses zod', () => {
    const api = sources.find((source) => source.path === 'src/api.ts')?.code ?? '';
    expect(/\bz\s*\.|from\s*''zod''|zod/.test(api),
      'STEP 5: I could not find zod being used in src/api.ts. The response has to be checked at '
      + 'runtime, because that is the only moment the real data exists.').toBe(true);
  });

  it('api.ts does not cast the response to Student[]', () => {
    const api = sources.find((source) => source.path === 'src/api.ts')?.code ?? '';
    expect(/as\s+Student\s*\[\s*\]/.test(api),
      'STEP 5: src/api.ts casts the response with `as Student[]`. A cast is a promise to the '
      + 'compiler, not a check on the data — the server can still send anything. Parse it instead.')
      .toBe(false);
  });
});

describe('STEP 1: the Subteam union is a union, not a string', () => {
  it('types.ts lists the four subteams', () => {
    const types = readFileSync('src/types.ts', 'utf8');
    for (const subteam of ['Software', 'Mechanical', 'Electrical', 'Outreach']) {
      expect(types.includes(subteam),
        `STEP 1: I could not find '${subteam}' in src/types.ts. Subteam should be a union of the `
        + `four names that actually appear, so a typo is a compile error.`).toBe(true);
    }
  });
});
