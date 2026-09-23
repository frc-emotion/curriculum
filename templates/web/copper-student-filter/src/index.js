// ============================================================
// RANK:        Web Copper: Student Filter
// FILE:        src/index.js
// STEPS HERE:  8
// GUIDE:       GUIDE_URL  (section "Web Copper")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: all checks pass, there is no `var` or `==`, and the original
//              students array is never modified.
// ============================================================
//
// This is what `npm start` runs. It is your window into your own code — the
// place you print things to see whether they look right.

import { students } from './students.js';

// STEP 8: Print the results of every function, with labels
// WHAT:       Import your functions from './studentUtils.js' and call each one,
//             printing the result with a label so a human can tell what they are
//             looking at. Something like:
//               Names: Ada Nwosu, Bo Tran, ...
//               Software subteam: 5 students
//             Then run `npm start` and read the output.
// WHY:        Checks tell you whether code is right. Printing tells you what it
//             is actually doing, which is a different and more useful thing when
//             you're stuck. Getting comfortable with "print it and look" now
//             will save you hours at every later rank.
// CONCEPTS:   ES module imports, console.log, reading your own output
// READ:       Guide > Web Copper > Resources #1
// CHECKED BY: your reviewer
// DONE WHEN:  `npm start` prints a labelled result for all seven functions
//             without crashing.

console.log(`Loaded ${students.length} students.`);
