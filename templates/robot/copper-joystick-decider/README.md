# Robot Copper — Joystick Direction Decider

**Track:** Robot (Java / WPILib)
**Builds on:** Unranked (Git and Workflows)

A driver pushes a joystick. Your code decides what that means: forward, backward, stop, or
"the robot is disabled, ignore everything." That decision is the first thing every teleop
program does, and it is where a surprising number of real bugs live.

No robot and no WPILib yet — this is plain Java you can run on any laptop.

Guide: **GUIDE_URL** (section "Robot Copper")

---

## Skills required

- Variables and the primitive types (`double`, `int`, `boolean`) plus `String`
- Constants with `final`
- `if` / `else if` / `else`
- Comparison operators and `Math.abs`
- The ternary operator `? :`
- `switch`
- Printing with `System.out.println`

---

## Setup

You need a **Java 17 JDK**. The easiest way to get one, and the one you'll want anyway for
later ranks, is the WPILib installer — it bundles the right JDK and VS Code extensions.

1. Install WPILib 2026: [WPILib Installation Guide](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)
2. Open this folder in VS Code.
3. Check it works:

```bash
./gradlew run
```

Windows: use `.\gradlew.bat run` instead. The first run downloads Gradle and takes a minute.

An untouched template runs and prints nothing. That is correct — `main` is empty until you
write step 1.

---

## Commands

```bash
./gradlew run         # run your program
./gradlew rankCheck   # grade your program
```

`rankCheck` fails right now, on purpose. Every failure starts with a step number:

```
STEP 6: expected a ternary operator (? :) in decideDirection
```

That's your to-do list. Work the lowest number first.

---

## About the three methods

`JoystickDecider.java` already contains three method signatures:

```java
static String decideDirection(double joystickValue, boolean isEnabled)
static double scaleSpeed(double joystickValue)
static String driveModeName(int driveMode)
```

You have not been taught to write methods yet — that is Iron. Here, you only fill in the
bodies. Don't rename them and don't change what they take or return; the checks call them by
name.

---

## Steps

**STEP 1 — Declare and print four variables.**
In `main`, declare `motorSpeed` (double), `motorPort` (int), `isEnabled` (boolean) and
`robotName` (String), then print each one with a label.
*Checked by: your reviewer.*

**STEP 2 — Declare `DEADBAND`.**
A class-level `static final double` equal to `0.1`. (`static` is explained at Gold. For now it
means the constant belongs to the class, so every method can see it.)
*Checked by: SourceScanCheck.*

**STEP 3 — Fill in `decideDirection`.**
Return `"FORWARD"` above the deadband, `"BACKWARD"` below the negative deadband, and `"STOP"`
otherwise, using `Math.abs`. **Exactly `0.1` and exactly `-0.1` are `"STOP"`.**
*Checked by: DecideDirectionCheck, SourceScanCheck.*

**STEP 4 — Handle the disabled case.**
When `isEnabled` is false, return `"DISABLED"` no matter what the joystick says.
*Checked by: DecideDirectionCheck.*

**STEP 5 — Declare `MAX_SPEED` and fill in `scaleSpeed`.**
`MAX_SPEED` is a `static final double` equal to `0.8`. `scaleSpeed` returns the joystick value
times `MAX_SPEED`. Print a scaled value in `main`.
*Checked by: ScaleSpeedCheck, SourceScanCheck.*

**STEP 6 — Rewrite the STOP check as a ternary.**
Same behaviour, one line, using `? :`.
*Checked by: SourceScanCheck, DecideDirectionCheck.*

**STEP 7 — Fill in `driveModeName` with a `switch`.**
`0` is `"TANK"`, `1` is `"ARCADE"`, anything else is `"UNKNOWN"`.
*Checked by: DriveModeNameCheck, SourceScanCheck.*

**STEP 8 — Call `decideDirection` for every test value.**
One call per line for `0.5`, `-0.8`, `0.05`, `0.1`, `-0.1`, `1.0`, and paste the output into
your PR. (Loops are Iron. Write them out.)
*Checked by: your reviewer.*

---

## Passes when

- The output is correct for every test value, **including exactly 0.1 and -0.1**.
- Constants use `final` instead of repeated numbers.
- `./gradlew rankCheck` passes.
- Your PR contains the pasted output from step 8.

---

## Resources

1. [WPILib Installation Guide](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)
2. [Oracle: Variables](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html)
4. [Oracle: if-then and if-then-else](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html)
7. [Oracle: switch](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html)
14. [Programiz: Java Ternary Operator](https://www.programiz.com/java-programming/ternary-operator)

The numbers match the Resources list in the guide's "Robot Copper" section, so #4 here is #4
there. Full guide: **GUIDE_URL**

---

## Stuck?

- Read the first failure only. Fix it. Run again.
- `Math.abs(-0.3)` is `0.3`. That's how one comparison can cover both directions.
- "Exactly 0.1 is STOP" is a hint about which comparison operator you need.
- Still stuck after a real try? Ask in the software channel and show what you tried.
