// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/screens/SettingsScreen.tsx
// STEPS HERE:  10
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Imports you will need (add them yourself):
//   import { Pressable, Text, View } from 'react-native';
//   import { useTheme } from '../theme/ThemeContext';

// STEP 10 (continued from RootNavigator.tsx): the settings tab
// WHAT:       A simple screen with a button that calls `toggleTheme` from your
//             theme context, and text saying which theme is on. Style it
//             however you like — either approach is fine here.
//             Add `testID="theme-toggle"` to the button.
// WHY:        A second tab that does something real, and a place for the theme
//             toggle to live that is nowhere near where the theme is defined —
//             which is the point of context, one more time.
// CONCEPTS:   Tabs, context consumers, simple screens
// READ:       Guide > Web Diamond > Resources #3
// CHECKED BY: your reviewer
// DONE WHEN:  the toggle changes the app's appearance from the Settings tab.

export {};
