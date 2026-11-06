package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


// ============================================================
// RANK:        Robot Platinum: Bench Motor Control
// STEPS HERE:  4, 5, 7
// ============================================================
//
// RobotContainer assembles the robot: it creates the motor, configures it once, and keeps
// the controller that drives it.

public class RobotContainer {
    private final TalonFX motor = new TalonFX(Constants.MOTOR_CAN_ID);
    private final CommandXboxController driverController =
            new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

    public RobotContainer() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = 40.0;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        motor.getConfigurator().apply(config);
    }

    public void setMotorOutput(double output) {
        motor.setControl(new DutyCycleOut(output));
    }

    public void stopMotor() {
        setMotorOutput(0.0);
    }

    public boolean isForwardPressed() {
        return driverController.a().getAsBoolean();
    }

    public boolean isReversePressed() {
        return driverController.b().getAsBoolean();
    }

    public double getMotorOutput() {
        return motor.get();
    }

    public Command getAutonomousCommand() {
        return Commands.none();
    }
}
