// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// FILE:        src/groupBy.ts
// STEPS HERE:  3
// GUIDE:       GUIDE_URL  (section "Web Iron")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: the project compiles in strict mode, the API data is validated
//              before use, and a failed request is handled cleanly.
// ============================================================

// STEP 3: Write a generic groupBy
// WHAT:       Export a function with exactly this signature:
//               export function groupBy<T>(
//                 items: readonly T[],
//                 getKey: (item: T) => string,
//               ): Record<string, T[]>
//             It returns an object whose keys come from getKey, each holding the
//             items that produced that key, in their original order.
//               groupBy(students, (s) => s.subteam)
//                 -> { Software: [...], Mechanical: [...], ... }
// WHY:        The `<T>` is the whole lesson. Without it you would write one
//             groupBy for students, another for matches, another for scouting
//             records — or one that takes `any[]` and gives back `any`, which
//             is the same as having no types at all. A generic says "I work on
//             a list of anything, and whatever you put in is what comes out."
//             Hover over a call to it once it compiles. TypeScript knows the
//             values are Students.
// CONCEPTS:   Generics, type parameters, higher-order functions, Record<K, V>,
//             readonly arrays
// READ:       Guide > Web Iron > Resources #3
// CHECKED BY: groupBy.check.ts
// DONE WHEN:  grouping students by subteam gives four keys, grouping by grade
//             (as a string) gives four, and an empty array gives {}.

// This line makes the file a module while it is still empty, so the rest of the
// project can import it. Once you have exported something real, you can delete it.
export {};
