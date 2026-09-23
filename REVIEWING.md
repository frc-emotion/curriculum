# Reviewing Rank-Up PRs

For leads. This is the checklist you review against, rank by rank.

Guide: **GUIDE_URL**

---

## How to review

1. **Read the PR description first.** Several steps are only ever checked by a human — the
   explanations, the pasted output, the screenshots. If those are missing, ask for them before
   you read any code.
2. **Let CI run the checks.** You do not need to run the code yourself unless CI is red and you
   want to see why, or the rank needs a hardware demo.
3. **Read the "Passes when" line below.** That is the bar. Not "is this how I would write it."
4. **Ask the cumulative questions.** Ranks are cumulative, so a student at Diamond is still
   responsible for Gold. Pick one or two from the list — in a PR comment or out loud at a
   meeting. A student who cannot explain their own code has not earned the rank, even if the
   checks are green.
5. **Leave at least one specific, kind suggestion.** "Nice work" is not a review.

### Running a check yourself

```bash
git fetch origin pull/<PR-NUMBER>/head:review-pr
git switch review-pr
cd students/<username>/<track>/<rank>
```

Then the rank's check command: `./gradlew rankCheck` for Java and WPILib ranks,
`npm install && npm run check` for web ranks.

### A note on AI-written code

Checks catch wrong code. They do not catch code a student did not write. The cumulative
questions are the real defense. If the code is flawless and the student cannot explain a
single line of it, that is your signal — and it is a teaching moment, not a punishment.

---

## Unranked — Git and Workflows

**Passes when:** the PR is merged, nobody else's roster lines were deleted, and every commit
message says what changed.

**Automated:** CI checks that the PR adds exactly one new file in `unranked/members/`, named
`<github-username>.md` in lowercase, with all three headings filled in, and that `ROSTER.md`
gained exactly one row and lost none.

**You check:**
- Commit messages actually describe the change ("add my member file", not "update" or "asdf").
- Step 8: they left a useful comment on a classmate's PR. Useful means specific.
- Step 9: if you left feedback, they addressed it with a **follow-up commit** rather than a
  force-push that hides the history.

**Cumulative questions:** none. This is the first rank.

---

# Robot track

## Robot Copper — Joystick Direction Decider

**Passes when:** the output is correct for every test value, including exactly 0.1 and -0.1,
and constants use `final` instead of repeated numbers.

**You check:**
- Step 8: the pasted console output is in the PR and matches what the code would really print.
- The deadband and max speed are named constants, not magic numbers sprinkled around.

**Cumulative questions (from Unranked):**
1. Why do we work on a branch instead of committing straight to `main`?
2. What does a commit actually record — the whole project, or just what you changed?
3. Someone else edited `ROSTER.md` after you branched. What happens when you open your PR,
   and how would you fix it?

## Robot Iron — Motor Ramp Simulator

**Passes when:** both ramps stop exactly at their targets, no logic is copy-pasted where a
method call would work, and each method does one clearly named job.

**You check:**
- Step 9: both ramp outputs are pasted in the PR.
- `setMotorSpeed(double, int)` really reuses `clamp` instead of repeating the clamping logic.
- Method names say what they do. `doStuff` fails this rank.

**Cumulative questions (from Copper):**
1. Why is `DEADBAND` a constant instead of typing `0.1` everywhere you need it?
2. What does `Math.abs` do for us in `decideDirection`, and what breaks without it?
3. Walk me through what your ternary does. Now write the same thing as an `if`/`else`.

## Robot Gold — Mock Robot Hardware

**Passes when:** all fields are private, no port numbers appear outside `Constants`, and the
student can explain in review what the lambda and method reference are doing.

**You check:**
- Step 10: the class-vs-object analogy is in the PR description and is actually theirs.
- They can point at `() -> intake.run()` and `shooter::stop` and say what each one is.
- `Intake` and `Shooter` genuinely share code through `MockSubsystem` instead of duplicating it.

**Cumulative questions (from Copper and Iron):**
1. Why is `setSpeed` clamping at all? What would a real motor do with 1.5?
2. Your `Motor` fields are private. What would break if they were public?
3. You wrote `clamp` at Iron and used the idea again here. What makes something worth being
   its own method?

