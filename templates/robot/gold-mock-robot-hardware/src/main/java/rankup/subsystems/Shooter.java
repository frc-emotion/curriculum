package rankup.subsystems;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// FILE:        subsystems/Shooter.java
// STEPS HERE:  6
// GUIDE:       GUIDE_URL  (section "Robot Gold")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: all fields are private, no port numbers appear outside Constants, and you can
//              explain in review what the lambda and method reference are doing.
// ============================================================
//
// The thing that throws game pieces. Same shape as the Intake, which is the point: once you
// know one subsystem you know them all.
//
// Imports you will need (add them yourself, under the package line):
//   import rankup.Constants;
//   import rankup.util.Mechanism;
//   import rankup.util.Motor;

public class Shooter {

    // STEP 6 (continued from Intake.java): make Shooter a real subsystem
    // WHAT:       Same shape as Intake. Change the declaration so Shooter extends
    //             MockSubsystem and implements Mechanism, then give it:
    //               - a PRIVATE Motor field, built from the shooter port constant in
    //                 Constants — no number literals in this file
    //               - a constructor `public Shooter()` that passes its own name up
    //               - `public void run()` that sets its motor to a running speed, using a
    //                 constant you add to Constants
    //               - `public void stop()` that sets its motor to 0
    //               - `@Override public void periodic()` that prints this subsystem's motor
    //                 speed
    // WHY:        Writing the second one is where inheritance earns its keep: you get the
    //             name, the getter and the shared behaviour without retyping any of it. Only
    //             the parts that are genuinely different — the port, the speed — are here.
    // CONCEPTS:   extends, implements, super(...), @Override, code reuse
    // READ:       Guide > Robot Gold > Resources #2
    // CHECKED BY: SubsystemCheck, SourceScanCheck (no number literals passed to `new Motor(`)
    // DONE WHEN:  a Shooter is both a MockSubsystem and a Mechanism, run() leaves its motor
    //             at a non-zero speed, and stop() brings it back to 0.

}
