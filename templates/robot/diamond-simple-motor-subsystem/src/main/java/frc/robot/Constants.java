package frc.robot;

// ============================================================
// RANK:        Robot Diamond: Simple Motor Subsystem
// FILE:        Constants.java
// STEPS HERE:  1, 3
// GUIDE:       GUIDE_URL  (section "Robot Diamond")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: nothing outside the subsystem touches the TalonFX directly, the limit switch
//              reliably blocks forward motion, and all bindings live in RobotContainer.
// ============================================================

public final class Constants {

    // STEP 1 (continued from SimpleMotorSubsystem.java): bring your Platinum numbers across
    // WHAT:       Copy the constants your Platinum code used — the motor's CAN ID, the
    //             controller port, the motor speed — from
    //             students/<your-username>/robot/platinum, and add them here.
    // WHY:        Same reason as every other rank: one place to change a number. You are
    //             carrying your own work forward, not starting over.
    // CONCEPTS:   public static final, configuration in one place
    // READ:       Guide > Robot Diamond > Resources #1
    // CHECKED BY: your reviewer
    // DONE WHEN:  no CAN ID or port number appears anywhere outside this file.

    // STEP 3 (continued from SimpleMotorSubsystem.java): the limit switch channel
    // WHAT:       Add the DIO channel number your limit switch is plugged into. Ask a lead
    //             which channel the bench setup uses.
    // WHY:        DIO channels are physical ports on the roboRIO. Wrong number, wrong port,
    //             and your limit switch silently never triggers — which is the most dangerous
    //             kind of wrong, because the code looks fine.
    // CONCEPTS:   Digital inputs, DIO channels, configuration in one place
    // READ:       Guide > Robot Diamond > Resources #4
    // CHECKED BY: your reviewer
    // DONE WHEN:  the subsystem builds its DigitalInput from this constant.

    // You will also want a deadband constant for step 8. Add it here rather than typing the
    // number into the lambda.

}
