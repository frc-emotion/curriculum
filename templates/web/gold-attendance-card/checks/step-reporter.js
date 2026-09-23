// ============================================================
// A Vitest reporter that prints your remaining steps as a tidy checklist
// instead of a wall of stack traces.
//
// Part of the grader. Don't edit.
// ============================================================
function firstLine(text) {
  const lines = String(text ?? '')
    .split('\n')
    .map((line) => line.trim())
    .filter((line) => line.length > 0);
  const message = lines[0] ?? String(text ?? '');
  // Vitest appends its own ": expected false to be true" tail to a custom assertion
  // message. Trim the LAST one — the message itself may legitimately say "expected".
  const tail = message.lastIndexOf(': expected ');
  return tail > 20 ? message.slice(0, tail) : message;
}

export default class StepReporter {
  onTestRunEnd(testModules = [], unhandledErrors = []) {
    const failures = [];

    for (const testModule of testModules) {
      for (const test of testModule.children.allTests()) {
        const result = test.result();
        if (result?.state !== 'failed') {
          continue;
        }
        const errors = result.errors ?? [];
        if (errors.length === 0) {
          failures.push(`${test.name} failed`);
        }
        for (const error of errors) {
          failures.push(firstLine(error.message));
        }
      }
    }

    for (const error of unhandledErrors) {
      failures.push(firstLine(error.message));
    }

    const unique = [...new Set(failures)].sort();

    console.log('');
    console.log('============================================================');
    if (unique.length === 0) {
      console.log('  All rank checks passed. Nice work.');
    } else {
      console.log(`  ${unique.length} check(s) still to go:`);
      console.log('');
      for (const failure of unique) {
        console.log(`    - ${failure}`);
      }
      console.log('');
      console.log('  Work on the LOWEST step number first. Later steps build on it.');
    }
    console.log('============================================================');
  }
}
