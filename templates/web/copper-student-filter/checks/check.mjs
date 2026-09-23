// ============================================================
// `npm run check` runs this.
//
// It runs the rank checks AND the linter, and always runs both, so you see
// everything that is left in one go rather than one thing at a time.
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

const testStatus = runNodeTool('node_modules/vitest/vitest.mjs', [
  'run',
  '--reporter=./checks/step-reporter.js',
]);

console.log('------------------------------------------------------------');
console.log('  Linter (STEP 9)');
console.log('------------------------------------------------------------');

const lintStatus = runNodeTool('node_modules/eslint/bin/eslint.js', ['src']);

if (lintStatus === 0) {
  console.log('  STEP 9: clean. No `var`, no `==`, no unused variables.');
} else {
  console.log('');
  console.log('  STEP 9: the linter is unhappy. Each line above gives the file, the line');
  console.log('  number and the rule. Fix them from the top down.');
}
console.log('');

process.exit(testStatus === 0 && lintStatus === 0 ? 0 : 1);
