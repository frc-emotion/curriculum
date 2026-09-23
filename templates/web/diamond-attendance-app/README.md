# Web Diamond — Attendance App

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Web Platinum (deeper React)

The same attendance app, on a phone. Two screens with typed navigation between them, a list
that scrolls properly, and attendance that is still there tomorrow morning.

Most of what you know still applies — components, props, state, context are all React, not
React DOM. What changes is everything at the bottom: there is no `<div>`, no `<button>`, and
no localStorage.

Guide: **GUIDE_URL** (section "Web Diamond")

---

## Skills required

Everything from Platinum, plus:

- React Native components: `View`, `Text`, `Pressable`, `FlatList`
- `StyleSheet.create`, and how RN styling differs from CSS
- NativeWind (`className` on native components)
- React Navigation: native stack, bottom tabs, typed route params
- `AsyncStorage`
- Expo, and running a real app on a real phone

---

## Setup

**You need a phone or an emulator.** Pick one:

- **Your own phone (easiest).** Install **Expo Go** from the App Store or Play Store.
  On iOS you now need a free Expo account signed in **on both sides** — run `npx expo login`
  in the terminal and sign in inside Expo Go with the same account. (Android doesn't enforce
  this yet; simulators are exempt.)
- **Android emulator** via Android Studio, or the **iOS simulator** via Xcode on a Mac.

Then:

```bash
npm install
npx expo start
```

Scan the QR code with your phone, or press `a` / `i` for an emulator.

This is an **Expo SDK 57** project — React Native 0.86, React 19.2 — which is the SDK current
Expo Go runs. `nautilus-frontend` is on an older SDK and uses a development build instead, so
the versions here are deliberately newer. The concepts are identical; a couple of imports
differ, and the guide notes where.

---

## Commands

```bash
npx expo start      # run the app on your phone or an emulator
npm run check       # rank checks + type checker + linter
npm run typecheck
npm run lint
```

The untouched template runs and shows a placeholder screen. The checks run entirely on your
laptop — no phone needed for those — but **step 7 is a real device**, and no checker can do
that part for you.

---

## The files

```
App.tsx                                 steps 8, 10 — providers and the navigator
src/
├── navigation/
│   ├── types.ts                        step 1 — the typed route map
│   └── RootNavigator.tsx               steps 2, 10 — stack, then tabs
├── screens/
│   ├── RosterScreen.tsx                steps 4, 5 — FlatList, navigate
│   ├── StudentDetailScreen.tsx         steps 6, 8 — route params, NativeWind
│   └── SettingsScreen.tsx              step 10 — theme toggle
├── components/StudentCard.tsx          step 3 — StyleSheet
├── storage/
│   ├── attendanceStorage.ts            step 9 — AsyncStorage
│   └── AttendanceContext.tsx           steps 8, 9 — shared state
├── theme/ThemeContext.tsx              step 10 — copy yours from Platinum
└── data/roster.ts                      roster, types, storage key — don't edit
checks/                                 the grader. Don't edit it.
```

Config files (`app.json`, `babel.config.js`, `metro.config.js`, `tailwind.config.js`,
`global.css`, `nativewind-env.d.ts`) are finished. You don't need to touch any of them.

---

## Steps

**STEP 1 — `RootStackParamList` and `RootTabParamList`** in `src/navigation/types.ts`.
`StudentDetail` takes `{ studentId: number }`. No `any`.
*Checked by: sourceScan.check.ts, tsc.*

**STEP 2 — The native stack** in `RootNavigator.tsx`: `Roster` and `StudentDetail`.
*Checked by: navigation.check.tsx.*

**STEP 3 — `StudentCard`, styled with `StyleSheet`.**
Props: `student`, `status`, `onPress`. Add `testID="student-card"`.
*Checked by: roster.check.tsx.*

**STEP 4 — `RosterScreen` with a `FlatList`** — `data`, `renderItem`, `keyExtractor`. Not `.map`.
*Checked by: navigation.check.tsx, sourceScan.check.ts.*

**STEP 5 — Tap a card → `navigation.navigate('StudentDetail', { studentId })`.**
*Checked by: navigation.check.tsx.*

**STEP 6 — `StudentDetailScreen`**, reading `route.params.studentId`, styled with NativeWind
`className`. Add `testID="student-detail"`.
*Checked by: navigation.check.tsx, sourceScan.check.ts.*

**STEP 7 — Run it on a real phone or emulator, and put screenshots (or a short recording) in
your PR.**
*Checked by: your reviewer. Nothing else can check this one.*

**STEP 8 — `AttendanceContext`** so both screens share one truth. A toggle on the detail
screen (`testID="detail-toggle"`) must be visible on the roster when you go back.
*Checked by: attendance.check.tsx.*

**STEP 9 — Persist with AsyncStorage.** `loadAttendance()` and `saveAttendance()` in
`attendanceStorage.ts`; load on mount, save on change. Load must survive an empty or corrupt
store.
*Checked by: storage.check.ts, sourceScan.check.ts.*

**STEP 10 — Bottom tabs and the theme.** Roster tab + Settings tab, with your Platinum theme
context and a toggle on Settings (`testID="theme-toggle"`).
*Checked by: navigation.check.tsx, your reviewer.*

**STEP 11 — In your PR:** what surprised you about React Native compared with React on the
web?
*Checked by: your reviewer.*

---

## Passes when

- Navigation works **both ways**, with params.
- Attendance **persists after a restart** — kill the app, reopen it, and it's still there.
- No TypeScript errors in the navigation types.
- `npm run check` passes.
- Screenshots from a real device are in your PR.

---

## Resources

1. [React Native: Core Components](https://reactnative.dev/docs/intro-react-native-components)
2. [React Navigation: Getting Started](https://reactnavigation.org/docs/getting-started)
3. [React: Passing Data Deeply with Context](https://react.dev/learn/passing-data-deeply-with-context)
4. [NativeWind: Installation and usage](https://www.nativewind.dev/docs/getting-started/installation)
5. [AsyncStorage: Usage](https://react-native-async-storage.github.io/async-storage/docs/usage)

Numbers match the guide's "Web Diamond" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **The QR code won't connect.** Phone and laptop must be on the same wifi. On school wifi
  that often fails — try `npx expo start --tunnel`.
- **iOS Expo Go says you need to log in.** That's the new SDK 57 rule: `npx expo login` in
  the terminal, and sign in inside Expo Go with the same free account.
- **"Text strings must be rendered within a `<Text>` component."** The most common React
  Native error. Any loose text has to be inside `<Text>`.
- **`className` does nothing.** NativeWind only scans the paths in `tailwind.config.js`, and
  `App.tsx` has to import `./global.css`. Restart the bundler after changing config:
  `npx expo start -c`.
- **Navigating says the screen doesn't exist.** The name in `navigate()` must match the
  `name` prop on `Stack.Screen` exactly — `RootStackParamList` is what makes that a compile
  error instead of a runtime shrug.
- **Attendance vanishes on restart.** Almost always the save-before-load bug: the empty
  starting state gets written over the stored data. Don't save until the load has finished.
- **The layout is nothing like you expected.** React Native is flexbox by default, in a
  column. There is no cascade and no inheritance — every component styles itself.
