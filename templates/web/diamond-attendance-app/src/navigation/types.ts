// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/navigation/types.ts
// STEPS HERE:  1
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// React Navigation is typed by handing it a map of "screen name -> what that
// screen expects". Everything else — navigate(), route.params, the back button
// — is checked against this one type.
//
// Get this right and the compiler catches a whole class of bug that is
// otherwise a runtime crash on somebody's phone.

// STEP 1: Describe your screens
// WHAT:       Export a type called `RootStackParamList` describing the stack:
//               Roster          takes no params        -> undefined
//               StudentDetail   takes { studentId: number }
//             Then export `RootTabParamList` for the tabs you add at step 10:
//               RosterTab       undefined
//               SettingsTab     undefined
//             No `any`. That is the whole point of this file.
// WHY:        `undefined` here does not mean "nothing"; it means "this screen
//             takes no parameters", and TypeScript will stop you passing any.
//             Once this type exists, `navigate('StudentDetail')` without a
//             studentId is a compile error rather than a blank screen — and
//             `route.params.studentId` is a number rather than a guess.
// CONCEPTS:   Param lists, typed navigation, union of screen names, why
//             `undefined` means "no params"
// READ:       Guide > Web Diamond > Resources #2
// CHECKED BY: navigation.check.tsx, sourceScan.check.tsx (no `any`), tsc
// DONE WHEN:  both types exist and the navigator and screens below use them.

export {};
