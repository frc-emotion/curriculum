package rankup.subsystems;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// FILE:        subsystems/Intake.java
// STEPS HERE:  6, 8
// GUIDE:       GUIDE_URL  (section "Robot Gold")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: all fields are private, no port numbers appear outside Constants, and you can
//              explain in review what the lambda and method reference are doing.
// ============================================================
//
// The thing that picks game pieces up. On the real robot it is one motor and a lot of
// opinions about wheel material.
//
// Imports you will need (add them yourself, under the package line):
//   import java.util.function.DoubleSupplier;
//   import rankup.Constants;
//   import rankup.util.Mechanism;
//   import rankup.util.Motor;

public class Intake {

    // STEP 6: Make Intake a real subsystem
    // WHAT:       Change the class declaration so Intake extends MockSubsystem and implements
    //             Mechanism. Then give it:
    //               - a PRIVATE Motor field, built in the constructor from the port constant
    //                 in Constants — no number literals in this file
    //               - a constructor `public Intake()` that passes its own name up to
    //                 MockSubsystem
    //               - `public void run()` that sets its motor to the intake speed from
    //                 Constants
    //               - `public void stop()` that sets its motor to 0
    //               - `@Override public void periodic()` that prints this subsystem's motor
    //                 speed
    // WHY:        This is the shape of every subsystem in Rebuilt-2026: it owns its hardware
    //             privately, it exposes verbs (run, stop) instead of the motor itself, and it
    //             reports on itself every loop. The `extends` gives it a name for free; the
    //             `implements` promises anyone holding a Mechanism that it can be stopped.
    // CONCEPTS:   extends, implements, super(...), @Override, private fields, composition
    // READ:       Guide > Robot Gold > Resources #2
    // CHECKED BY: SubsystemCheck, ConstantsCheck, SourceScanCheck (no number literals passed
    //             to `new Motor(`)
    // DONE WHEN:  an Intake is both a MockSubsystem and a Mechanism, run() leaves its motor
    //             at the intake speed, and stop() leaves it at 0.

    // STEP 8: Add runAtSpeed
    // WHAT:       Add this method:
    //               public void runAtSpeed(DoubleSupplier speed)
    //             It asks the supplier for a value and sets the motor to it.
    // WHY:        A DoubleSupplier is a promise to produce a number when asked, not a number
    //             you already have. That difference is everything: the subsystem can ask
    //             again every loop and get the joystick's *current* position. Pass a plain
    //             double instead and the intake is stuck at whatever the stick read once, at
    //             startup.
    // CONCEPTS:   java.util.function.DoubleSupplier, getAsDouble(), lambdas that return
    //             values, deferred evaluation
    // READ:       Guide > Robot Gold > Resources #3 and #4
    // CHECKED BY: RunAtSpeedCheck
    // DONE WHEN:  runAtSpeed(() -> 0.42) leaves the motor at 0.42.

}
