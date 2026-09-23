// ============================================================
// Checks the things a behaviour test can't see: which language features you
// used. Comments and text inside quotes are stripped first, so the STEP
// comments describing what to write never count as the code itself.
// ============================================================
import { readFileSync } from 'node:fs';
import { describe, expect, it } from 'vitest';

function stripCommentsAndStrings(source) {
  return source
    .replace(/\/\*[\s\S]*?\*\//g, ' ')
    .replace(/(^|[^:])\/\/[^\n]*/g, '$1 ')
    .replace(/`(?:\\.|[^`\\])*`/g, '``')
    .replace(/'(?:\\.|[^'\\])*'/g, "''")
    .replace(/"(?:\\.|[^"\\])*"/g, '""');
}

const code = stripCommentsAndStrings(readFileSync('src/studentUtils.js', 'utf8'));

function bodyOf(name) {
  const declaration = new RegExp(
    `(?:function\\s+${name}\\s*\\(|(?:const|let)\\s+${name}\\s*=\\s*(?:function\\s*)?\\()`,
  );
  const match = declaration.exec(code);
  if (!match) {
    return '';
  }
  // Grab the parameter list plus a generous chunk of what follows.
  return code.slice(match.index, match.index + 600);
}

describe('STEP 6: formatStudent destructures its parameter', () => {
  it('has a destructured parameter', () => {
    const declaration = bodyOf('formatStudent');
    expect(/\(\s*\{[^}]*\}/.test(declaration),
      'STEP 6: expected formatStudent to destructure its parameter, like '
      + '`formatStudent({ name, grade, subteam })`. Reading student.name inside the body works, '
      + 'but this step is about making the function\'s needs visible in its signature.').toBe(true);
  });
});

describe('STEP 7: getContactEmail uses ?. and ??', () => {
  it('uses optional chaining', () => {
    expect(bodyOf('getContactEmail').includes('?.'),
      'STEP 7: expected optional chaining (?.) in getContactEmail. It is what stops a missing '
      + '`contact` from throwing.').toBe(true);
  });

  it('uses nullish coalescing', () => {
    expect(bodyOf('getContactEmail').includes('??'),
      'STEP 7: expected the nullish coalescing operator (??) in getContactEmail, to supply the '
      + 'fallback when there is no email.').toBe(true);
  });
});
