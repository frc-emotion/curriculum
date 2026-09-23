# Web Ruby — Owning a feature in nautilus-frontend

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Web Emerald (first contribution to nautilus-frontend)

No template. At Emerald you made a change somebody else scoped for you. Here you own a
feature end to end — including deciding what "done" means and saying so before you start.

Guide: **GUIDE_URL** (section "Web Ruby")

---

## Part 1 — Own a feature

1. **Agree the issue with a lead first.** Feature-sized, not bug-sized: something a user would
   notice and describe in a sentence.
2. **Comment your plan on the issue before writing any code.** How you'll break it up, which
   screens and components you'll touch, what you're unsure about. Wait for a reply. This is
   the step people skip, and it is the one that saves a week.
3. **Build all three states.** Every screen that loads anything has them:
   - **loading** — something on screen while you wait
   - **empty** — a sentence when there is nothing to show
   - **error** — what went wrong and what to do about it

   You built exactly these at Platinum. They are not optional here.
4. **Split it into small, linked PRs.** Each one should make sense on its own and leave the
   app working. Link them to the issue.
5. **Test on iOS and Android.** They differ, usually in layout and in keyboard behaviour.
   Screenshots or a recording from both, in your PRs.

## Part 2 — Review two PRs

Two lower-rank PRs, each with at least one specific suggestion, judged against that rank's
"Passes when" in [REVIEWING.md](../../../REVIEWING.md).

---

## Passes when

- A feature-sized issue is **merged**, with loading, empty and error states.
- The work is split into **small linked PRs**, not one big one.
- Tested on **iOS and Android**, with evidence.
- You reviewed **two** lower-rank PRs usefully.

---

## Resources

1. [nautilus-frontend](https://github.com/frc-emotion/nautilus-frontend)
2. [React Native: Platform-specific code](https://reactnative.dev/docs/platform-specific-code)
3. [GitHub: About pull requests](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests)

Full guide: **GUIDE_URL**

---

## After Ruby

You've finished the track. The useful thing to do now is make the next person's version of it
shorter: teach Copper, review the PRs nobody has picked up, and write down the thing that
confused you most so the guide can say it better.
