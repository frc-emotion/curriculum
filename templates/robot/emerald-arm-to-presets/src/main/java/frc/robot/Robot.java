package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// ============================================================
// RANK:        Robot Emerald: Arm to Presets
// FILE:        Robot.java
// STEPS HERE:  none — this file is plumbing
// GUIDE:       GUIDE_URL  (section "Robot Emerald")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: the arm reaches each preset without sustained oscillation, commands end at their
//              setpoints, and the angle limits hold.
// ============================================================
//
// You documented all of this at Platinum, so it comes filled in this time. The short version
// is below each method.

public class Robot extends TimedRobot {

    private final RobotContainer m_robotContainer;

    public Robot() {
        m_robotContainer = new RobotContainer();
    }

    // Every 20ms, in every mode. Running the scheduler here is what makes commands work at
    // all: it checks triggers, starts and ends commands, and calls every subsystem's
    // periodic().
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    // Once, when the robot becomes disabled.
    @Override
    public void disabledInit() {
    }

    // Every 20ms while disabled.
    @Override
    public void disabledPeriodic() {
    }

    // Once, when autonomous starts.
    @Override
    public void autonomousInit() {
    }

    // Every 20ms during autonomous.
    @Override
    public void autonomousPeriodic() {
    }

    // Once, when teleop starts.
    @Override
    public void teleopInit() {
    }

    // Every 20ms during teleop. Notice there is nothing here — at this rank the controller
    // drives the motor through commands and a default command, not through this method.
    @Override
    public void teleopPeriodic() {
    }

    // Every 20ms, but only when running in simulation.
    @Override
    public void simulationPeriodic() {
        // Simulation plumbing. ArmSimHelper is finished for you — see sim/ArmSimHelper.java.
        // Your ArmSubsystem needs a method that steps its own simulation, and this is where
        // you call it from. Same pattern as Diamond.
        //
        // This file has no assessed steps. Everything happens in Constants.java,
        // subsystems/ArmSubsystem.java, commands/PTuneCommand.java and RobotContainer.java.
    }
}
