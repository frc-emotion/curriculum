package frc.robot;

// ============================================================
// RANK:        Robot Emerald: Arm to Presets
// FILE:        Constants.java
// STEPS HERE:  4, 6, 7
// GUIDE:       GUIDE_URL  (section "Robot Emerald")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: the arm reaches each preset without sustained oscillation, commands end at
//              their setpoints, and the angle limits hold.
// ============================================================

public final class Constants {

    // STEP 4 (continued from ArmSubsystem.java): the gear ratio
    // WHAT:       Add a constant named GEAR_RATIO, a double. The value is a physical fact
    //             about this arm and it is written at the top of sim/ArmSimHelper.java —
    //             go read it there, the same way you would read it off a real gearbox.
    // WHY:        The motor's encoder counts MOTOR rotations, not arm rotations. With a 50:1
    //             gearbox the motor turns 50 times for one turn of the arm, so the raw
    //             encoder number is 50x too big. Every mechanism on the robot has this
    //             problem, and forgetting it is why an arm asked to go to 45 degrees ends up
    //             somewhere near 0.9.
    // CONCEPTS:   Gear ratios, unit conversion, encoder counts vs real-world angles
    // READ:       Guide > Robot Emerald > Resources #2
    // CHECKED BY: SourceScanCheck (GEAR_RATIO must exist here)
    // DONE WHEN:  getAngleDegrees() uses this and reports an angle you believe.

    // STEP 6 (continued from ArmSubsystem.java): the angle limits
    // WHAT:       Add MIN_ANGLE and MAX_ANGLE, both doubles, in degrees. Match the hard stops
    //             listed at the top of sim/ArmSimHelper.java.
    // WHY:        Software limits are the layer that stops the arm before the hard stop does.
    //             Reaching a hard stop under power means a stalled motor, a bent bracket, or
    //             a snapped chain — and it always happens the one time nobody expected the
    //             setpoint to be wrong.
    // CONCEPTS:   Software limits, defensive programming, clamping
    // READ:       Guide > Robot Emerald > Resources #2
    // CHECKED BY: SetpointClampCheck, SourceScanCheck
    // DONE WHEN:  asking the arm for 200 degrees leaves the setpoint at MAX_ANGLE.

    // STEP 7 (continued from RobotContainer.java): the three preset angles
    // WHAT:       Add three doubles in degrees: STOW_ANGLE, INTAKE_ANGLE and SCORE_ANGLE.
    //             Pick sensible values between MIN_ANGLE and MAX_ANGLE — stowed low, intake
    //             near the floor, scoring high.
    // WHY:        Drivers do not aim an arm by hand in a match. They press a button and it
    //             goes exactly where it went last time. Named angles in one file are what
    //             make that tunable between matches.
    // CONCEPTS:   Presets, named configuration, driver ergonomics
    // READ:       Guide > Robot Emerald > Resources #4
    // CHECKED BY: SourceScanCheck
    // DONE WHEN:  X, Y and B each send the arm to its own angle.

    // You will also want constants for your PID and feedforward gains. Put them here too,
    // rather than typing numbers into the subsystem.

}
