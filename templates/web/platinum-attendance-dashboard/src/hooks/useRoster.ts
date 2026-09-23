// ============================================================
// RANK:        Web Platinum: Attendance Dashboard
// FILE:        src/hooks/useRoster.ts
// STEPS HERE:  1, 5, 8
// GUIDE:       GUIDE_URL  (section "Web Platinum")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: counts update instantly on toggle, loading and error states both
//              show up, and no value is stored in state that could be
//              calculated instead.
// ============================================================
//
// A custom hook is just a function whose name starts with `use` and which calls
// other hooks. That is the entire rule. What it buys you is a place to put
// "how we get the roster" so that no component has to know or care.
//
// This is the same instinct as api.ts at Iron, and the same instinct as a
// subsystem on the robot track: one owner for one job.
//
// Imports you will need as you go (add them yourself):
//   import { useEffect, useState } from 'react';
//   import { ROSTER_URL, type Student } from '../data/roster.ts';

// STEP 1: Write useRoster
// WHAT:       Export a hook `useRoster()` that fetches the roster from
//             ROSTER_URL and returns an object with exactly these three
//             properties:
//               students   Student[]          (empty while loading or failed)
//               isLoading  boolean
//               error      string | null
//             Fetch once when the component mounts. Type the error as
//             `string | null`, not `any`.
// WHY:        Every screen that loads anything has these three states, and
//             users notice when you skip two of them. Returning all three from
//             one hook means a component can render the right thing without
//             knowing anything about HTTP.
//             `string | null` rather than `any` is deliberate: the component
//             needs something it can show a person, so turn the failure into a
//             sentence here, where you still know what was being fetched.
// CONCEPTS:   Custom hooks, useEffect, useState, the mount lifecycle, loading
//             and error states, typed returns
// READ:       Guide > Web Platinum > Resources #1 and #2
// CHECKED BY: dashboard.check.tsx (loading, loaded and failed all render)
// DONE WHEN:  the dashboard shows a loading message, then the roster; and shows
//             an error message when the request fails.

// STEP 5: Ignore responses that arrive too late
// WHAT:       Add cleanup to your effect so that if the component unmounts (or
//             the effect re-runs) before the response arrives, the late response
//             is ignored instead of being written into state.
//             Two ways to do it: a boolean `ignore` flag that the cleanup
//             function flips, or an AbortController. Either is fine. Pick one
//             and be able to explain it.
//             COMMIT THIS ON ITS OWN, with a message saying what it fixes. Your
//             reviewer reads the commit history for this step.
// WHY:        Responses come back in whatever order the network feels like. A
//             user who opens a screen, leaves, and comes back can end up looking
//             at the FIRST request's data, arriving after the second. React also
//             warns about setting state on an unmounted component, and this is
//             the fix people paste from Stack Overflow without understanding —
//             so understand it.
// CONCEPTS:   useEffect cleanup functions, race conditions, AbortController,
//             component unmounting
// READ:       Guide > Web Platinum > Resources #2
// CHECKED BY: sourceScan.check.tsx (the effect must return a cleanup function),
//             your reviewer (in the commit history)
// DONE WHEN:  the effect returns a cleanup function, and you can explain out
//             loud which bug it prevents.

// STEP 8: Swap the inside for TanStack Query
// WHAT:       Rewrite the INSIDE of useRoster to use TanStack Query's useQuery
//             instead of useEffect and useState. Keep the return shape exactly
//             the same — `{ students, isLoading, error }` — so that nothing
//             else in the app has to change.
//             You will need to wrap the app in a QueryClientProvider; the
//             natural home for it is main.tsx or App.tsx.
//             One gotcha: by default TanStack Query retries a failed request
//             three times before giving up, so your error state takes a few
//             seconds to appear and the checks time out waiting for it. Turn
//             retries off on the QueryClient while you are learning.
//             COMMIT THIS SEPARATELY, and say in your PR what got simpler.
// WHY:        This is the payoff for step 1. Because every component only ever
//             talked to `useRoster`, you can replace the entire data layer and
//             touch one file. That is what a good boundary buys you, and it is
//             worth feeling once.
//             TanStack Query is also what nautilus-frontend uses, so the hook
//             you end up with is the shape you will meet there.
// CONCEPTS:   TanStack Query, useQuery, queryKey, QueryClientProvider,
//             refactoring behind a stable interface
// READ:       Guide > Web Platinum > Resources #4
// CHECKED BY: dashboard.check.tsx (the same checks must still pass), your
//             reviewer (the diff should touch almost nothing else)
// DONE WHEN:  every check that passed before still passes, and the change is
//             its own commit.

export {};
