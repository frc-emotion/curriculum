# Robot Platinum — Bench Motor Control

**Track:** Robot (Java / WPILib)
**Builds on:** Robot Gold (classes, inheritance, interfaces, lambdas)

First rank with real WPILib and a real motor. You'll learn the robot's lifecycle — which
code runs once, which runs fifty times a second — then use it to spin a TalonFX with an Xbox
controller, first in simulation and then on the bench.

This project is a WPILib 2026 GradleRIO command-based project using **GradleRIO 2026.2.1**,
**Java 17** and **Phoenix 6 (26.1.1)** — the same versions as `Rebuilt-2026`.

Guide: **GUIDE_URL** (section "Robot Platinum")

---

> ## ⚠️ Safety
>
> This rank ends with a real motor spinning.
>
> - **Bench testing only.** The motor is secured to a bench. Never on a robot that can move.
> - **A lead must be present.** Not "nearby". Present, watching, able to hit disable.
> - **Start slow.** `MOTOR_SPEED` is 0.3 for a reason. Don't raise it because it looks boring.
> - **Nothing loose.** No sleeves, no lanyards, no hair, no fingers near the shaft.
> - **Know how to stop it** before you enable it. The disable button on the driver station.
>
> Do the whole thing in simulation first. Simulation cannot break a finger.

---

## Skills required

Everything from Gold, plus:

- The WPILib robot lifecycle: `*Init` vs `*Periodic`, the 20ms loop
- Command-based project structure: `Robot`, `RobotContainer`, `Constants`
- Creating and configuring a `TalonFX`
- Current limits and neutral mode
- `DutyCycleOut` control requests
- `CommandXboxController`
- `SmartDashboard` telemetry
- The simulation GUI

---

## Setup

You need **WPILib 2026** installed — it brings the right JDK, VS Code and the simulator:
[WPILib Installation Guide](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)

```bash
./gradlew build           # compile
./gradlew simulateJava    # run it in simulation, with the Sim GUI
./gradlew rankCheck       # advisory: lists steps that look unfinished
./gradlew deploy          # to a real roboRIO — lead present, bench only
```

Windows: `.\gradlew.bat build`.

The first build downloads WPILib and Phoenix, which takes a few minutes and a lot of
bandwidth. Do it at a meeting, not five minutes before you need it.

**The untouched template builds and runs in simulation.** It just doesn't do anything yet.

---

## About the check at this rank

`./gradlew rankCheck` here is **advisory**. It reads your code and lists steps that look
unfinished, but it never fails your build, because no source scan can tell whether a motor
actually spun.

**This rank is signed off by a lead who watches both demos.** `./gradlew build` passing is
required; everything else is a human's judgement.

---

## Steps

**STEP 1 — Document the lifecycle.**
Write a comment above every method in `Robot.java` saying when it runs and how often.
*Checked by: your reviewer (advisory scan flags missing ones).*

**STEP 2 — Print in each `Init`, then run the sim.**
Print a message in `disabledInit`, `autonomousInit` and `teleopInit`. Run
`./gradlew simulateJava`, switch between Disabled / Autonomous / Teleop in the Sim GUI, and
paste the console output into your PR.
*Checked by: your reviewer.*

**STEP 3 — `Constants`.**
The motor's CAN ID, the controller port, and `MOTOR_SPEED = 0.3`.
*Checked by: your reviewer (advisory scan).*

**STEP 4 — Create and configure the `TalonFX`.**
Apply a `TalonFXConfiguration` with a **stator current limit** (enabled) and **brake** neutral
mode.
*Checked by: your reviewer (advisory scan).*

**STEP 5 — Drive the motor.**
In `teleopPeriodic`: forward at `MOTOR_SPEED` while **A** is held, backward while **B** is
held, stopped when neither is. Use `DutyCycleOut`. You create the controller.
*Checked by: your reviewer (advisory scan).*

**STEP 6 — Stop the motor in `disabledInit`.**
*Checked by: your reviewer (advisory scan) — and they will test it.*

**STEP 7 — Publish the motor's applied output to SmartDashboard, every loop.**
*Checked by: your reviewer (advisory scan).*

**STEP 8 — Demo it.**
Simulation first, all the way through. Then, **with a lead present**, deploy to the bench
motor and demo it.
*Checked by: your reviewer, in person.*

---

## Passes when

- The sim demo **and** the bench demo both work.
- No numbers are hard-coded outside `Constants`.
- The motor stops when the robot is disabled.
- `./gradlew build` passes.
- Step 2's console output is pasted in your PR.

---

## Resources

1. [What is WPILib?](https://docs.wpilib.org/en/stable/docs/software/what-is-wpilib.html)
2. [Creating a Robot Program](https://docs.wpilib.org/en/stable/docs/software/vscode-overview/creating-robot-program.html)
3. [What Is Command-Based Programming?](https://docs.wpilib.org/en/stable/docs/software/commandbased/what-is-command-based.html)
4. [Phoenix 6: Control Requests](https://v6.docs.ctr-electronics.com/en/stable/docs/api-reference/api-usage/control-requests.html)

Numbers match the guide's "Robot Platinum" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **The build takes forever the first time.** Normal. It's downloading WPILib.
- **"Could not find TalonFX".** Check `vendordeps/Phoenix6-26.1.1.json` is still there, and
  add the import — VS Code will offer it.
- **The motor doesn't move in sim.** Open the Sim GUI, find the device list, and check the
  TalonFX is there. Then check the driver station in the GUI is set to Teleop **and** Enabled.
- **It moves in sim but not on the bench.** Almost always the CAN ID. Check it in Phoenix
  Tuner against the constant you wrote.
- **The motor keeps spinning after you let go of A.** A motor controller holds the last thing
  it was told. You need an `else` that commands zero.
