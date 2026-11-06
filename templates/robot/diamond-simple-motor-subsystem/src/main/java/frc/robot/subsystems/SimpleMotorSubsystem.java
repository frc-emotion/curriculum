package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.sim.MotorSimHelper;

// ============================================================
// RANK:        Robot Diamond: Simple Motor Subsystem
// STEPS HERE:  1, 2, 3, 4, 5, 6
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

    private final TalonFX motor = new TalonFX(Constants.MOTOR_CAN_ID);
    private final DigitalInput limitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_DIO_CHANNEL);
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0.0);
    private final MotorSimHelper simHelper = new MotorSimHelper(1.0, 0.001);

    public SimpleMotorSubsystem() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = 40.0;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        motor.getConfigurator().apply(config);
    }

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
    // DONE WHEN:  all five exist, and stop() really leaves the motor at zero output.
    public void setSpeed(double speed) {
        if (speed > 0.0 && isAtLimit()) {
            stop();
            return;
        }
        motor.setControl(dutyCycleOut.withOutput(speed));
    }

    public void stop() {
        motor.setControl(dutyCycleOut.withOutput(0.0));
    }

    public double getPositionRotations() {
        return motor.getPosition().getValueAsDouble();
    }

    public double getVelocityRps() {
        return motor.getVelocity().getValueAsDouble();
    }

    public boolean isAtLimit() {
        return !limitSwitch.get();
    }

    public void zeroEncoder() {
        motor.setPosition(0.0);
    }

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
    // DONE WHEN:  isAtLimit() is true exactly when the switch is physically pressed.

    // STEP 4: Publish telemetry every loop
    // WHAT:       Override periodic() and publish three things to SmartDashboard: the
    //             position, the velocity, and whether the limit switch is pressed.
    // WHY:        periodic() runs every 20ms for every registered subsystem, which makes it
    //             the natural home for "tell the world what I'm doing". When something
    //             misbehaves at competition, these three numbers are what you look at.
    // CONCEPTS:   periodic(), SmartDashboard, NetworkTables, telemetry
    // DONE WHEN:  all three values appear in the Sim GUI and change as the motor runs.
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Motor Pos", getPositionRotations());
        SmartDashboard.putNumber("Motor Vc", getVelocityRps());
        SmartDashboard.putBoolean("Limit Switch Pressed", isAtLimit());
    }

    // STEP 5: Make the limit switch actually stop the motor
    // WHAT:       Change setSpeed so that while the limit switch is pressed, a forward
    //             command is refused (the motor stays stopped instead). Backward must still
    //             work — otherwise the mechanism is stuck at the limit forever.
    // WHY:        This is the whole point of the switch. Putting the rule inside setSpeed
    //             means EVERY caller gets it for free: commands, the default command, code
    //             someone writes next season. A safety rule that lives in one command is a
    //             safety rule waiting to be bypassed.
    // CONCEPTS:   Guard clauses, safety interlocks, why rules belong at the lowest level
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
    // DONE WHEN:  holding A runs the motor and releasing A stops it, every time.
    public Command runForward() {
        return this.startEnd(
                () -> setSpeed(Constants.MOTOR_SPEED),
                this::stop);
    }

    public Command runReverse() {
        return this.startEnd(
                () -> setSpeed(-Constants.MOTOR_SPEED),
                this::stop);
    }

    public void updateSimulation() {
        simHelper.update(motor);
    }

    // STEP 11: Explain the split
    // WHAT:       In your pull request, write two or three sentences on why hardware lives in
    //             subsystems and actions live in commands.
    // WHY:        This is the idea the whole command-based framework is built on. If you can
    //             say it plainly, the rest of WPILib stops looking arbitrary — and at Emerald
    //             you will be making this exact decision yourself, without a STEP comment
    //             telling you which is which.
    // CONCEPTS:   Subsystems vs commands, ownership, the scheduler's job
    // DONE WHEN:  the explanation is in your PR description, in your own words.

}
