# Robot Gold — Mock Robot Hardware

**Track:** Robot (Java / WPILib)
**Builds on:** Robot Iron (loops, methods, arrays)

You're building a fake robot: motors, subsystems, buttons — the whole shape of
`Rebuilt-2026`, with nothing that can actually hurt anyone. Every idea here shows up again
at Platinum with real hardware behind it, so this is the rank where the structure of robot
code finally makes sense.

Guide: **GUIDE_URL** (section "Robot Gold")

---

## Skills required

Everything from Iron, plus:

- Classes and objects, and the difference between them
- Private fields, constructors, getters — encapsulation
- Inheritance (`extends`) and `super(...)`
- Interfaces (`implements`)
- `@Override`
- Lambdas (`->`) and method references (`::`)
- `Runnable` and `DoubleSupplier`
- `ArrayList` of a parent type

---

## The files

```
src/main/java/rankup/
├── Main.java                    steps 3, 7, 8, 9, 10  — where it all gets wired together
├── Constants.java               step 2                — every number lives here
├── util/
│   ├── Motor.java               step 1                — a pretend motor controller
│   ├── Mechanism.java           step 4                — an interface: "I can be stopped"
│   └── Button.java              step 7                — holds a piece of code, runs it later
└── subsystems/
    ├── MockSubsystem.java       step 5                — the shared parent
    ├── Intake.java              steps 6, 8            — picks pieces up
    └── Shooter.java             step 6                — throws them
```

Each file arrives as a bare declaration with STEP comments inside. **Nothing extends,
implements, or holds anything yet** — that's what you're adding.

---

## Commands

```bash
./gradlew run         # run your fake robot
./gradlew rankCheck   # grade it
```

Windows: `.\gradlew.bat run`.

---

## Steps

**STEP 1 — `Motor` (util/Motor.java).**
Three **private** fields (`name`, `port`, `speed`), a constructor `public Motor(String name,
int port)`, `setSpeed(double)` that clamps to `-1.0 .. 1.0`, plus `getSpeed()`, `getName()`
and `getPort()`.
*Checked by: MotorCheck.*

**STEP 2 — `Constants`.**
`public static final int INTAKE_MOTOR_PORT = 1` and `SHOOTER_MOTOR_PORT = 2`, plus
`public static final double INTAKE_SPEED = 0.8`. Add a shooter speed constant too — no
numbers typed anywhere else.
*Checked by: ConstantsCheck, SourceScanCheck.*

**STEP 3 — Two motors in `Main`.**
Create two, set the speed on one, print both. Prove they're separate objects.
*Checked by: MotorCheck.*

**STEP 4 — `Mechanism` (util/Mechanism.java).**
Declare `void stop();` and `String getName();`. No bodies — it's an interface.
*Checked by: MechanismCheck.*

**STEP 5 — `MockSubsystem`.**
Constructor `MockSubsystem(String name)`, `getName()`, and `periodic()` that prints the name.
*Checked by: SubsystemCheck.*

**STEP 6 — `Intake` and `Shooter`.**
Both `extends MockSubsystem` **and** `implements Mechanism`. Each owns a **private** `Motor`
built from `Constants`, has a no-argument constructor that passes its name up, a `run()`, a
`stop()`, and an `@Override periodic()` that prints its motor speed.
*Checked by: SubsystemCheck, SourceScanCheck.*

**STEP 7 — `Button` and two bindings.**
`Button(Runnable action)` and `press()`. In `Main`, build one with a lambda
(`() -> intake.run()`) and one with a method reference (`shooter::stop`).
*Checked by: ButtonCheck, SourceScanCheck.*

**STEP 8 — `Intake.runAtSpeed(DoubleSupplier speed)`.**
Sets the motor from the supplier. In `Main`, pass a lambda returning a pretend joystick value.
*Checked by: RunAtSpeedCheck.*

**STEP 9 — Five robot ticks.**
Hold every subsystem in an `ArrayList<MockSubsystem>` and loop 5 times calling `periodic()`
on each, pressing buttons between ticks.
*Checked by: your reviewer.*

**STEP 10 — Class vs object.**
One sentence in your PR description: a real-world analogy for the difference.
*Checked by: your reviewer.*

---

## Passes when

- All fields are private.
- No port numbers appear outside `Constants`.
- You can explain in review what the lambda and the method reference are doing.
- `./gradlew rankCheck` passes.
- Your PR has the class-vs-object sentence.

---

## Resources

1. [Oracle: Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html)
2. [Oracle: Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
3. [Oracle: Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
4. [WPILib: Treating Functions as Data](https://docs.wpilib.org/en/stable/docs/software/basic-programming/functions-as-data.html)

Numbers match the guide's "Robot Gold" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **"I couldn't find a constructor..."** — check the parameter types and their order.
- **"Setting the intake's speed changed the shooter too."** — a field marked `static` is
  shared by every object of that class. That's almost never what you want for hardware.
- **`extends` vs `implements`** — you extend one class (what you *are*) and implement any
  number of interfaces (what you can *do*). Both can appear on the same line:
  `class Foo extends Bar implements Baz`.
- **Lambdas feel like magic.** A lambda is just a method without a name, written where it's
  needed. `() -> intake.run()` means "when someone runs this, call intake.run()".
- **`::` looks alien.** `shooter::stop` means the same as `() -> shooter.stop()`, shorter.
