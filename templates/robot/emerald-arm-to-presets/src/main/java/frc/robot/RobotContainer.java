package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

// ============================================================
// RANK:        Robot Emerald: Arm to Presets
// FILE:        RobotContainer.java
// STEPS HERE:  7, 8
// GUIDE:       GUIDE_URL  (section "Robot Emerald")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: the arm reaches each preset without sustained oscillation, commands end at
//              their setpoints, and the angle limits hold.
// ============================================================
//
// Same job as always: this is where the robot gets assembled and every binding lives. By the
// end of this rank it holds two subsystems at once, which is the first time your robot looks
// like a real one.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//   import frc.robot.subsystems.ArmSubsystem;

public class RobotContainer {

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // STEP 7: Bind the three presets
        // WHAT:       Bind X, Y and B to commands that send the arm to STOW_ANGLE,
        //             INTAKE_ANGLE and SCORE_ANGLE. Each command must FINISH once the arm has
        //             arrived — use atSetpoint() to decide when that is.
        // WHY:        A command that never finishes holds the subsystem forever, so the next
        //             button press has to interrupt it. Commands that end cleanly are what
        //             let a driver press three buttons in a row and have the robot do the
        //             obvious thing.
        // CONCEPTS:   Bindings, onTrue, commands that finish, atSetpoint, requirements
        // READ:       Guide > Robot Emerald > Resources #4
        // CHECKED BY: your reviewer (they will press all three), SourceScanCheck
        // DONE WHEN:  each button sends the arm to its angle, and the command ends when it
        //             gets there instead of hanging around.

        // STEP 8: Bring your Diamond subsystem back
        // WHAT:       Copy your own SimpleMotorSubsystem from
        //             students/<your-username>/robot/diamond into this project's subsystems
        //             folder, create it here alongside the arm, and bind it to buttons too.
        //             Both subsystems must work at the same time, without interfering.
        // WHY:        A real robot is never one mechanism. This is the first time you run two
        //             at once — and the scheduler handles it, because each command declares
        //             which subsystem it requires. Break that and you get the classic
        //             mid-match bug: two things fighting over one motor.
        // CONCEPTS:   Multiple subsystems, requirements, the scheduler, reusing your own code
        // READ:       Guide > Robot Emerald > Resources #4
        // CHECKED BY: your reviewer
        // DONE WHEN:  the arm holds a preset while the other motor runs, and neither one
        //             interrupts the other.
    }

    public Command getAutonomousCommand() {
        return Commands.none();
    }
}
