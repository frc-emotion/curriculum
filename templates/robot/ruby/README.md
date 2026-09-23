# Robot Ruby — Contributing to Rebuilt-2026

**Track:** Robot (Java / WPILib)
**Builds on:** Robot Emerald (PID and setpoints)

There is no template for this rank. You are working in the team's actual competition code
now: [`frc-emotion/Rebuilt-2026`](https://github.com/frc-emotion/Rebuilt-2026).

Everything you have built so far was a rehearsal with the stakes removed. This is the real
repo, on the real robot, with other people depending on it.

Guide: **GUIDE_URL** (section "Robot Ruby")

---

> ## ⚠️ Safety
>
> Your code will run on a robot that weighs more than you do.
>
> - **Nothing goes on the robot without a lead present.** Ever.
> - **Sim first, always.** If it hasn't run in simulation, it doesn't go on hardware.
> - **Never deploy during a match or on a robot someone is working on.** Say out loud that
>   you are deploying, and wait for an answer.
> - If you are not sure whether something is safe, it isn't. Ask.

---

## What you are being assessed on

Not "can you write Java" — you proved that at Emerald. This rank is about working *inside*
somebody else's codebase without breaking it, and being useful to the people around you.

---

## Part 1 — Your first contribution

1. **Read the repo before you change it.** Write a short map for yourself: where do
   subsystems live, where do commands live, what is in `Constants`, what does
   `RobotContainer` wire together? Put that map in your PR description.
2. **Claim a `good first issue`.** Comment on it so nobody doubles up. If there isn't one
   open, ask a lead — do not invent your own scope for this part.
3. **Branch** as `<your-username>/<short-description>`.
4. **Make the change.** Small. Only what the issue asks for.
5. **`./gradlew build` must pass**, and it must run in simulation.
6. **Open a PR** saying what you changed, why, and how you tested it. Include
   `Closes #<issue>` so the issue closes itself when you merge.

## Part 2 — Something bigger

1. Ask a lead for an issue that takes more than one sitting.
2. **Comment your plan on the issue before you write any code.** How you will break it up,
   what you will test, what you are unsure about. Wait for a reply.
3. Split the work into **small PRs**, each one reviewable on its own.
4. Test in simulation, then on hardware with a lead.

## Part 3 — Review two PRs

Review two lower-rank PRs from other students. For each one: read that rank's "Passes when"
in [REVIEWING.md](../../../REVIEWING.md), check the code against it, and leave **at least one
specific suggestion**. "Looks good" is not a review.

---

## Passes when

- Your issue is **closed by a merged PR you wrote**.
- It was tested in simulation **and** on hardware.
- You have reviewed **two** lower-rank PRs usefully.
- You did not modify anything outside the scope of your issue.

---

## Things that get a PR sent back

- Formatting changes mixed in with real changes. Nobody can review a 400-line diff where 380
  lines are whitespace.
- Constants typed inline instead of added to `Constants`.
- A subsystem's hardware exposed so a command can reach it directly.
- "I tested it" with no description of what you actually did.
- Force-pushing over review feedback so the history disappears.

---

## Resources

1. [GitHub: Contributing to a project](https://docs.github.com/en/get-started/exploring-projects-on-github/contributing-to-a-project)
2. [WPILib: Command-Based Programming](https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html)
3. [Rebuilt-2026](https://github.com/frc-emotion/Rebuilt-2026)

Full guide: **GUIDE_URL**

---

## After Ruby

Ruby is the last rank, which mostly means the training is over and the work isn't. Teach
somebody Copper. Review the PRs. Pick up the issues nobody wants. That is what being a lead
actually is.
