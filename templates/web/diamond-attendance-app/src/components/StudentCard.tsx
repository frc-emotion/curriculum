// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/components/StudentCard.tsx
// STEPS HERE:  3
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Your Gold AttendanceCard, rebuilt for a phone. The logic is identical; what
// changes is that there is no HTML here. `<div>` and `<button>` do not exist on
// a phone — you get React Native's own components instead, and they map onto
// real native views.
//
// Imports you will need (add them yourself):
//   import { Pressable, StyleSheet, Text, View } from 'react-native';
//   import type { AttendanceStatus, Student } from '../data/roster';

// STEP 3: Build the card with StyleSheet
// WHAT:       Write a component taking these typed props:
//               student   Student
//               status    AttendanceStatus
//               onPress   (studentId: number) => void
//             It shows the name, `Grade 11 · Software`, and the current status,
//             and calls onPress with the student's id when tapped.
//             Style it with `StyleSheet.create` — NOT NativeWind. The detail
//             screen at step 6 uses NativeWind instead, on purpose, so you meet
//             both.
//             Add `testID="student-card"` to the pressable. The checks use it
//             to find your cards.
// WHY:        StyleSheet is React Native's own styling, and it is what you will
//             read in any older file in nautilus-frontend. It looks like CSS
//             and mostly is not: no cascade, no inheritance, numbers instead of
//             pixels, and flexbox by default with a column direction. Meeting
//             it before the shortcut means NativeWind feels like a convenience
//             rather than magic.
// CONCEPTS:   View / Text / Pressable, StyleSheet.create, flexbox on React
//             Native, testID, why there is no <div>
// READ:       Guide > Web Diamond > Resources #1
// CHECKED BY: roster.check.tsx
// DONE WHEN:  twelve cards render on the roster screen and each one is
//             tappable.

export {};
