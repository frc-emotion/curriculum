// ============================================================
// RANK:        Web Gold: Attendance Card
// FILE:        src/components/AttendanceList.tsx
// STEPS HERE:  3
// GUIDE:       GUIDE_URL  (section "Web Gold")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: the cards toggle correctly, the filter works, there are no key
//              warnings in the console, and state is never mutated directly.
// ============================================================
//
// Many students, many cards. Same idea as `.map` at Copper, except what comes
// out is elements instead of strings.
//
// Imports you will need (add them yourself):
//   import type { AttendanceStatus, Student } from '../data/roster.ts';
//   import AttendanceCard from './AttendanceCard.tsx';
//
// Export it as the default export or as a named export called
// `AttendanceList` — the checks accept either.

// STEP 3: Build the list
// WHAT:       Write a component taking these typed props:
//               students   Student[]
//               statuses   Record<number, AttendanceStatus>
//               onToggle   (studentId: number) => void
//             Render a <ul className="card-list"> with one <li> per student,
//             each holding an AttendanceCard for that student, given that
//             student's status from `statuses`.
//             Each item needs a `key`, and the key must be the STUDENT'S ID —
//             not the array index.
// WHY:        `key` is how React tells one item in a list from another between
//             renders. With the index as the key, "the second one" is a
//             different student the moment the list is filtered or reordered,
//             and React reuses the wrong card's state. It looks fine until it
//             suddenly isn't, and it is one of the hardest React bugs to spot
//             because nothing errors.
//             Open the browser console while you work. React warns loudly when
//             a key is missing, and those warnings are on the "Passes when"
//             list.
// CONCEPTS:   Rendering lists, .map in JSX, the key prop, why index keys break,
//             passing props through
// READ:       Guide > Web Gold > Resources #3
// CHECKED BY: attendanceList.check.tsx, sourceScan.check.tsx (no index keys)
// DONE WHEN:  all 12 students render, and the browser console has no key
//             warnings in it.

export {};
