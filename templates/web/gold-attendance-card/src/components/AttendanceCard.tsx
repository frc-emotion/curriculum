// ============================================================
// RANK:        Web Gold: Attendance Card
// FILE:        src/components/AttendanceCard.tsx
// STEPS HERE:  1, 2
// GUIDE:       GUIDE_URL  (section "Web Gold")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: the cards toggle correctly, the filter works, there are no key
//              warnings in the console, and state is never mutated directly.
// ============================================================
//
// One student, one row. This is the smallest component in the app and the one
// worth getting right, because everything else is made of these.
//
// A component is a function that takes props and returns what to draw. That is
// the whole idea. It does not own the attendance data — it is handed a status
// and a function to call, and it does as it is told. That makes it easy to test
// and impossible for it to disagree with the rest of the app.
//
// Imports you will need (add them yourself):
//   import type { AttendanceStatus, Student } from '../data/roster.ts';
//
// Export it either as the default export or as a named export called
// `AttendanceCard` — the checks accept either.

// STEP 1: Build the card
// WHAT:       Write a component that takes exactly these props, with a TYPE for
//             them (no `any`):
//               student   Student
//               status    AttendanceStatus
//               onToggle  (studentId: number) => void
//             It renders:
//               - the student's name
//               - their grade and subteam, in the shape `Grade 11 · Software`
//               - a <button> whose text is the current status
//             There are class names ready for you in src/index.css: `card`,
//             `card-name`, `card-meta`, `status-button`, and `status-present` /
//             `status-absent` / `status-excused`.
// WHY:        Typed props are a contract between two components. Get them wrong
//             and the compiler tells you at the call site, not at 9pm when a
//             card renders "undefined". This is the same idea as the method
//             signatures you wrote at Iron, applied to the screen.
// CONCEPTS:   Components as functions, props, typed props, JSX, template
//             literals in JSX
// READ:       Guide > Web Gold > Resources #1 and #2
// CHECKED BY: attendanceCard.check.tsx
// DONE WHEN:  rendering a card shows the name, `Grade 11 · Software`, and a
//             button with the status on it.

// STEP 2: Make the button report the click
// WHAT:       When the button is clicked, call `onToggle` with THIS student's
//             id. The card does not decide what the new status is — it only
//             reports that someone pressed the button.
// WHY:        A component that changes data it was given cannot be reused, and
//             two of them will eventually disagree about what the truth is.
//             Sending the event upward — "the user did a thing, here's who" —
//             is how React keeps one owner for each piece of state. You will
//             see the same pattern in every form you ever write.
// CONCEPTS:   Event handlers, onClick, passing functions as props, lifting
//             events up
// READ:       Guide > Web Gold > Resources #2
// CHECKED BY: attendanceCard.check.tsx
// DONE WHEN:  clicking the button calls onToggle exactly once, with the
//             student's id.

// This line makes the file a module while it is still empty. Delete it once you
// have exported your component.
export {};
