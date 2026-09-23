// ============================================================
// `npm run check` runs this.
//
// Three things, always all three, so you see everything left in one go:
//   1. the rank checks
//   2. the TypeScript compiler
//   3. the linter
//
// Part of the grader. Don't edit.
// ============================================================
import { spawnSync } from 'node:child_process';
import { existsSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import path from 'node:path';

const projectRoot = path.dirname(path.dirname(fileURLToPath(import.meta.url)));

function runNodeTool(relativeEntry, args) {
  const entry = path.join(projectRoot, relativeEntry);
  if (!existsSync(entry)) {
    console.error(`Couldn't find ${relativeEntry}. Run \`npm install\` first.`);
    return 1;
  }
  const result = spawnSync(process.execPath, [entry, ...args], {
    cwd: projectRoot,
    stdio: 'inherit',
  });
  return result.status ?? 1;
}

function banner(title) {
  console.log('------------------------------------------------------------');
  console.log(`  ${title}`);
  console.log('------------------------------------------------------------');
}

const testStatus = runNodeTool('node_modules/vitest/vitest.mjs', [
  'run',
  '--reporter=./checks/step-reporter.js',
]);

banner('TypeScript (STEPS 1 and 9)');
const typeStatus = runNodeTool('node_modules/typescript/bin/tsc', ['--noEmit']);
if (typeStatus === 0) {
  console.log('  Types are clean. Strict mode and all.');
} else {
  console.log('');
  console.log('  Every line above is a type error: the file, the line, and what it expected.');
  console.log('  Errors in checks/typeFixture.ts mean your Student or Subteam type is missing');
  console.log('  or the wrong shape — that is STEP 1.');
}

banner('Linter (STEP 9)');
const lintStatus = runNodeTool('node_modules/eslint/bin/eslint.js', ['src']);
if (lintStatus === 0) {
  console.log('  Linter is happy. No `any`, no silenced errors.');
} else {
  console.log('');
  console.log('  STEP 9: fix the rules listed above, from the top down.');
}
console.log('');

process.exit(testStatus === 0 && typeStatus === 0 && lintStatus === 0 ? 0 : 1);
