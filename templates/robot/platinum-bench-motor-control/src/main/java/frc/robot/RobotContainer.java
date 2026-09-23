package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

// ============================================================
// RANK:        Robot Platinum: Bench Motor Control
// FILE:        RobotContainer.java
// STEPS HERE:  4, 5, 7
// GUIDE:       GUIDE_URL  (section "Robot Platinum")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew build
// PASSES WHEN: the sim demo and bench demo both work, no numbers are hard-coded outside
//              Constants, and the motor stops when disabled.
// ============================================================
//
// RobotContainer is where the robot gets assembled: hardware is created here, and controls
// get wired to it here. Rebuilt-2026 has the same file doing the same job, just bigger.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import com.ctre.phoenix6.configs.TalonFXConfiguration;
//   import com.ctre.phoenix6.controls.DutyCycleOut;
//   import com.ctre.phoenix6.hardware.TalonFX;
//   import com.ctre.phoenix6.signals.NeutralModeValue;
//   import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//   import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

    // STEP 4: Create the motor and configure it
    // WHAT:       Create a TalonFX using the CAN ID from Constants, then apply a
    //             TalonFXConfiguration to it that sets:
    //               - a stator current limit (and enables it)
    //               - brake as the neutral mode
    //             Keep the TalonFX as a field here for now; it moves into a subsystem at
    //             Diamond.
    // WHY:        A current limit is the difference between a stalled mechanism that stops
    //             and a stalled mechanism that cooks its motor and browns out the robot.
    //             Brake mode means the motor resists movement when it is not being
    //             commanded, instead of coasting — which matters a lot for an arm.
    // CONCEPTS:   TalonFX, TalonFXConfiguration, current limits, neutral mode, CAN IDs
    // READ:       Guide > Robot Platinum > Resources #4
    // CHECKED BY: your reviewer, plus build warnings for the config pieces
    // DONE WHEN:  the code builds and the motor appears in the Sim GUI's device list.

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // STEP 5 (continued from Robot.java): create the controller
        // WHAT:       Create the CommandXboxController on the port from Constants. You can
        //             keep it as a field here, and read its A and B buttons from
        //             teleopPeriodic.
        // WHY:        All bindings living in one place is the convention across WPILib and
        //             all of our robot code. When a driver asks "what does Y do?", there is
        //             exactly one file to open.
        // CONCEPTS:   CommandXboxController, controller ports, keeping bindings in one place
        // READ:       Guide > Robot Platinum > Resources #3
        // CHECKED BY: your reviewer
        // DONE WHEN:  the controller exists here and teleopPeriodic can read its buttons.

        // STEP 7: Publish telemetry
        // WHAT:       Set up publishing the motor's applied output to SmartDashboard. The
        //             publishing itself happens every loop (see Robot.java).
        // WHY:        Telemetry is how a robot tells you what it is doing. Without it you are
        //             debugging by listening to the motor.
        // CONCEPTS:   SmartDashboard, NetworkTables, telemetry
        // READ:       Guide > Robot Platinum > Resources #1
        // CHECKED BY: your reviewer, plus a build warning
        // DONE WHEN:  the value shows up in the Sim GUI and changes while you hold a button.
    }

    public Command getAutonomousCommand() {
        // No autonomous at this rank. Commands.none() is a real command that finishes
        // immediately, which is the polite way to say "nothing to do".
        return Commands.none();
    }
}
