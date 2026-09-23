# Web Platinum — Attendance Dashboard

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Web Gold (React fundamentals)

The roster no longer sits in a file — you have to go and get it. That one change brings
everything else with it: waiting, failing, and a race condition you have to think about.

Then you add the things that make it a dashboard: live counts, a theme that reaches the whole
app without being passed down, and a data layer you can swap out without touching anything
else.

Guide: **GUIDE_URL** (section "Web Platinum")

---

## Skills required

Everything from Gold, plus:

- `useEffect` and the mount lifecycle
- Custom hooks
- Loading and error states
- Effect cleanup, and the race it prevents
- `createContext` / `useContext`
- Derived state — calculating instead of storing
- TanStack Query

---

## Setup

```bash
npm install
npm run dev
```

The roster is served from `public/roster.json`, so `npm run dev` makes a genuine HTTP request
for it. The checks intercept that request with MSW, which means they can make it slow, or make
it fail, without touching the network.

---

## Commands

```bash
npm run dev         # the dev server
npm run check       # rank checks + type checker + linter
npm run typecheck
npm run lint
```

---

## The files

```
public/roster.json                  the "server"
src/
├── main.tsx                        plumbing — don't edit
├── index.css                       finished styles, both themes
├── App.tsx                         steps 2, 3, 4, 6, 7
├── hooks/useRoster.ts              steps 1, 5, 8
├── context/ThemeContext.tsx        step 6
├── components/
│   ├── AttendanceCard.tsx          step 7 — copy your own from Gold
│   └── AttendanceList.tsx          step 7 — copy your own from Gold
└── data/roster.ts                  types and the endpoint — don't edit
checks/                             the grader. Don't edit it.
```

---

## Two steps want their own commit

**Step 5** (effect cleanup) and **step 8** (the TanStack Query swap) are each meant to be a
separate commit with a message saying what changed. Your reviewer reads the history for those
two — a single "did everything" commit makes them impossible to review.

---

## Steps

**STEP 1 — `useRoster()` in `src/hooks/useRoster.ts`.**
Fetches from `ROSTER_URL`, returns `{ students, isLoading, error }` with `error` typed
`string | null`.
*Checked by: dashboard.check.tsx.*

**STEP 2 — A loading state** while the request is in flight.
*Checked by: dashboard.check.tsx.*

**STEP 3 — An error state** when it fails, with a message a person can act on.
*Checked by: dashboard.check.tsx.*

**STEP 4 — The summary: `3 of 12 present`**, plus a count per status. **Calculate during
render.** Don't store them in state.
*Checked by: dashboard.check.tsx, sourceScan.check.tsx.*

**STEP 5 — Clean up the effect** so a late response is ignored. An `ignore` flag or an
`AbortController`. **Its own commit.**
*Checked by: sourceScan.check.tsx, and your reviewer in the history.*

**STEP 6 — `ThemeContext`.**
Export `ThemeProvider` and a `useTheme()` hook returning `{ theme, toggleTheme }`. `useTheme`
throws outside the provider. Put `data-theme={theme}` on the outer element, and the toggle
button in a **grandchild** — so the value genuinely travels without a prop.
*Checked by: theme.check.tsx, sourceScan.check.tsx.*

**STEP 7 — Bring your Gold card and list across**, and keep the attendance state in `App`.
*Checked by: dashboard.check.tsx.*

**STEP 8 — Swap `useRoster`'s insides for TanStack Query.**
Same return shape, so nothing else changes. Wrap the app in a `QueryClientProvider`. **Its own
commit**, and say in your PR what got simpler.
*Checked by: every check from step 1 onward, which must still pass.*

---

## Passes when

- Counts update **instantly** on toggle.
- Loading **and** error states both show up.
- No value is stored in state that could be calculated instead.
- `npm run check` passes — both before and after step 8.

---

## Resources

1. [React: Choosing the State Structure](https://react.dev/learn/choosing-the-state-structure)
2. [React: Synchronizing with Effects](https://react.dev/learn/synchronizing-with-effects)
3. [React: Passing Data Deeply with Context](https://react.dev/learn/passing-data-deeply-with-context)
4. [TanStack Query: Queries](https://tanstack.com/query/latest/docs/framework/react/guides/queries)

Numbers match the guide's "Web Platinum" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **The effect runs forever.** Your dependency array is missing, so it re-runs after every
  render, which sets state, which renders again. An empty `[]` means "once, on mount".
- **"Can't perform a React state update on an unmounted component."** That's step 5.
- **The counts don't move when you toggle.** They're in state. Delete that state and work them
  out during render.
- **`useTheme` returns undefined.** The component is outside the provider. That's exactly what
  the throw in step 6 is for.
- **After step 8, the error state takes forever.** TanStack Query retries three times by
  default. Turn retries off on the `QueryClient`.
- **After step 8, "No QueryClient set".** The `QueryClientProvider` needs to be above anything
  calling `useRoster`.