## Robot Platinum — Bench Motor Control

> **Safety:** this rank ends with a hardware demo. Bench testing only, motor secured, lead
> present, low speed first. Do not sign this off from a video.

**Passes when:** the sim demo and bench demo both work, no numbers are hard-coded outside
`Constants`, and the motor stops when disabled.

**You check:**
- Step 1: every lifecycle method has a comment saying when it runs and how often, and those
  comments are correct.
- Step 2: console output from switching Disabled / Autonomous / Teleop is pasted in the PR.
- Step 8: you watched the bench demo yourself.
- The CI source scans are **warnings**, not failures — read them and judge.
- Disabled really does stop the motor. Test it.

**Cumulative questions (from Gold):**
1. `Constants` holds the CAN ID. Why not just type the number where the motor is created?
2. What is the difference between the `TalonFX` class and the `TalonFX` object you made?
3. Which of your methods run once, and which run fifty times a second? How do you know?

## Robot Diamond — Simple Motor Subsystem

> **Safety:** bench testing only, lead present, low speed first.

**Passes when:** nothing outside the subsystem touches the `TalonFX` directly, the limit
switch reliably blocks forward motion, and all bindings live in `RobotContainer`.

**You check:**
- Step 10: you watched the sim and the bench test, including turning the shaft by hand and
  pressing the limit switch.
- Step 11: the subsystem-vs-command explanation is in the PR and makes sense.
- `sim/MotorSimHelper.java` is unmodified. It is not part of the assessment.
- The default command has a real deadband, so the motor is quiet at rest.

**Cumulative questions (from Gold, Platinum, Iron):**
1. Why is the `TalonFX` field private? (Gold)
2. Where does `periodic()` get called from, and how often? (Platinum)
3. Your deadband is back. Why does a joystick need one at all? (Iron / Copper)

## Robot Emerald — Arm to Presets

> **Safety:** a gravity-loaded arm can fall. Hard stops set, lead present, low `kP` first,
> hands clear before enabling.

**Passes when:** the arm reaches each preset without sustained oscillation, commands end at
their setpoints, and the angle limits hold.

**You check:**
- Step 3: three `kP` values — overshoot, oscillation, final — each with what they observed.
- Step 9: you were there for the hardware retune.
- Step 10: three things learned, three things to explore next.
- Step 8: their Diamond subsystem is actually theirs, copied from their own merged folder.
- `sim/ArmSimHelper.java` is unmodified.

**Cumulative questions (from Diamond, Platinum, Gold):**
1. Why does the arm need a gravity term when a flat drivetrain motor does not?
2. What happens if `setSetpoint` is called with 200 degrees? Show me. (Diamond / Platinum)
3. Your command finishes at the setpoint. What actually ends a command? (Diamond)

## Robot Ruby — Contributing to Rebuilt-2026

**Passes when:** their issue is closed by a merged PR they wrote, tested in sim and on
hardware, and they have reviewed two lower-rank PRs usefully.

**You check:**
- Part 1: repo map, a claimed `good first issue`, correct branch name, `./gradlew build`
  passing, a PR that says what / why / how tested with `Closes #<issue>`.
- Part 2: a plan commented on the issue **before** any code, split into small PRs.
- Part 3: two lower-rank reviews, each with at least one specific suggestion, judged against
  that rank's "Passes when".
- They did not modify anything outside the scope of their issue.

**Cumulative questions (from Emerald and below):**
1. Walk me through how a button press in `RobotContainer` reaches a motor.
2. Something you changed broke in a match. How would you find it?
3. Why did you split this into three PRs instead of one?

---

# Web track

## Web Copper — Student Filter

**Passes when:** all checks pass, there is no `var` or `==`, and the original `students`
array is never modified.

**You check:**
- Step 10: `npm run check` really passes — CI will tell you.
- They used the array method that fits (`.filter`, `.map`, `.find`), not a loop wearing a
  costume.
- Nothing mutates the input. The checks freeze the input arrays, but read the code too.

