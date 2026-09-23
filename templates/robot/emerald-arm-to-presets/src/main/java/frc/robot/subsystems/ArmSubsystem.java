package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// ============================================================
// RANK:        Robot Emerald: Arm to Presets
// FILE:        subsystems/ArmSubsystem.java
// STEPS HERE:  1, 4, 5, 6
// GUIDE:       GUIDE_URL  (section "Robot Emerald")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: the arm reaches each preset without sustained oscillation, commands end at
//              their setpoints, and the angle limits hold.
// ============================================================
//
// An arm is the first mechanism where "just set the motor to 0.3" stops working. Point it
// up and gravity fights you. Point it down and gravity helps too much. It needs to hold a
// position, not a speed — which is what a controller is for.
//
// Build this in two passes. Part 1 (step 1) needs only enough of a subsystem for
// PTuneCommand to drive the motor and read the encoder. Part 2 (steps 4-6) turns it into a
// real arm that thinks in degrees.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import com.ctre.phoenix6.configs.TalonFXConfiguration;
//   import com.ctre.phoenix6.controls.VoltageOut;
//   import com.ctre.phoenix6.hardware.TalonFX;
//   import com.ctre.phoenix6.signals.NeutralModeValue;
//   import edu.wpi.first.math.controller.PIDController;
//   import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//   import frc.robot.Constants;
//   import frc.robot.sim.ArmSimHelper;

public class ArmSubsystem extends SubsystemBase {

    // STEP 1 (continued from PTuneCommand.java): enough subsystem to tune against
    // WHAT:       Give this subsystem a private TalonFX (same setup as your Diamond
    //             subsystem: current limit, brake mode, CAN ID from Constants), plus:
    //               - a way for a command to read the motor's raw position in rotations
    //               - a way for a command to command the motor
    //             It also needs an ArmSimHelper and a method that steps the simulation, so
    //             Robot.simulationPeriodic() can call it. That mirrors Diamond exactly.
    // WHY:        You cannot tune a controller against nothing. Part 1 is about watching kP
    //             push a real mechanism around, so the arm needs to move before it needs to
    //             be clever.
    // CONCEPTS:   Subsystems, encapsulation, simulation wiring
    // READ:       Guide > Robot Emerald > Resources #3
    // CHECKED BY: your reviewer
    // DONE WHEN:  running the simulation shows the arm drawn on SmartDashboard, and
    //             PTuneCommand can move it.

    // STEP 4: Make the arm think in degrees
    // WHAT:       Add these four methods:
    //               public void setSetpoint(double targetDegrees)
    //               public double getSetpointDegrees()
    //               public double getAngleDegrees()
    //               public boolean atSetpoint()
    //             getAngleDegrees converts the motor's encoder reading into a real arm angle
    //             using GEAR_RATIO from Constants. atSetpoint reports whether the arm has
    //             arrived — PIDController can answer that for you if you give it a tolerance.
    // WHY:        Everything outside this subsystem should talk in degrees, because degrees
    //             are what humans can reason about. "Go to 90" is reviewable. "Go to 12.5
    //             rotations" is a bug waiting to be misread.
    // CONCEPTS:   Unit conversion, gear ratios, tolerance, the subsystem's public vocabulary
    // READ:       Guide > Robot Emerald > Resources #2 and #3
    // CHECKED BY: ArmApiCheck, SetpointClampCheck, SourceScanCheck
    // DONE WHEN:  getAngleDegrees() matches the arm you can see drawn on SmartDashboard.

    // STEP 5: Hold the angle with PID plus a gravity term
    // WHAT:       In periodic(), work out the motor output from two pieces added together:
    //               - the PID controller's correction toward the setpoint, and
    //               - a gravity feedforward term, scaled by a constant of yours called kG.
    //             Command the result as a voltage.
    //             Read resources #2 and #7 before you write this. They explain what the
    //             gravity term has to do and how it changes with the arm's angle. Work out
    //             the expression yourself — that reasoning is the assessment.
    // WHY:        Pure PID can hold an arm up, but only by running a constant error: it has
    //             to droop before it pushes. A feedforward term says "here is roughly the
    //             effort needed to fight gravity right now", so the PID only has to correct
    //             what's left. That is the difference between an arm that sags and one that
    //             holds.
    // CONCEPTS:   Feedforward vs feedback, gravity compensation, why the term changes with
    //             angle, combining outputs, volts vs duty cycle
    // READ:       Guide > Robot Emerald > Resources #2 and #7
    // CHECKED BY: your reviewer (they will watch it hold position), SourceScanCheck
    // DONE WHEN:  the arm holds a horizontal setpoint without drooping, and holds a steep one
    //             without fighting itself.

    // STEP 6: Clamp every setpoint
    // WHAT:       Make setSetpoint clamp its argument between MIN_ANGLE and MAX_ANGLE from
    //             Constants, so getSetpointDegrees() never reports a value outside them —
    //             no matter what anybody asks for.
    // WHY:        Clamping here, rather than at each call site, means the rule cannot be
    //             forgotten. A typo in one preset, a bad joystick value, a command written
    //             next season by someone who never read this file: all of them hit the same
    //             guard.
    // CONCEPTS:   Clamping, guard clauses, defensive programming, single source of truth
    // READ:       Guide > Robot Emerald > Resources #2
    // CHECKED BY: SetpointClampCheck
    // DONE WHEN:  setSetpoint(200) leaves getSetpointDegrees() at MAX_ANGLE, and
    //             setSetpoint(-200) leaves it at MIN_ANGLE.

}
