# Web Gold — Attendance Card

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Web Iron (TypeScript and async code)

React, finally. You're building the attendance screen a lead actually uses at a meeting: a
card per student, tap to change their status, filter by status.

Everything here transfers directly to nautilus-frontend — same ideas, same patterns, one fewer
layer of framework in the way.

Guide: **GUIDE_URL** (section "Web Gold")

---

## Skills required

Everything from Iron, plus:

- Components as functions, and JSX
- Typed props
- `useState`
- Event handlers and passing functions down as props
- Rendering lists, and the `key` prop
- Conditional rendering
- Immutable state updates
- Derived values (calculate, don't store)

---

## Setup

Node 24, then in this folder:

```bash
npm install
npm run dev
```

`npm run dev` prints a `http://localhost:5173` address. Open it. **Keep the browser console
open while you work** — React puts its warnings there, and "no key warnings in the console"
is on the Passes when list.

---

## Commands

```bash
npm run dev         # the dev server — this is how you look at your work
npm run check       # rank checks + type checker + linter
npm run typecheck   # just the compiler
npm run lint        # just the linter
```

The untouched template runs and shows a heading. Everything below the heading is yours.

---

## The files

```
index.html                          the page React attaches to
src/
├── main.tsx                        plumbing — don't edit
├── index.css                       finished styles, with class names to use
├── App.tsx                         steps 4-8 — owns the state
├── components/
│   ├── AttendanceCard.tsx          steps 1-2 — one student, one row
│   └── AttendanceList.tsx          step 3 — many cards
└── data/roster.ts                  the roster and its types — don't edit
checks/                             the grader. Don't edit it.
```

The two component files arrive empty, with `export {};` at the bottom to keep the project
importable. Delete that line once you export your component. **Export either as the default
or by name** — the checks accept both.

---

## Steps

**STEP 1 — `AttendanceCard`.**
Typed props: `student: Student`, `status: AttendanceStatus`, `onToggle: (studentId: number) => void`.
Renders the name, `Grade 11 · Software`, and a `<button>` showing the status.
*Checked by: attendanceCard.check.tsx.*

**STEP 2 — The button calls `onToggle(student.id)`.**
The card reports the click. It does not decide the new status.
*Checked by: attendanceCard.check.tsx.*

**STEP 3 — `AttendanceList`.**
Props: `students`, `statuses`, `onToggle`. A `<ul className="card-list">` with one `<li>` per
student. **`key` must be the student's id, not the array index.**
*Checked by: attendanceList.check.tsx, sourceScan.check.tsx.*

**STEP 4 — `App` owns the state.**
`useState` holding `Record<number, AttendanceStatus>`, everyone starting at `DEFAULT_STATUS`.
*Checked by: app.check.tsx.*

**STEP 5 — Toggle immutably.**
`present → absent → excused → present`. Build a **new** object every time — React compares by
identity, so mutating the old one changes nothing on screen.
*Checked by: app.check.tsx.*

**STEP 6 — The filter.**
A second piece of state: `'all' | 'present' | 'absent' | 'excused'`, four buttons that set it,
`aria-pressed` on the active one. Work out the filtered list **during render** — don't store it
in a third piece of state.
*Checked by: app.check.tsx.*

**STEP 7 — An empty state** when the filter matches nobody.
*Checked by: app.check.tsx.*

**STEP 8 — In your PR: trace a click.**
Two or three sentences from button press to updated screen. Name the handler, the state
update, the re-render.
*Checked by: your reviewer.*

---

## Passes when

- The cards toggle correctly.
- The filter works.
- There are **no key warnings in the browser console**.
- State is never mutated directly.
- `npm run check` passes.
- Step 8's explanation is in your PR.

---

## Resources

1. [React: Your First Component](https://react.dev/learn/your-first-component)
2. [React: Passing Props to a Component](https://react.dev/learn/passing-props-to-a-component)
3. [React: Rendering Lists](https://react.dev/learn/rendering-lists)
4. [React: State: A Component's Memory](https://react.dev/learn/state-a-components-memory)

Numbers match the guide's "Web Gold" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **Clicking does nothing.** The classic: you changed the existing state object instead of
  making a new one. React asks "is this the same object?" — not "did anything inside change?"
- **`onToggle` fires during render.** `onClick={onToggle(id)}` calls it immediately.
  `onClick={() => onToggle(id)}` hands React a function to call later.
- **"Each child in a list should have a unique key."** Step 3. Use `student.id`.
- **The whole page went blank.** Check the browser console; a thrown error in a component
  unmounts everything. The message usually names the component.
- **Types complain about `statuses[student.id]`.** `noUncheckedIndexedAccess` is on, so a
  lookup might be `undefined`. Fall back to `DEFAULT_STATUS` with `??`.
- **The filter shows stale results.** You stored the filtered list in state. Delete that state
  and calculate it while rendering.
