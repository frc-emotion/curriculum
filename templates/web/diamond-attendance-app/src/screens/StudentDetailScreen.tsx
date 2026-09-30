// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/screens/StudentDetailScreen.tsx
// STEPS HERE:  6, 8
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (section "Web Diamond")
// RUN:         npx expo start
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Imports you will need as you go (add them yourself):
//   import { Pressable, Text, View } from 'react-native';
//   import type { NativeStackScreenProps } from '@react-navigation/native-stack';
//   import { findStudent } from '../data/roster';
//   import type { RootStackParamList } from '../navigation/types';

// STEP 6: Read the param, and style this screen with NativeWind
// WHAT:       Type the props with
//             `NativeStackScreenProps<RootStackParamList, 'StudentDetail'>`,
//             read `route.params.studentId`, look the student up with
//             `findStudent`, and show their name, grade, subteam and current
//             status.
//             Style this screen with NativeWind `className` strings — NOT
//             StyleSheet. Tailwind class names, on React Native components:
//               <View className="flex-1 items-center justify-center p-6">
//             Handle the case where the student is not found. It should not be
//             possible, but "should not be possible" is not the same as "is
//             not possible".
//             Add `testID="student-detail"` to the outer View.
// WHY:        Two styling systems in one app is not a mistake — it is what
//             nautilus-frontend actually looks like, because it was partly
//             converted. You need to be able to read and write both, and to
//             notice that NativeWind is just generating the same StyleSheet
//             objects underneath.
// CONCEPTS:   route.params, typed screen props, NativeWind className, Tailwind
//             utility classes on native components, handling missing data
// READ:       "Web Diamond" tab > Skills required
// DONE WHEN:  tapping any student opens their details, correctly styled.

// STEP 8 (continued from AttendanceContext.tsx): toggle from here too
// WHAT:       Put a button on this screen that toggles the student's status,
//             reading and writing through your attendance context.
// WHY:        This is the test of whether the state really is shared. Toggle
//             here, go back, and the roster must already agree — no refresh, no
//             passing anything back through navigation.
// CONCEPTS:   Shared state across screens, context, why not to pass data back
//             through navigation
// READ:       "Web Diamond" tab > Skills required
// DONE WHEN:  a change made here is visible on the roster when you go back.

export {};
