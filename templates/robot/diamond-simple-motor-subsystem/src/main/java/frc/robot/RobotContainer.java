package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

// ============================================================
// RANK:        Robot Diamond: Simple Motor Subsystem
// FILE:        RobotContainer.java
// STEPS HERE:  7, 8, 9
// GUIDE:       GUIDE_URL  (section "Robot Diamond")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: nothing outside the subsystem touches the TalonFX directly, the limit switch
//              reliably blocks forward motion, and all bindings live in RobotContainer.
// ============================================================
//
// Every binding on the robot lives in this file. When a driver asks "what does B do?", this
// is the one file anyone has to open — and that is only true if you keep it that way.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//   import edu.wpi.first.wpilibj2.command.button.Trigger;
//   import frc.robot.subsystems.SimpleMotorSubsystem;

public class RobotContainer {

    // You will need a subsystem field and a controller field here. Both are yours to add —
    // that is part of steps 7 and 8.

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // STEP 7: Bind the two command factories to buttons
        // WHAT:       While A is held, run your subsystem's runForward() command. While B is
        //             held, run runReverse(). Create the CommandXboxController yourself, on
        //             the port from Constants.
        // WHY:        `whileTrue` is the binding that matches "hold to run": the command
        //             starts when you press, and — importantly — ends when you let go, which
        //             is what makes your stop-on-end behaviour actually fire.
        // CONCEPTS:   CommandXboxController, Trigger, whileTrue, command lifecycle
        // READ:       Guide > Robot Diamond > Resources #3
        // CHECKED BY: SourceScanCheck (whileTrue must appear here), your reviewer
        // DONE WHEN:  holding A drives the motor one way, holding B the other, and letting go
        //             of either stops it.

        // STEP 8: Set a default command driven by the joystick
        // WHAT:       Give the subsystem a default command that drives the motor from the
        //             left joystick's Y axis, passed as a DoubleSupplier lambda. Apply a
        //             deadband (use a constant from Constants) so a resting stick commands
        //             exactly zero.
        // WHY:        A default command is what a subsystem does when nothing else has
        //             claimed it — the robot's resting behaviour. And the deadband is the
        //             same idea you met at Copper, now with a real motor on the end of it:
        //             without one, a worn joystick makes the mechanism creep all match.
        // CONCEPTS:   Default commands, DoubleSupplier, lambdas, deadband, subsystem
        //             requirements
        // READ:       Guide > Robot Diamond > Resources #2
        // CHECKED BY: SourceScanCheck (setDefaultCommand must appear here), your reviewer
        // DONE WHEN:  moving the left stick moves the motor, and letting it centre stops it
        //             dead.

        // STEP 9: Zero the encoder when the limit switch is pressed
        // WHAT:       Make a Trigger from your subsystem's limit switch, and when it becomes
        //             pressed, reset the motor's encoder position to zero.
        // WHY:        This is how mechanisms find out where they are. A limit switch is at a
        //             known physical spot, so the moment it presses, the robot knows exactly
        //             what the encoder *should* read. Real arms and elevators do this every
        //             time they home.
        // CONCEPTS:   Trigger, onTrue, encoder zeroing, using a sensor as a reference point
        // READ:       Guide > Robot Diamond > Resources #3 and #4
        // CHECKED BY: SourceScanCheck (`new Trigger` must appear here), your reviewer
        // DONE WHEN:  pressing the limit switch — in sim or on the bench — sends the reported
        //             position back to 0.
    }

    public Command getAutonomousCommand() {
        // Still no autonomous at this rank.
        return Commands.none();
    }
}
