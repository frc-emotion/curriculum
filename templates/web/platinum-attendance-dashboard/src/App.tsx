// ============================================================
// RANK:        Web Platinum: Attendance Dashboard
// FILE:        src/App.tsx
// STEPS HERE:  2, 3, 4, 6, 7
// GUIDE:       GUIDE_URL  (section "Web Platinum")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: counts update instantly on toggle, loading and error states both
//              show up, and no value is stored in state that could be
//              calculated instead.
// ============================================================
//
// The dashboard. Same job as Gold's App — own the state, hand it down — with
// two new problems: the data now arrives late, and it might not arrive at all.
//
// Imports you will need as you go (add them yourself):
//   import { useState } from 'react';
//   import AttendanceList from './components/AttendanceList.tsx';
//   import { ThemeProvider, useTheme } from './context/ThemeContext.tsx';
//   import { useRoster } from './hooks/useRoster.ts';
//   import { DEFAULT_STATUS, type AttendanceStatus } from './data/roster.ts';

export default function App() {
  // STEP 2: Show something while the roster is loading
  // WHAT:       Call useRoster. While `isLoading` is true, render a loading
  //             message instead of the list.
  // WHY:        Without this the screen is blank for a moment and then things
  //             appear, which reads as "broken, then fixed". On a school wifi
  //             that moment can be several seconds.
  // CONCEPTS:   Conditional rendering, loading states, early returns
  // READ:       Guide > Web Platinum > Resources #2
  // CHECKED BY: dashboard.check.tsx
  // DONE WHEN:  a slow response shows your loading message first.

  // STEP 3: Show something when it fails
  // WHAT:       When `error` is set, render that message to the user instead of
  //             the list. Make it something a person can act on.
  // WHY:        This is the state everybody forgets, and the only one that
  //             happens at competition on a saturated wifi. "Couldn't load the
  //             roster — check your connection" is a complete feature.
  // CONCEPTS:   Error states, conditional rendering, error messages as UI
  // READ:       Guide > Web Platinum > Resources #2
  // CHECKED BY: dashboard.check.tsx
  // DONE WHEN:  a failing request shows your message and no empty list.

  // STEP 4: The summary counts
  // WHAT:       Above the list, show a summary line reading exactly like this:
  //               3 of 12 present
  //             and a count for each status. CALCULATE these while rendering,
  //             from the statuses you already hold. Do not put them in state.
  // WHY:        A count kept in state is a second copy of the truth, and the
  //             day it disagrees with the first copy you will not know which is
  //             right. Calculating during render means it cannot ever be stale
  //             — which is why the counts update the instant you toggle a card,
  //             with no extra code at all.
  // CONCEPTS:   Derived state, calculating during render, why extra state is a
  //             bug waiting to happen
  // READ:       Guide > Web Platinum > Resources #1
  // CHECKED BY: dashboard.check.tsx
  // DONE WHEN:  toggling a card changes the summary immediately.

  // STEP 6 (continued from ThemeContext.tsx): wire the theme up
  // WHAT:       Wrap the page in your ThemeProvider, put `data-theme={theme}`
  //             on the outer element, and place the theme toggle button inside
  //             a child component rather than directly here.
  // WHY:        See ThemeContext.tsx. The point is that the button reaches the
  //             theme without anybody passing it down.
  // CONCEPTS:   Context providers, composition, data-attributes and CSS
  // READ:       Guide > Web Platinum > Resources #3
  // CHECKED BY: theme.check.tsx
  // DONE WHEN:  pressing the toggle switches the palette.

  // STEP 7 (continued): keep the attendance state here
  // WHAT:       Hold the statuses and the toggle handler here, exactly as you
  //             did at Gold, and pass them to your AttendanceList. Students
  //             arrive from useRoster now rather than from a file.
  // WHY:        Nothing about state ownership changed just because the data
  //             comes from a server. The list still displays what it is given.
  // CONCEPTS:   State ownership, immutable updates, composing hooks and state
  // READ:       Guide > Web Platinum > Resources #1
  // CHECKED BY: dashboard.check.tsx
  // DONE WHEN:  cards toggle and the summary keeps up.

  return (
    <div className="page">
      <h1>Attendance Dashboard</h1>
      <p className="subtitle">FRC 2658</p>
    </div>
  );
}
