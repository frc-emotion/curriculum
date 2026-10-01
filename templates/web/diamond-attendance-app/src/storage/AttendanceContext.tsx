// ============================================================
// RANK:        Web Diamond: Attendance App
// STEPS HERE:  8, 9
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Web track > Diamond tab)
// ============================================================
//
// The attendance data, in one place that both screens can reach. Same shape as
// the theme context you wrote at Platinum — a provider, a hook, and a throw if
// the hook is used outside the provider.
//
// Imports you will need (add them yourself):
//   import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
//   import type { ReactNode } from 'react';
//   import { DEFAULT_STATUS, type AttendanceStatus } from '../data/roster';
//   import { loadAttendance, saveAttendance } from './attendanceStorage';

// STEP 8: Share the attendance between screens
// WHAT:       Export an `AttendanceProvider` taking `children`, and a
//             `useAttendance()` hook returning exactly:
//               statuses     Record<number, AttendanceStatus>
//               statusFor    (studentId: number) => AttendanceStatus
//               toggle       (studentId: number) => void
//               isReady      boolean
//             `toggle` cycles present -> absent -> excused -> present, building
//             a new object each time — same rule as Gold.
//             `useAttendance()` throws if used outside the provider.
// WHY:        Two screens need the same answer to "is Ada here?". Passing it
//             through navigation params would mean two copies that drift apart;
//             lifting it to a provider above the navigator means there is only
//             ever one.
//             `isReady` exists because the stored data arrives asynchronously —
//             see step 9.
// CONCEPTS:   Context, providers, shared state across screens, immutable
//             updates, custom hooks
// DONE WHEN:  toggling on either screen updates both.

// STEP 9 (continued from attendanceStorage.ts): load once, save on change
// WHAT:       When the provider mounts, load the saved attendance and put it
//             into state; set `isReady` to true when that finishes. After that,
//             save whenever the attendance changes.
//             Careful: do not save the empty starting state over the top of the
//             saved data before the load has finished. That bug wipes the
//             user's data on every launch, and it looks exactly like "saving
//             does not work".
// WHY:        This is the whole feature. A meeting register that forgets
//             everything when the app closes is a worse version of paper. And
//             the load-then-save ordering is the kind of thing that is obvious
//             once you have been bitten and invisible before.
// CONCEPTS:   useEffect for loading, effects that depend on state, ordering,
//             async state, guarding an effect
// DONE WHEN:  marking people in, force-quitting the app and reopening it shows
//             the same attendance.

export {};
