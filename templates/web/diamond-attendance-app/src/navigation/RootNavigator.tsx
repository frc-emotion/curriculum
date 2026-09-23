// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/navigation/RootNavigator.tsx
// STEPS HERE:  2, 10
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Imports you will need as you go (add them yourself):
//   import { createNativeStackNavigator } from '@react-navigation/native-stack';
//   import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
//   import RosterScreen from '../screens/RosterScreen';
//   import SettingsScreen from '../screens/SettingsScreen';
//   import StudentDetailScreen from '../screens/StudentDetailScreen';
//   import type { RootStackParamList, RootTabParamList } from './types';

// STEP 2: Build the stack
// WHAT:       Create a native stack navigator typed with your
//             RootStackParamList, with two screens:
//               name="Roster"         -> RosterScreen
//               name="StudentDetail"  -> StudentDetailScreen
//             Give each a sensible title in the header. Export the stack
//             component as the default export.
// WHY:        A stack is a pile of screens: push one on, and the back gesture
//             pops it off. That is the mental model behind every phone app you
//             have used, and it is why you get a back button for free rather
//             than wiring one up.
// CONCEPTS:   Native stack navigator, screens, headers, typed navigators
// READ:       Guide > Web Diamond > Resources #2
// CHECKED BY: navigation.check.tsx
// DONE WHEN:  the app opens on the roster and can push a detail screen.

// STEP 10: Add bottom tabs
// WHAT:       Wrap what you have in a bottom tab navigator typed with your
//             RootTabParamList, with two tabs:
//               RosterTab    -> your stack from step 2
//               SettingsTab  -> SettingsScreen
//             Put the theme toggle from ThemeContext on the Settings screen.
// WHY:        A stack inside a tab is the single most common shape in real
//             apps, and it is worth meeting once: each tab keeps its own
//             history, so switching tabs and coming back leaves you where you
//             were. nautilus-frontend is built this way.
// CONCEPTS:   Bottom tabs, nesting navigators, each tab having its own stack
// READ:       Guide > Web Diamond > Resources #2
// CHECKED BY: navigation.check.tsx, your reviewer
// DONE WHEN:  both tabs work, and going Roster -> detail -> Settings -> Roster
//             leaves you on the detail screen.

export {};
