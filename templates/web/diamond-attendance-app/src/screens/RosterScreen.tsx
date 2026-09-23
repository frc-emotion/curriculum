// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/screens/RosterScreen.tsx
// STEPS HERE:  4, 5
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Imports you will need as you go (add them yourself):
//   import { FlatList, View } from 'react-native';
//   import type { NativeStackScreenProps } from '@react-navigation/native-stack';
//   import StudentCard from '../components/StudentCard';
//   import { roster } from '../data/roster';
//   import type { RootStackParamList } from '../navigation/types';

// STEP 4: Render the roster with a FlatList
// WHAT:       Render the roster in a `FlatList`, not with `.map`. You need
//             three props: `data`, `renderItem`, and `keyExtractor` (the
//             student's id, as a string).
// WHY:        `.map` builds every row up front. A FlatList only builds the rows
//             on screen and recycles them as you scroll — with twelve students
//             you would never notice, but this is a habit worth forming before
//             the list is a season of match scouting data. It is also what
//             gives you pull-to-refresh, sticky headers and scroll-to-index for
//             free later.
// CONCEPTS:   FlatList, renderItem, keyExtractor, virtualised lists, why not
//             .map on a phone
// READ:       Guide > Web Diamond > Resources #1
// CHECKED BY: roster.check.tsx, sourceScan.check.tsx (a FlatList must appear)
// DONE WHEN:  all twelve students render and the list scrolls smoothly.

// STEP 5: Tap a card to open the detail screen
// WHAT:       Type this screen's props with
//             `NativeStackScreenProps<RootStackParamList, 'Roster'>`, and when
//             a card is tapped, call
//               navigation.navigate('StudentDetail', { studentId })
// WHY:        This is the moment the typed param list from step 1 pays off. Get
//             the screen name wrong, or forget the studentId, and the compiler
//             says so — instead of the app pushing a screen that renders
//             nothing and leaves you staring at it.
// CONCEPTS:   navigation.navigate, route params, NativeStackScreenProps, typed
//             navigation
// READ:       Guide > Web Diamond > Resources #2
// CHECKED BY: navigation.check.tsx
// DONE WHEN:  tapping Ada opens a detail screen showing Ada, and the back
//             gesture returns you to the roster.

export {};
