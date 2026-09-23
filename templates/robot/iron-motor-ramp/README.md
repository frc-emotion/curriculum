# Robot Iron — Motor Ramp Simulator

**Track:** Robot (Java / WPILib)
**Builds on:** Robot Copper (variables, conditionals, operators)

Slamming a motor from 0 to full power strips gears, browns out the radio, and tips robots
over. Real code *ramps*: it walks the speed up a step at a time. You're building a simulator
for that ramp, so you can watch the numbers without breaking anything expensive.

At Copper, the method signatures were written for you. Not here. **Writing the method is the
skill this rank is about** — the name, what it takes, what it gives back.

Guide: **GUIDE_URL** (section "Robot Iron")

---

## Skills required

Everything from Copper, plus:

- `for` and `while` loops
- `break`
- Writing your own methods: parameters, return types, `void`
- Method overloading
- Arrays and `double[]`
- `ArrayList<Double>`
- Nested loops

---

## Setup

Same as Copper: a Java 17 JDK, easiest via the
[WPILib installer](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html).

```bash
./gradlew run         # run your program
./gradlew rankCheck   # grade your program
```

Windows: `.\gradlew.bat run`.

The untouched template runs and prints nothing — `main` is empty until you fill it in. The
check fails with a list of every method it can't find yet. That list is your to-do list.

---

## Getting the signatures right

The checks find your methods **by name and parameter types**. If you write `Clamp` instead of
`clamp`, or take an `int` where the check passes a `double`, the check will tell you it
couldn't find the method. Copy the signatures out of the STEP comments exactly.

You'll also need this import at the top of the file, under `package rankup;`:

```java
import java.util.ArrayList;
```

---

## Steps

**STEP 1 — `static boolean isWithinDeadband(double value, double deadband)`**
True when the value is within `deadband` of zero. Exactly on the edge counts as inside.
*Checked by: DeadbandCheck.*

**STEP 2 — `static double clamp(double value, double min, double max)`**
Returns the value, never below `min`, never above `max`.
*Checked by: ClampCheck.*

**STEP 3 — `static void setMotorSpeed(double speed)`**
Clamps to `-1.0 .. 1.0` **by calling `clamp`**, prints `Motor set to <speed>`, and adds the
clamped value to `speedLog`.
*Checked by: SetMotorSpeedCheck.*

**STEP 4 — Overload: `static void setMotorSpeed(double speed, int port)`**
Prints `Motor <port> set to <speed>`, also logs, and **must reuse `clamp`** rather than
repeating it.
*Checked by: SetMotorSpeedCheck.*

**STEP 5 — `static ArrayList<Double> rampUp(double target, double step)`**
Start at 0, add `step` each time round, call `setMotorSpeed` with each new speed, and return
the list of speeds. First element is `step`. Last element is **exactly** `target` — shorten
the final step and `break` rather than overshooting.

```
rampUp(1.0, 0.25)  ->  [0.25, 0.5, 0.75, 1.0]
rampUp(0.9, 0.2)   ->  [0.2, 0.4, 0.6, 0.8, 0.9]
```

*Checked by: RampUpCheck, SourceScanCheck.*

**STEP 6 — `static ArrayList<Double> speedLog`**
A class-level list that every `setMotorSpeed` call appends to. `main` prints its size at the
end.
*Checked by: SetMotorSpeedCheck, SourceScanCheck.*

**STEP 7 — Arrays: `static double average(double[] speeds)` and `static double max(double[] speeds)`**
In `main`, make a `double[]` of 4 swerve module speeds and call both.
*Checked by: ArrayMathCheck.*

**STEP 8 — `static void printRampGrid(int motors, int steps)`**
Nested loops, one line per motor, numbered from 1:

```
Motor 1: 0.2 0.4 0.6 0.8 1.0
Motor 2: 0.2 0.4 0.6 0.8 1.0
Motor 3: 0.2 0.4 0.6 0.8 1.0
```

`main` calls it with `3` and `5`.
*Checked by: RampGridCheck, SourceScanCheck.*

**STEP 9 — Run both ramps.**
`main` runs `rampUp(1.0, 0.25)` and `rampUp(0.9, 0.2)`. Paste both outputs into your PR.
*Checked by: your reviewer.*

---

## Passes when

- Both ramps stop **exactly** at their targets.
- No logic is copy-pasted where a method call would work (look at step 4).
- Each method does one clearly named job.
- `./gradlew rankCheck` passes.
- Both ramp outputs are pasted in your PR.

---

## Resources

1. [Oracle: The for Statement](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html)
2. [Oracle: The while and do-while Statements](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html)
3. [Oracle: Defining Methods](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)
4. [Oracle: Arrays](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html)

Numbers match the guide's "Robot Iron" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **"I couldn't find a method called..."** — the name or the parameter types don't match.
  Compare yours against the STEP comment character by character.
- **The ramp overshoots.** Before you add a full step, ask what the value *would* be. If that
  is past the target, use the target instead.
- **`0.6000000000000001`.** That's normal: computers store decimals approximately. The checks
  allow a tiny difference, so don't chase it.
- **Nested loops confuse you.** Print something in the outer loop and something else in the
  inner one, and watch the order they come out.