**Cumulative questions (from Unranked):**
1. Why do we work on a branch instead of committing straight to `main`?
2. What is in a commit — the whole project or just the change?
3. Your PR shows a file you did not mean to touch. How did that happen and how do you fix it?

## Web Iron — Typed Student Tracker

**Passes when:** the project compiles in strict mode, the API data is validated before use,
and a failed request is handled cleanly.

**You check:**
- No `any` anywhere, and no `@ts-ignore` smuggled in to silence the compiler.
- The zod schema actually validates before the data is used, not after.
- Step 8: a real network failure prints a friendly message instead of a stack trace.

**Cumulative questions (from Copper):**
1. Why `.filter` here and `.find` there?
2. What does `??` do that `||` does not? Give me a case where it matters.
3. You copied your Copper functions and added types. Did any of them turn out to be wrong
   once TypeScript looked at them?

## Web Gold — Attendance Card

**Passes when:** the cards toggle correctly, the filter works, there are no key warnings in
the console, and state is never mutated directly.

**You check:**
- Step 8: the click-to-screen-update explanation is in the PR, in their own words.
- No `key={index}` if the list can reorder — ask them why that matters.
- State updates create new objects and arrays rather than editing the old ones.

**Cumulative questions (from Iron and Copper):**
1. What type are these props, and what would happen if you passed a number for `status`?
2. Your `.map` builds the list. What is the `key` for, really?
3. Why does React re-render when state changes but not when a plain variable changes?

## Web Platinum — Attendance Dashboard

**Passes when:** counts update instantly on toggle, loading and error states both show up,
and no value is stored in state that could be calculated instead.

**You check:**
- Step 5 is **yours to check in the commit history** — the stale-response cleanup should be a
  real commit, not an afterthought.
- Step 8 is a **separate commit** that swaps `useRoster` to TanStack Query with the same
  return shape, and the PR says what got simpler.
- The "X of Y present" count is computed during render, not stored in state.

**Cumulative questions (from Gold and Iron):**
1. You moved status out of the card and into the list. What broke, and why did lifting fix it?
2. When does your effect run, and when does it run again?
3. What is the error type here, and why `string | null` instead of `any`?

## Web Diamond — Attendance App

**Passes when:** navigation works both ways with params, attendance persists after a restart,
and there are no TypeScript errors in the navigation types.

**You check:**
- Step 7: screenshots or a recording from a real phone or emulator.
- Kill the app and reopen it — attendance really survives.
- `StudentDetailScreen` uses NativeWind `className`, and the card uses `StyleSheet`. Both
  styling approaches appear, on purpose.
- Route params are typed. `any` in `RootStackParamList` fails this rank.

**Cumulative questions (from Platinum and Gold):**
1. What replaced `<div>` and `<button>` here, and why can't you just use them?
2. Your theme came from Platinum's context. What does context save you from doing?
3. `FlatList` instead of `.map`. What does it do differently with 500 students?

## Web Emerald — First contribution to nautilus-frontend

**Passes when:** their issue is closed by a merged PR, built with existing gluestack-ui
components and NativeWind classes, tested on a device.

**You check:**
- Repo map in the PR description, a claimed `good first issue`, correct branch name.
- Before/after screenshots and `Closes #<issue>`.
- They reused existing components instead of inventing a parallel design system.

**Cumulative questions (from Diamond and below):**
1. Where do screens live in this repo, and where do shared components live?
2. This screen fetches data. Where does that call live, and why not in the component?
3. Why does this repo use a development build instead of Expo Go?

## Web Ruby — Owning a feature in nautilus-frontend

**Passes when:** a feature-sized issue is merged with loading, empty and error states, split
into small linked PRs, tested on iOS and Android, plus two lower-rank reviews.

**You check:**
- Part 1: lead-approved issue, a plan commented **before** coding, all three UI states present,
  small linked PRs, screenshots or a recording from both platforms.
- Part 2: two lower-rank reviews with at least one specific suggestion each.

**Cumulative questions (from Emerald and below):**
1. What happens in your feature when the network is slow, empty, and broken?
2. Why did you split this into small PRs? What would have gone wrong with one big one?
3. Teach me the thing you learned that you wish you'd known at Gold.
