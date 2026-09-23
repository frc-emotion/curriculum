// ============================================================
// A Jest reporter that prints your remaining steps as a tidy checklist instead
// of a wall of stack traces.
//
// Part of the grader. Don't edit.
// ============================================================
const ANSI = new RegExp(String.fromCharCode(27) + '\\[[0-9;]*m', 'g');

function firstLine(text) {
  return (
    String(text ?? '')
      .replace(ANSI, '')
      .split('\n')
      .map((line) => line.trim())
      .filter((line) => line.length > 0 && !line.startsWith('at '))[0] ?? ''
  );
}

class StepReporter {
  onRunComplete(_contexts, results) {
    const failures = [];

    for (const file of results.testResults ?? []) {
      if (file.testExecError) {
        failures.push(firstLine(file.testExecError.message));
      }
      for (const test of file.testResults ?? []) {
        if (test.status !== 'failed') {
          continue;
        }
        const messages = test.failureMessages ?? [];
        if (messages.length === 0) {
          failures.push(`${test.title} failed`);
        }
        for (const message of messages) {
          failures.push(firstLine(message).replace(/^Error:\s*/, ''));
        }
      }
    }

    const unique = [...new Set(failures.filter(Boolean))].sort();

    console.log('');
    console.log('============================================================');
    if (unique.length === 0 && results.numFailedTests === 0) {
      console.log('  All rank checks passed. Nice work.');
      console.log('');
      console.log('  The checker cannot hold a phone, so you still need step 7:');
      console.log('  screenshots or a recording from a real device or emulator.');
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

module.exports = StepReporter;
