// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// FILE:        src/studentUtils.ts
// STEPS HERE:  2
// GUIDE:       GUIDE_URL  (section "Web Iron")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: the project compiles in strict mode, the API data is validated
//              before use, and a failed request is handled cleanly.
// ============================================================
//
// Your Copper functions, with types on them. The behaviour is identical — you
// already proved you can write these. What is new is saying, out loud and in
// the signature, what goes in and what comes out.
//
// Pay attention to which of them TypeScript starts arguing with. `findStudentById`
// cannot promise to return a Student, because it might not find one. That is
// not TypeScript being difficult; that is a bug you were carrying at Copper and
// handling by luck.

// STEP 2: Port your Copper functions and type them
// WHAT:       Copy your seven functions from
//             students/<your-username>/web/copper/src/studentUtils.js into this
//             file, and give every parameter and every return value a type.
//             The names and behaviour stay exactly the same:
//               getStudentNames(students)               -> string[]
//               getStudentsBySubteam(students, subteam) -> Student[]
//               findStudentById(students, id)           -> Student | undefined
//               countByGrade(students)                  -> Record<number, number>
//               getRegularAttendees(students, minimum)  -> Student[]
//               formatStudent(student)                  -> string
//               getContactEmail(student)                -> string
//             Import the Student type from './types.js'. Note the `.js` — that
//             is not a typo; see the note at the bottom of this file.
//             No `any`, anywhere.
// WHY:        Reusing your own work is the point. You will notice two things:
//             most of it just works, and one or two signatures turn out to have
//             been lying. `findStudentById` returning `Student | undefined` is
//             the compiler telling you about a crash you had not hit yet.
// CONCEPTS:   Typed parameters, return types, union types, Record<K, V>,
//             importing types, strict null checks
// READ:       Guide > Web Iron > Resources #2 and #3
// CHECKED BY: studentUtils.check.ts, sourceScan.check.ts (no `any`)
// DONE WHEN:  all seven behave exactly as they did at Copper, and
//             `npm run typecheck` is clean.

// ------------------------------------------------------------
// Why imports end in .js
//
// This project is ES modules, so import paths need a file extension, and the
// extension you write is the one the *output* would have — `.js`, even though
// the file on disk is `.ts`. It looks wrong. It is correct, and both Node and
// your editor agree.
//
//   import type { Student } from './types.js';
// ------------------------------------------------------------

// This line makes the file a module while it is still empty, so the rest of the
// project can import it. Once you have exported something real, you can delete it.
export {};
