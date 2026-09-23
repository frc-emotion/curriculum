// ============================================================
// RANK:        Web Gold: Attendance Card
// FILE:        src/App.tsx
// STEPS HERE:  4, 5, 6, 7, 8
// GUIDE:       GUIDE_URL  (section "Web Gold")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: the cards toggle correctly, the filter works, there are no key
//              warnings in the console, and state is never mutated directly.
// ============================================================
//
// The top of the tree. App owns the attendance data; everything below it just
// displays what it is given and reports clicks back up.
//
// That arrangement has a name — "state lives at the lowest common ancestor of
// everything that needs it" — and it is most of what makes a React app
// maintainable. The list needs the statuses. The filter needs to know the
// statuses to filter by them. So the statuses live here, above both.
//
// Imports you will need as you go (add them yourself):
//   import { useState } from 'react';
//   import AttendanceList from './components/AttendanceList.tsx';
//   import type { AttendanceStatus } from './data/roster.ts';

import { roster } from './data/roster.ts';

export default function App() {
  // STEP 4: Own the attendance state
  // WHAT:       Hold the attendance in state with useState. The shape is:
  //               Record<number, AttendanceStatus>
  //             — a lookup from student id to status. Start every student on
  //             the roster at DEFAULT_STATUS from src/data/roster.ts.
  //             Pass it, and the roster, down to AttendanceList.
  // WHY:        A plain variable would work exactly once: you could change it,
  //             and nothing on screen would move. useState is how you tell
  //             React "this value matters to what's drawn — when it changes,
  //             draw again". That connection is the single most important idea
  //             in React.
  //             The lookup shape matters too: finding a student's status by id
  //             is instant, and no card has to search a list.
  // CONCEPTS:   useState, state vs a plain variable, re-rendering, Record<K, V>,
  //             initial state
  // READ:       Guide > Web Gold > Resources #2 and #4
  // CHECKED BY: app.check.tsx
  // DONE WHEN:  all 12 students render as 'absent' when the page first loads.

  // STEP 5: Toggle a student's status, without mutating
  // WHAT:       Write the handler you pass down as `onToggle`. Given a student
  //             id, it moves that student to the next status, cycling:
  //               present -> absent -> excused -> present
  //             It must build a NEW object rather than changing the existing
  //             one. Only that student's entry changes.
  // WHY:        React decides whether to redraw by asking "is this the same
  //             object as last time?" — it does not go looking inside. Change
  //             a property on the existing object and the answer is "same
  //             object", so nothing redraws, and your click appears to do
  //             nothing at all. This is the immutability rule from Copper, and
  //             this is the rank where ignoring it visibly breaks the screen.
  // CONCEPTS:   Immutable updates, spreading objects, updater functions,
  //             reference equality, why React re-renders
  // READ:       Guide > Web Gold > Resources #4
  // CHECKED BY: app.check.tsx
  // DONE WHEN:  clicking one card cycles it through all three statuses, and no
  //             other card changes.

  // STEP 6: Add a filter
  // WHAT:       Add a second piece of state for the current filter, one of:
  //               'all' | 'present' | 'absent' | 'excused'
  //             Render four buttons (class `filter-button`, inside a div with
  //             class `filters`) that set it, and show only the students whose
  //             status matches. Mark the active one with
  //             `aria-pressed={true}` — the CSS uses that, and so do screen
  //             readers.
  //             Work out the filtered list DURING render, from the state you
  //             already have. Do not keep a third piece of state holding it.
  // WHY:        Anything you can calculate, calculate. A stored copy of the
  //             filtered list is a second source of truth, and the day it
  //             disagrees with the first one you will have no idea which is
  //             right. This is the most common piece of feedback on student
  //             React PRs.
  // CONCEPTS:   Multiple pieces of state, derived values, filtering during
  //             render, aria-pressed, conditional class names
  // READ:       Guide > Web Gold > Resources #4
  // CHECKED BY: app.check.tsx
  // DONE WHEN:  pressing "Present" shows only present students, and "All"
  //             brings everyone back.

  // STEP 7: Say something when the list is empty
  // WHAT:       When the filter matches nobody, render a message instead of an
  //             empty list — something like "Nobody is marked present yet."
  //             There is an `empty` class in the CSS for it.
  // WHY:        A blank space is ambiguous: is it loading, is it broken, did
  //             the filter work? One sentence removes the doubt. Every screen
  //             you build from here on has three states — data, empty, error —
  //             and empty is the one people forget.
  // CONCEPTS:   Conditional rendering, ternaries in JSX, empty states
  // READ:       Guide > Web Gold > Resources #3
  // CHECKED BY: app.check.tsx
  // DONE WHEN:  filtering to "Excused" on a fresh page shows your message, not
  //             a blank gap.

  // STEP 8: Explain the click
  // WHAT:       In your pull request, write two or three sentences tracing what
  //             happens between clicking a status button and the screen
  //             changing. Name the pieces: the event handler, the state update,
  //             the re-render.
  // WHY:        If you can follow that path in words, you can debug it. Most
  //             "React is broken" moments are really one of those three steps
  //             not happening, and knowing the order tells you which one to
  //             look at.
  // CONCEPTS:   The React render cycle, state updates, re-rendering
  // READ:       Guide > Web Gold > Resources #4
  // CHECKED BY: your reviewer
  // DONE WHEN:  the explanation is in your PR description, in your own words.

  return (
    <div className="page">
      <h1>Attendance</h1>
      <p className="subtitle">FRC 2658 · {roster.length} students</p>
    </div>
  );
}
