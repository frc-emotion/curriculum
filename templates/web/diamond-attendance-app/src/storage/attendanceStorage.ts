// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/storage/attendanceStorage.ts
// STEPS HERE:  9
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Everything that talks to the phone's storage lives here — the same rule as
// api.ts at Iron. One file knows about AsyncStorage; nothing else does.
//
// Imports you will need (add them yourself):
//   import AsyncStorage from '@react-native-async-storage/async-storage';
//   import { ATTENDANCE_STORAGE_KEY, type AttendanceStatus } from '../data/roster';

// STEP 9: Save and load the attendance
// WHAT:       Export exactly these two async functions:
//               loadAttendance(): Promise<Record<number, AttendanceStatus>>
//               saveAttendance(attendance: Record<number, AttendanceStatus>): Promise<void>
//             Use AsyncStorage with ATTENDANCE_STORAGE_KEY from
//             src/data/roster.ts. AsyncStorage only stores strings, so you will
//             be using JSON.
//             loadAttendance must return an empty object rather than throwing
//             when there is nothing stored yet, or when what IS stored turns
//             out to be unreadable.
// WHY:        A first launch has nothing saved, and a half-written or
//             out-of-date value is a real possibility on a phone that ran out
//             of battery mid-save. Both cases have to end with a usable app
//             rather than a crash on startup — which is the worst kind, because
//             the user cannot get past it to fix anything.
//             This is the same trust-boundary thinking as zod at Iron: data
//             from outside your program is a rumour until you have checked it.
// CONCEPTS:   AsyncStorage, JSON.stringify / JSON.parse, async functions,
//             defensive parsing, keys and versioning
// READ:       Guide > Web Diamond > Resources #5
// CHECKED BY: storage.check.ts
// DONE WHEN:  saving then loading gives you back what you saved, and loading
//             with nothing stored gives you {}.

export {};
