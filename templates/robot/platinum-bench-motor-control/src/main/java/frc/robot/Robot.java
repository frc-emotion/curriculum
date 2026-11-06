package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// ============================================================
// RANK:        Robot Platinum: Bench Motor Control
// STEPS HERE:  1, 2, 5, 6
// ============================================================
//
// This is the robot lifecycle. WPILib calls these methods for you, and each one has a very
// specific job based on the robot mode and the timing of the loop.

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;

    // Runs once 
    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
    }

    // Runs about every 20 ms; robot is powered.
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    // robot enters the Disabled state.
    @Override
    public void disabledInit() {
        System.out.println("Robot disabled.");
        robotContainer.stopMotor();
    }

    //  every 20 ms; robot is disabled.
    @Override
    public void disabledPeriodic() {
    }

    // robot enters Autonomous mode.
    @Override
    public void autonomousInit() {
        System.out.println("Robot autonomous.");
    }

    // every 20 ms Autonomous is active.
    @Override
    public void autonomousPeriodic() {
    }

    // Teleop mode.
    @Override
    public void teleopInit() {
        System.out.println("Robot teleop.");
        robotContainer.stopMotor();
    }

    // every 20 ms; Teleop is active.
    @Override
    public void teleopPeriodic() {
        double output = 0.0;

        if (robotContainer.isForwardPressed()) {
            output = Constants.MOTOR_SPEED;
        } else if (robotContainer.isReversePressed()) {
            output = -Constants.MOTOR_SPEED;
        }

        robotContainer.setMotorOutput(output);
        SmartDashboard.putNumber("Motor Applied Output", robotContainer.getMotorOutput());
    }

    // every 20 ms; robot is in Simulation mode.
    @Override
    public void simulationPeriodic() {
    }
}
