/* Grader setup. Part of the grader — don't edit.
   Two stand-ins so the checks can run without a phone:
     - AsyncStorage becomes an in-memory store
     - SafeAreaProvider gets fixed screen insets. It normally waits for a real
       layout pass before rendering anything, and a test never has one, so
       without this the whole app renders as an empty box. */
import type { ReactNode } from 'react';

jest.mock('@react-native-async-storage/async-storage', () =>
  // eslint-disable-next-line @typescript-eslint/no-require-imports
  require('@react-native-async-storage/async-storage/jest/async-storage-mock'),
);

jest.mock('react-native-safe-area-context', () => {
  const actual = jest.requireActual('react-native-safe-area-context');
  const insets = { top: 0, right: 0, bottom: 0, left: 0 };
  const frame = { x: 0, y: 0, width: 390, height: 844 };
  return {
    ...actual,
    SafeAreaProvider: ({ children }: { children: ReactNode }) => children,
    SafeAreaInsetsContext: {
      ...actual.SafeAreaInsetsContext,
      Consumer: ({ children }: { children: (i: typeof insets) => ReactNode }) => children(insets),
    },
    useSafeAreaInsets: () => insets,
    useSafeAreaFrame: () => frame,
    initialWindowMetrics: { insets, frame },
  };
});
