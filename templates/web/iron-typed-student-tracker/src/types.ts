// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// FILE:        src/types.ts
// STEPS HERE:  1
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (section "Web Iron")
// RUN:         npm start
// PASSES WHEN: the project compiles in strict mode, the API data is validated
//              before use, and a failed request is handled cleanly.
// ============================================================
//
// One file that describes the shape of everything else. When somebody joins the
// project and asks "what IS a student?", this is the answer.

// STEP 1: Describe a student
// WHAT:       Export two types:
//               `Subteam` — a union of exactly the four subteam names that
//                 appear in the data: 'Software', 'Mechanical', 'Electrical',
//                 'Outreach'.
//               `Student` — an object type with:
//                 id               number
//                 name             string
//                 grade            number
//                 subteam          Subteam
//                 meetingsAttended number
//                 contact          OPTIONAL, and when present it has an
//                                  optional email and an optional phone, both
//                                  strings
//             Then annotate the array in src/data/students.ts as `Student[]`.
// WHY:        A union type is the difference between a typo you find now and a
//             typo you find in a match. Write `subteam: string` and
//             'Softwear' is fine by TypeScript; write the union and your editor
//             underlines it as you type.
//             The optional `contact` is what makes the compiler force you to
//             handle the missing case — the bug you fixed by hand at Copper.
// CONCEPTS:   Type aliases, union types, optional properties (?), object types
// READ:       "Web Iron" tab > Skills required
// DONE WHEN:  annotating the data array as Student[] compiles cleanly, and
//             changing one subteam to 'Softwear' makes it fail.

// This line makes the file a module while it is still empty, so the rest of the
// project can import it. Once you have exported something real, you can delete it.
export {};
