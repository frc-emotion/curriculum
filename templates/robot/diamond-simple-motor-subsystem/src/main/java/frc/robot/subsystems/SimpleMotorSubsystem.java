package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// ============================================================
// RANK:        Robot Diamond: Simple Motor Subsystem
// FILE:        subsystems/SimpleMotorSubsystem.java
// STEPS HERE:  1, 2, 3, 4, 5, 6
// GUIDE:       GUIDE_URL  (section "Robot Diamond")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: nothing outside the subsystem touches the TalonFX directly, the limit switch
//              reliably blocks forward motion, and all bindings live in RobotContainer.
// ============================================================
//
// A subsystem owns a piece of hardware and is the only thing allowed to touch it. Everything
// else asks it politely. That single rule is what keeps two parts of the code from fighting
// over the same motor — which, on a real robot, sounds like a gearbox losing an argument.
//
// This is the same shape as every subsystem in Rebuilt-2026.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import com.ctre.phoenix6.configs.TalonFXConfiguration;
//   import com.ctre.phoenix6.controls.DutyCycleOut;
//   import com.ctre.phoenix6.hardware.TalonFX;
//   import com.ctre.phoenix6.signals.NeutralModeValue;
//   import edu.wpi.first.wpilibj.DigitalInput;
//   import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//   import edu.wpi.first.wpilibj2.command.Command;
//   import frc.robot.Constants;
//   import frc.robot.sim.MotorSimHelper;

public class SimpleMotorSubsystem extends SubsystemBase {

    // STEP 1: Move your Platinum motor code in here
    // WHAT:       Bring the TalonFX and its configuration over from your Platinum rank —
    //             your own code, from students/<your-username>/robot/platinum. The TalonFX
    //             field must be PRIVATE. Keep the current limit and the brake neutral mode.
    //             Put the port numbers in Constants (see Constants.java).
    // WHY:        At Platinum the motor lived in RobotContainer, where anything could reach
    //             it. Moving it in here and making it private means there is exactly one
    //             door to the hardware. When two commands both want the motor, the scheduler
    //             can referee — but only because the subsystem owns it.
    // CONCEPTS:   Subsystems, encapsulation, private fields, SubsystemBase
    // READ:       Guide > Robot Diamond > Resources #1
    // CHECKED BY: MotorPrivacyCheck (the TalonFX field must be private)
    // DONE WHEN:  the motor lives here, is private, and RobotContainer never mentions
    //             TalonFX at all.

    // STEP 2: Give the subsystem its verbs
    // WHAT:       Add these five methods:
    //               public void setSpeed(double speed)
    //               public void stop()
    //               public double getPositionRotations()
    //               public double getVelocityRps()
    //               public boolean isAtLimit()
    //             Position and velocity come from the TalonFX's own signals; isAtLimit reads
    //             the limit switch you add in step 3.
    // WHY:        These are the only things the rest of the robot can ask for. Notice what is
    //             NOT here: no getMotor(). If a subsystem hands out its hardware, it has
    //             stopped being a subsystem.
    // CONCEPTS:   Public interface vs private state, encoders, sensor getters
    // READ:       Guide > Robot Diamond > Resources #1 and #4
    // CHECKED BY: SubsystemApiCheck
    // DONE WHEN:  all five exist, and stop() really leaves the motor at zero output.

    // STEP 3: Add the limit switch
    // WHAT:       Add a DigitalInput on the DIO channel from Constants, and have isAtLimit()
    //             report whether it is pressed.
    //             Careful: most limit switches are wired so that DigitalInput.get() returns
    //             TRUE when NOT pressed. Check yours on the bench and make isAtLimit() mean
    //             what its name says.
    // WHY:        A limit switch is how a mechanism knows it has hit the end. Getting the
    //             polarity backwards is a classic: the code is "right", and the arm drives
    //             itself into a hard stop at full power.
    // CONCEPTS:   DigitalInput, DIO channels, normally-open vs normally-closed, sensor
    //             polarity
    // READ:       Guide > Robot Diamond > Resources #4
    // CHECKED BY: SubsystemApiCheck, your reviewer (they will press the real switch)
    // DONE WHEN:  isAtLimit() is true exactly when the switch is physically pressed.

    // STEP 4: Publish telemetry every loop
    // WHAT:       Override periodic() and publish three things to SmartDashboard: the
    //             position, the velocity, and whether the limit switch is pressed.
    // WHY:        periodic() runs every 20ms for every registered subsystem, which makes it
    //             the natural home for "tell the world what I'm doing". When something
    //             misbehaves at competition, these three numbers are what you look at.
    // CONCEPTS:   periodic(), SmartDashboard, NetworkTables, telemetry
    // READ:       Guide > Robot Diamond > Resources #1
    // CHECKED BY: your reviewer
    // DONE WHEN:  all three values appear in the Sim GUI and change as the motor runs.

    // STEP 5: Make the limit switch actually stop the motor
    // WHAT:       Change setSpeed so that while the limit switch is pressed, a forward
    //             command is refused (the motor stays stopped instead). Backward must still
    //             work — otherwise the mechanism is stuck at the limit forever.
    // WHY:        This is the whole point of the switch. Putting the rule inside setSpeed
    //             means EVERY caller gets it for free: commands, the default command, code
    //             someone writes next season. A safety rule that lives in one command is a
    //             safety rule waiting to be bypassed.
    // CONCEPTS:   Guard clauses, safety interlocks, why rules belong at the lowest level
    // READ:       Guide > Robot Diamond > Resources #1
    // CHECKED BY: your reviewer (they will hold the switch and push the stick)
    // DONE WHEN:  with the switch pressed, forward does nothing and backward still works.

    // STEP 6: Write two command factories
    // WHAT:       Add two methods that each return a Command:
    //               public Command runForward()
    //               public Command runReverse()
    //             Each one runs the motor in its direction while it is scheduled, and STOPS
    //             the motor when it ends. SubsystemBase gives you helpers for building
    //             commands like these — look at what run(...) and startEnd(...) do.
    // WHY:        "Ends" is the interesting word. When the driver lets go of A, the command
    //             ends — and if it doesn't stop the motor on the way out, the motor keeps
    //             going. That exact bug has driven more than one robot into a wall.
    // CONCEPTS:   Commands, command factories, SubsystemBase helpers, end behaviour,
    //             requirements
    // READ:       Guide > Robot Diamond > Resources #2
    // CHECKED BY: SubsystemApiCheck (both must exist and return a Command), your reviewer
    // DONE WHEN:  holding A runs the motor and releasing A stops it, every time.


    // STEP 11: Explain the split
    // WHAT:       In your pull request, write two or three sentences on why hardware lives in
    //             subsystems and actions live in commands.
    // WHY:        This is the idea the whole command-based framework is built on. If you can
    //             say it plainly, the rest of WPILib stops looking arbitrary — and at Emerald
    //             you will be making this exact decision yourself, without a STEP comment
    //             telling you which is which.
    // CONCEPTS:   Subsystems vs commands, ownership, the scheduler's job
    // READ:       Guide > Robot Diamond > Resources #1 and #2
    // CHECKED BY: your reviewer
    // DONE WHEN:  the explanation is in your PR description, in your own words.

}
