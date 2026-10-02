// ============================================================
// RANK:        Web Diamond: Attendance App
// STEPS HERE:  10
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Web track > Diamond tab)
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
// DONE WHEN:  the Settings tab can toggle the theme.

export {};
