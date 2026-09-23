package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// ============================================================
// RANK:        Robot Diamond: Simple Motor Subsystem
// FILE:        Robot.java
// STEPS HERE:  10
// GUIDE:       GUIDE_URL  (section "Robot Diamond")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: nothing outside the subsystem touches the TalonFX directly, the limit switch
//              reliably blocks forward motion, and all bindings live in RobotContainer.
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
        // STEP 10: Wire up the simulation
        // WHAT:       Call MotorSimHelper.update(...) here, passing your subsystem's TalonFX,
        //             so the simulated encoder actually moves. You will need a MotorSimHelper
        //             and a way to reach the motor — since the TalonFX is private (step 1),
        //             the tidy way is a method on your subsystem that does the sim update
        //             itself, and call that from here.
        //             Then: run the simulation, find the DIO section in the Sim GUI, and
        //             toggle your limit switch's channel while the motor is running forward.
        // WHY:        Simulation is where you find out your logic is wrong without breaking
        //             anything. Every hour spent in sim is an hour not spent debugging on a
        //             robot with eight people waiting.
        // CONCEPTS:   Simulation, simulationPeriodic, DIO simulation, keeping hardware private
        // READ:       Guide > Robot Diamond > Resources #4
        // CHECKED BY: your reviewer (they will watch you do it)
        // DONE WHEN:  the position on SmartDashboard climbs while the motor runs, and
        //             toggling the DIO in the Sim GUI stops forward motion.
    }
}
