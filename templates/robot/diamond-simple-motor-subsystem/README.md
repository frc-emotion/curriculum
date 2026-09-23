# Robot Diamond — Simple Motor Subsystem

**Track:** Robot (Java / WPILib)
**Builds on:** Robot Platinum (WPILib structure and motors)

At Platinum the motor lived in `RobotContainer` where anything could reach it. Now you build
a real **subsystem**: it owns the hardware, nothing else touches it, and the rest of the
robot asks it politely. Then you add a sensor, wire buttons to commands, and make the limit
switch physically impossible to drive through.

This is the shape of every subsystem in `Rebuilt-2026`.

Guide: **GUIDE_URL** (section "Robot Diamond")

---

> ## ⚠️ Safety
>
> - **Bench testing only**, motor secured, **a lead present**. Not negotiable.
> - **Start at low speed.** Keep `MOTOR_SPEED` where Platinum left it.
> - **Test the limit switch in simulation first.** Step 10 has you toggle it in the Sim GUI
>   before you ever press the real one. A limit switch with the logic backwards drives the
>   mechanism *into* the hard stop at full power.
> - Keep hands, sleeves and hair clear of the shaft before anyone enables.

---

## Skills required

Everything from Platinum, plus:

- Subsystems and `SubsystemBase`
- Commands, and command factory methods
- Triggers and bindings: `whileTrue`, `onTrue`
- Default commands
- `DigitalInput` and DIO channels
- Reading encoder position and velocity from a `TalonFX`
- Simulation: `simulationPeriodic`, toggling DIO in the Sim GUI

---

## The files

```
src/main/java/frc/robot/
├── Main.java                              plumbing, don't edit
├── Robot.java                             step 10 — the lifecycle, filled in for you
├── RobotContainer.java                    steps 7, 8, 9 — every binding lives here
├── Constants.java                         steps 1, 3 — every number lives here
├── subsystems/
│   └── SimpleMotorSubsystem.java          steps 1-6 — the main event
├── commands/package-info.java             why this folder is empty (read it)
└── sim/MotorSimHelper.java                DO NOT EDIT — finished plumbing, not assessed
```

`MotorSimHelper` fakes the physics so the encoder moves in simulation. It's done for you
because writing a physics model isn't what this rank is about. Leave it alone.

---

## Commands

```bash
./gradlew build           # compile
./gradlew simulateJava    # run it in simulation, with the Sim GUI
./gradlew rankCheck       # grade it
```

Windows: `.\gradlew.bat build`.

The rank checks start WPILib's simulation layer, so they can create a motor and a limit
switch with no hardware plugged in.

---

## Steps

**STEP 1 — Move your Platinum motor code into the subsystem.**
Bring the `TalonFX` and its configuration over from
`students/<your-username>/robot/platinum` — **your own code**. The field must be **private**,
and the port numbers go in `Constants`.
*Checked by: MotorPrivacyCheck.*

**STEP 2 — Give the subsystem its verbs.**
`setSpeed(double)`, `stop()`, `getPositionRotations()`, `getVelocityRps()`, `isAtLimit()`.
No `getMotor()`.
*Checked by: SubsystemApiCheck.*

**STEP 3 — Add the limit switch.**
A `DigitalInput` on the DIO channel from `Constants`. Watch the polarity: most switches read
`true` when **not** pressed.
*Checked by: SubsystemApiCheck, LimitSwitchCheck, and your reviewer on the bench.*

**STEP 4 — Publish telemetry in `periodic()`.**
Position, velocity, and limit switch state, to SmartDashboard.
*Checked by: your reviewer.*

**STEP 5 — Make the limit switch block forward motion.**
Inside `setSpeed`, so every caller gets the rule for free. Backward must still work.
*Checked by: your reviewer.*

**STEP 6 — Two command factories: `runForward()` and `runReverse()`.**
Both return a `Command`. Both **stop the motor when they end**.
*Checked by: SubsystemApiCheck.*

**STEP 7 — Bind them.**
`whileTrue` on A and B, in `RobotContainer`.
*Checked by: SourceScanCheck.*

**STEP 8 — A default command from the left joystick Y axis.**
Pass it as a `DoubleSupplier` lambda, with a deadband from `Constants`.
*Checked by: SourceScanCheck.*

**STEP 9 — A `Trigger` on the limit switch that zeroes the encoder.**
*Checked by: SourceScanCheck.*

**STEP 10 — Test it.**
Simulation first: call `MotorSimHelper.update` from `simulationPeriodic()`, watch the
position climb, toggle the DIO in the Sim GUI. Then the bench, **with a lead**: turn the
shaft by hand and watch the encoder, then press the limit switch.
*Checked by: your reviewer, in person.*

**STEP 11 — Explain it.**
Two or three sentences in your PR: why hardware lives in subsystems and actions live in
commands.
*Checked by: your reviewer.*

---

## Passes when

- Nothing outside the subsystem touches the `TalonFX` directly.
- The limit switch reliably blocks forward motion.
- All bindings live in `RobotContainer`.
- `./gradlew rankCheck` passes.
- Both demos done, and step 11's explanation is in your PR.

---

## Resources

1. [Subsystems](https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html)
2. [Commands](https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html)
3. [Binding Commands to Triggers](https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html)
4. [Encoders — Software](https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/encoders-software.html)

Numbers match the guide's "Robot Diamond" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **"I couldn't find a TalonFX field."** It has to be a field on `SimpleMotorSubsystem`, and
  private.
- **"RobotContainer still mentions TalonFX."** That's the point of the rank — `RobotContainer`
  should only ever talk to the subsystem.
- **The command runs but never stops.** Look at what happens when a command *ends*. If
  nothing stops the motor on the way out, the motor keeps going.
- **The default command never runs.** A default command only runs when nothing else has
  claimed the subsystem, and only if the subsystem is a requirement of the command.
- **`isAtLimit()` never changes.** Confirm the DIO channel matches the constant, and that
  you're reading the `DigitalInput` rather than returning a fixed value.
- **The encoder stays at 0 in sim.** You haven't wired `MotorSimHelper.update` into
  `simulationPeriodic()` yet (step 10).
