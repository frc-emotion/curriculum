# Robot Emerald — Arm to Presets

**Track:** Robot (Java / WPILib)
**Builds on:** Robot Diamond (sensors, subsystems, commands)

An arm is the first mechanism where "set the motor to 0.3" stops working. Gravity pulls on
it, and how hard depends on where it is pointing. It has to *hold a position*, not a speed.

That's what a controller is for. You'll build one from the bottom up: first watch a P
controller push the arm around and tune it by feel, then add gravity compensation, then wire
three driver presets on top.

This rank comes in three parts. Don't try to do it in one sitting.

Guide: **GUIDE_URL** (section "Robot Emerald")

---

> ## ⚠️ Safety
>
> **A gravity-loaded arm can fall.** This is the most dangerous mechanism you've worked on.
>
> - **Hard stops fitted and checked** before the arm is ever powered.
> - **A lead present** for every hardware test. Every one.
> - **Start with a low `kP`.** A high one turns a small error into a fast swing.
> - **Hands clear before anyone enables.** An arm holding position is still under power, and
>   it will move the instant the setpoint changes.
> - **Tune in simulation first.** All of part 1 and part 2 happen in sim. The hardware retune
>   in step 9 is the *last* thing, not the first.
> - If the arm starts oscillating, **disable first**, then talk about it.

---

## Skills required

Everything from Diamond, plus:

- PID control, starting with P alone
- `PIDController`, tolerance and `atSetpoint()`
- Feedforward, and why gravity needs one
- Live tuning through SmartDashboard
- Gear ratios and unit conversion
- Writing a command as a class
- Software limits
- Running two subsystems at once

---

## The files

```
src/main/java/frc/robot/
├── Main.java, Robot.java                  plumbing, filled in for you
├── RobotContainer.java                    steps 7, 8
├── Constants.java                         steps 4, 6, 7
├── subsystems/ArmSubsystem.java           steps 1, 4, 5, 6
├── commands/PTuneCommand.java             steps 1, 2, 3
└── sim/ArmSimHelper.java                  DO NOT EDIT — finished, not assessed
```

`ArmSimHelper` simulates a real arm — gravity, inertia, hard stops — and draws it on
SmartDashboard under **"Arm"** so you can watch it move. **The arm's physical facts are
listed at the top of that file** (gear ratio, length, mass, hard stops). Those are
measurements, like reading a number off a real gearbox. Copy them into your `Constants`.

There is no `SimpleMotorSubsystem` here. Step 8 has you bring your own across from Diamond.

---

## Commands

```bash
./gradlew build           # compile
./gradlew simulateJava    # run in simulation — watch the arm on SmartDashboard
./gradlew rankCheck       # grade it
```

Windows: `.\gradlew.bat build`.

---

## Part 1 — Feel what P does (steps 1–3)

**STEP 1 — `PTuneCommand`.**
Constructor `public PTuneCommand(ArmSubsystem arm, double targetRotations)`. Drive the motor
toward a target encoder position with a `PIDController` using **only kP**. You'll need enough
of `ArmSubsystem` first to read the position and command the motor.
*Checked by: SourceScanCheck, your reviewer.*

**STEP 2 — Make kP tunable live.**
Read it from SmartDashboard every loop instead of hard-coding it.
*Checked by: SourceScanCheck.*

**STEP 3 — Record three kP values.**
One that **overshoots**, one that **oscillates**, and your final one — with a sentence each
on what you saw. In your PR.
*Checked by: your reviewer.*

## Part 2 — Make it an arm (steps 4–6)

**STEP 4 — Think in degrees.**
`setSetpoint(double targetDegrees)`, `getSetpointDegrees()`, `getAngleDegrees()` (using
`GEAR_RATIO` from `Constants`), `atSetpoint()`.
*Checked by: ArmApiCheck, SourceScanCheck.*

**STEP 5 — PID plus a gravity term.**
In `periodic()`, combine the controller's correction with a gravity feedforward scaled by
your own `kG`. **Read resources #2 and #7 first** — they explain what the gravity term has to
do and how it changes with angle. Work out the expression yourself.
*Checked by: your reviewer.*

**STEP 6 — Clamp every setpoint** between `MIN_ANGLE` and `MAX_ANGLE`, inside `setSetpoint`.
*Checked by: SetpointClampCheck.*

## Part 3 — Make it useful (steps 7–10)

**STEP 7 — Three presets.**
`STOW_ANGLE`, `INTAKE_ANGLE`, `SCORE_ANGLE` in `Constants`, bound to **X**, **Y** and **B**.
Each command **finishes** when the arm arrives.
*Checked by: SourceScanCheck, your reviewer.*

**STEP 8 — Bring your Diamond subsystem back.**
Copy your own `SimpleMotorSubsystem` from `students/<your-username>/robot/diamond` and run
both subsystems together, both bound to buttons.
*Checked by: your reviewer.*

**STEP 9 — Retune on hardware, with a lead, and demo all three presets.**
*Checked by: your reviewer, in person.*

**STEP 10 — In your PR: three things you learned, three you want to explore next.**
*Checked by: your reviewer.*

---

## Passes when

- The arm reaches each preset **without sustained oscillation**.
- Commands **end** at their setpoints.
- The angle limits hold.
- `./gradlew rankCheck` passes.
- Steps 3 and 10 are written up in your PR.

---

## Resources

1. [Introduction to PID](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/introduction-to-pid.html)
2. [Tuning a Vertical Arm](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-vertical-arm.html)
3. [PIDController](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/pidcontroller.html)
4. [PID Control in Command-Based](https://docs.wpilib.org/en/stable/docs/software/commandbased/pid-subsystems-commands.html)

Numbers match the guide's "Robot Emerald" Resources list — resource #7 referenced in step 5
is in the guide. Full guide: **GUIDE_URL**

---

## Stuck?

- **The arm slams into the top.** kP is too high, or your gravity term has the wrong sign.
  Drop kP to something tiny and build up.
- **The arm droops below the setpoint and stays there.** That is what a missing gravity term
  looks like. P alone has to have an error to push at all.
- **The arm wobbles forever.** Classic too-much-kP oscillation. That's step 3's second
  observation — write it down before you fix it.
- **The angle reads about 50x too big.** You're reading motor rotations, not arm rotations.
  That's the gear ratio.
- **`atSetpoint()` is never true.** Your tolerance is probably too tight. Two degrees is a
  reasonable place to start.
- **The command never ends.** Something has to check `atSetpoint()` and finish.
