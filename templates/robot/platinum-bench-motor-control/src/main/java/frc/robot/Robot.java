package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// ============================================================
// RANK:        Robot Platinum: Bench Motor Control
// FILE:        Robot.java
// STEPS HERE:  1, 2, 5, 6
// GUIDE:       GUIDE_URL  (section "Robot Platinum")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew build
// PASSES WHEN: the sim demo and bench demo both work, no numbers are hard-coded outside
//              Constants, and the motor stops when disabled.
// ============================================================
//
// This is the robot's schedule. WPILib calls these methods for you — you never call them
// yourself. The field controls which ones run: Disabled, Autonomous and Teleop each have an
// Init that fires once when that mode starts, and a Periodic that fires about every 20
// milliseconds while it lasts.
//
// The Javadoc that usually sits above each method has been removed on purpose. Writing those
// comments is step 1, and you cannot write them accurately without working out what actually
// calls what.

public class Robot extends TimedRobot {

    // STEP 1: Document the lifecycle
    // WHAT:       Write a short comment above EVERY method in this file saying when it runs
    //             and how often. One or two lines each. Be specific: "once, when the robot
    //             is disabled" is useful; "runs sometimes" is not.
    // WHY:        Nearly every confusing robot bug comes from code living in the wrong
    //             method. Something that belongs in teleopInit, put in teleopPeriodic, runs
    //             fifty times a second instead of once. Writing this down now is how you stop
    //             guessing later.
    // CONCEPTS:   The robot lifecycle, init vs periodic, the 20ms loop, driver station modes
    // READ:       Guide > Robot Platinum > Resources #2 and #3
    // CHECKED BY: your reviewer, plus a build warning if any method has no comment above it
    // DONE WHEN:  every method below has an accurate comment, and you can answer "which of
    //             these runs 50 times a second?" without looking.

    // STEP 2: Print something in each Init
    // WHAT:       Print a message in disabledInit, autonomousInit and teleopInit. Then run
    //             the simulation, use the Sim GUI to switch between Disabled, Autonomous and
    //             Teleop, and paste the console output into your PR.
    // WHY:        This is how you prove to yourself that the lifecycle works the way the
    //             comments you just wrote say it does. Reading it is one thing; watching the
    //             lines appear as you flip modes is another.
    // CONCEPTS:   The robot lifecycle, simulation, the Sim GUI driver station
    // READ:       Guide > Robot Platinum > Resources #2
    // CHECKED BY: your reviewer (the pasted output)
    // DONE WHEN:  switching modes in the Sim GUI prints a line each time.

    private final RobotContainer m_robotContainer;

    public Robot() {
        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        // This one line is what makes command-based work. It is already written for you
        // because the scheduler is Diamond's topic, not Platinum's.
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        // STEP 6: Stop the motor when the robot is disabled
        // WHAT:       Make sure the motor is stopped here.
        // WHY:        Disabled means disabled. If the robot is switched off mid-command with
        //             a motor still commanded, whatever it was doing is what it will try to
        //             do the instant it is enabled again — usually into somebody's hand.
        // CONCEPTS:   The disabled state, safe defaults, why init is the right place
        // READ:       Guide > Robot Platinum > Resources #3
        // CHECKED BY: your reviewer (they will test it), plus a build warning
        // DONE WHEN:  disabling the robot in sim, and on the bench, stops the motor every
        //             time.
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        // STEP 5: Drive the motor from the controller
        // WHAT:       While the A button is held, run the motor forward at MOTOR_SPEED.
        //             While B is held, run it backward at the same speed. When neither is
        //             held, stop. Use a DutyCycleOut control request to command the TalonFX.
        //             You create the controller yourself — a CommandXboxController on the
        //             port from Constants is the one to use.
        // WHY:        This is the smallest complete teleop loop: read input, decide, command
        //             hardware, every 20ms. Note what happens if you forget the "stop when
        //             neither is held" part — the motor keeps the last thing you told it,
        //             forever. Motor controllers do not guess.
        // CONCEPTS:   Reading a controller, DutyCycleOut, commanding a TalonFX, the periodic
        //             loop, safe defaults
        // READ:       Guide > Robot Platinum > Resources #4
        // CHECKED BY: your reviewer (sim and bench demo), plus build warnings
        // DONE WHEN:  in simulation, holding A and B moves the motor's applied output the
        //             way you expect, and letting go returns it to zero.

        // STEP 7 (continued from RobotContainer.java): publish the motor's output
        // WHAT:       Every loop, publish the motor's applied output to SmartDashboard.
        // WHY:        You cannot see inside a robot. Telemetry is how you find out whether
        //             the motor is doing what you asked, and it is the first thing anyone
        //             asks for when something goes wrong at competition.
        // CONCEPTS:   SmartDashboard, NetworkTables, telemetry, publishing every loop
        // READ:       Guide > Robot Platinum > Resources #1
        // CHECKED BY: your reviewer, plus a build warning
        // DONE WHEN:  the value appears in the Sim GUI's NetworkTables view and changes as
        //             you hold the buttons.
    }

    @Override
    public void simulationPeriodic() {
    }
}
