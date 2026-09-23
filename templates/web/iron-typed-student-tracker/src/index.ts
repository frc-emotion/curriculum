// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// FILE:        src/index.ts
// STEPS HERE:  7, 8
// GUIDE:       GUIDE_URL  (section "Web Iron")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: the project compiles in strict mode, the API data is validated
//              before use, and a failed request is handled cleanly.
// ============================================================

import { students } from './data/students.js';

// STEP 7: Put it together
// WHAT:       Print, with labels:
//               - a few of your studentUtils results on the local `students`
//                 array, and
//               - the result of grouping students by subteam.
//             Then call fetchStudents against a real public URL and print how
//             many students came back. This one works and returns data in the
//             right shape:
//               https://jsonplaceholder.typicode.com/users
//             ...except it doesn't. Those are users, not students, so your zod
//             schema will reject them — which is the schema doing its job.
//             Point fetchStudents at it anyway and watch it refuse. Then decide
//             what you want your program to print when that happens.
// WHY:        Seeing validation reject real data from a real server is worth
//             more than any amount of reading about it. The endpoint is
//             genuinely fine; it is just not YOUR shape, which is exactly the
//             situation zod exists for.
// CONCEPTS:   Top-level await, async main functions, composing your own modules
// READ:       Guide > Web Iron > Resources #4
// CHECKED BY: your reviewer
// DONE WHEN:  `npm start` prints labelled results and does not crash.

// STEP 8: Handle the failure like a person, not a stack trace
// WHAT:       Wrap the network call so that when it fails — bad URL, no wifi,
//             rejected data — your program prints one clear line explaining what
//             went wrong, and exits normally. Test it by turning your wifi off,
//             or by pointing it at http://localhost:9/nope.
// WHY:        This is the difference between an app that says "Couldn't load the
//             roster — check your connection" and one that dumps a wall of red
//             text at a fourteen-year-old. You will build the visible version of
//             this at Platinum; this is the same idea with no UI in the way.
// CONCEPTS:   try/catch, error messages for humans, graceful degradation
// READ:       Guide > Web Iron > Resources #4
// CHECKED BY: your reviewer (show them the output with the network off)
// DONE WHEN:  `npm start` with no network prints one friendly line and exits
//             without a stack trace.

console.log(`Loaded ${students.length} students from the local roster.`);
