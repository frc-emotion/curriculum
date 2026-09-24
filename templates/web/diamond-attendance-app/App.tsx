// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        App.tsx
// STEPS HERE:  8, 9, 10
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// The root of the app. On the web this was App.tsx too — the difference is what
// goes in it: instead of a <div>, everything lives inside a NavigationContainer,
// because on a phone "what screen am I on" is part of the app's state.
//
// Imports you will need as you go (add them yourself):
//   import { NavigationContainer } from '@react-navigation/native';
//   import { AttendanceProvider } from './src/storage/AttendanceContext';
//   import { ThemeProvider } from './src/theme/ThemeContext';
//   import RootNavigator from './src/navigation/RootNavigator';

import { StatusBar } from 'expo-status-bar';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { StyleSheet, Text, View } from 'react-native';
import './global.css';

// STEP 8 (continued from AttendanceContext.tsx): wrap the app in your providers
// WHAT:       Once your attendance context exists, wrap the whole app in it
//             here, so both the roster screen and the detail screen see the
//             same attendance data.
// WHY:        Two screens, one truth. The detail screen has to show the same
//             status the roster shows, and toggling in either place has to
//             update both. That is exactly the problem context solves — same
//             as the theme at Platinum, except now it is data people care
//             about.
// CONCEPTS:   Context providers, provider order, sharing state between screens
// READ:       Guide > Web Diamond > Resources #3
// CHECKED BY: attendance.check.tsx
// DONE WHEN:  toggling a student on the detail screen shows the new status when
//             you go back.

// STEP 10 (continued from ThemeContext.tsx): the theme provider
// WHAT:       Wrap the app in your ThemeProvider too, and make sure the
//             NavigationContainer is inside your providers.
// WHY:        Anything that needs the theme has to be underneath the provider,
//             and every screen is underneath the navigator — so the navigator
//             goes inside.
// CONCEPTS:   Provider composition, context and navigation
// READ:       Guide > Web Diamond > Resources #3
// CHECKED BY: your reviewer
// DONE WHEN:  the theme toggle in Settings changes the look of both tabs.

// STEP 7: Run it on a real phone (or an emulator)
// WHAT:       Start the app with `npx expo start` and open it on your phone
//             through Expo Go, or on an Android emulator or the iOS simulator.
//             Tap through the roster to a detail screen and back. Put
//             screenshots — or a short recording — in your pull request.
// WHY:        Everything looks fine in a checker. A phone is where you find out
//             that your text is clipped, your tap target is too small, or the
//             list sits under the notch. There is no substitute, which is why
//             this step is a person looking at a screen.
// CONCEPTS:   Expo Go, development builds, emulators, testing on real hardware
// READ:       Guide > Web Diamond > Resources #1
// CHECKED BY: your reviewer. Nothing else can check this one.
// DONE WHEN:  screenshots from a real device or emulator are in your PR.

// STEP 11: What surprised you?
// WHAT:       In your PR, write two or three sentences on what surprised you
//             about React Native compared with React on the web.
// WHY:        You have now written the same app twice, in two places. The
//             differences you noticed yourself are the ones you will remember —
//             and they are exactly what the next person needs warning about.
// CONCEPTS:   React vs React DOM vs React Native, what is the platform
// READ:       Guide > Web Diamond
// CHECKED BY: your reviewer
// DONE WHEN:  the paragraph is in your PR description.

export default function App() {
  return (
    <SafeAreaProvider>
      <View style={styles.placeholder}>
        <Text style={styles.title}>Attendance</Text>
        <Text style={styles.subtitle}>FRC 2658</Text>
        <Text style={styles.hint}>
          Nothing here yet. Start with src/navigation/types.ts — step 1.
        </Text>
      </View>
      <StatusBar style="auto" />
    </SafeAreaProvider>
  );
}

// This placeholder goes away as soon as you render your navigator instead.
const styles = StyleSheet.create({
  placeholder: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 24,
    backgroundColor: '#11151c',
    gap: 6,
  },
  title: { color: '#e6ebf2', fontSize: 28, fontWeight: '700' },
  subtitle: { color: '#93a1b5', fontSize: 16 },
  hint: { color: '#5b6878', fontSize: 13, marginTop: 16, textAlign: 'center' },
});
