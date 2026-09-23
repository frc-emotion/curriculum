// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/theme/ThemeContext.tsx
// STEPS HERE:  10
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================

// STEP 10 (continued from RootNavigator.tsx): bring your theme context across
// WHAT:       Copy YOUR ThemeContext from
//             students/<your-username>/web/platinum/src/context/ThemeContext.tsx
//             into this file. The React part is identical — context does not
//             know it is on a phone.
//             Export `ThemeProvider` and `useTheme()` as before.
// WHY:        Worth noticing how little changes. Hooks, context and state are
//             React, not React DOM, so they work the same on a phone. What
//             changes is only the components you render at the bottom.
// CONCEPTS:   Context on React Native, reusing your own code, what is React vs
//             what is the platform
// READ:       Guide > Web Diamond > Resources #3
// CHECKED BY: your reviewer
// DONE WHEN:  the Settings tab can toggle the theme.

export {};
