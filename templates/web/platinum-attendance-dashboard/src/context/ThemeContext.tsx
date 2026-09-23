// ============================================================
// RANK:        Web Platinum: Attendance Dashboard
// FILE:        src/context/ThemeContext.tsx
// STEPS HERE:  6
// GUIDE:       GUIDE_URL  (section "Web Platinum")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: counts update instantly on toggle, loading and error states both
//              show up, and no value is stored in state that could be
//              calculated instead.
// ============================================================
//
// Some things are needed everywhere: the theme, the signed-in user, the
// language. Passing them down through every component in between is called
// "prop drilling", and it means a component that does not care about the theme
// still has to accept it and hand it on. Context is the way out.
//
// Imports you will need as you go (add them yourself):
//   import { createContext, useContext, useMemo, useState } from 'react';
//   import type { ReactNode } from 'react';

// STEP 6: Build the theme context
// WHAT:       Export three things from this file:
//               - a ThemeProvider component taking `children`, which holds the
//                 current theme ('light' | 'dark') in state
//               - a `useTheme()` hook returning
//                   { theme: 'light' | 'dark', toggleTheme: () => void }
//               - (the context itself can stay private to this file)
//             Then, in App, wrap the page in ThemeProvider, set
//             `data-theme={theme}` on the outer element, and put a theme toggle
//             button somewhere that is NOT a direct child of App — inside the
//             summary or the header component — so the value genuinely travels
//             without being passed as a prop.
//             `useTheme()` should throw a clear error if it is used outside the
//             provider. src/index.css already has both palettes.
// WHY:        The reason to make the button a grandchild is that it proves the
//             point. If the toggle sat right next to the provider you could
//             have passed a prop and never needed context at all.
//             The throw matters too: a hook used outside its provider otherwise
//             fails later, somewhere else, with a confusing message.
// CONCEPTS:   createContext, useContext, provider components, prop drilling,
//             custom hooks over raw context, children
// READ:       Guide > Web Platinum > Resources #3
// CHECKED BY: theme.check.tsx
// DONE WHEN:  the toggle changes the page's colours, and the component holding
//             the button never receives a theme prop.

export {};
