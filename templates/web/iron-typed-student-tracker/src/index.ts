// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// STEPS HERE:  7, 8, 9
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Web track > Iron tab)
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
// DONE WHEN:  `npm start` with no network prints one friendly line and exits
//             without a stack trace.

console.log(`Loaded ${students.length} students from the local roster.`);

// STEP 9: No escape hatches (applies to every file in src/)
// WHAT:       No `any` and no `@ts-ignore` anywhere. Run `npx tsc --noEmit` and fix
//             every error it lists until it prints nothing.
// WHY:        `any` switches TypeScript off for that value; `unknown` is the honest
//             escape hatch because it makes you check before you use it. Silencing
//             an error does not fix it.
// CONCEPTS:   Strict mode, unknown vs any, reading compiler errors
// DONE WHEN:  `npx tsc --noEmit` prints no errors, and a search of src/ finds no
//             `any` and no `@ts-ignore`.
