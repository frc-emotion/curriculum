package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

// ============================================================
// RANK:        Robot Emerald: Arm to Presets
// FILE:        commands/PTuneCommand.java
// STEPS HERE:  1, 2, 3
// GUIDE:       GUIDE_URL  (section "Robot Emerald")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: the arm reaches each preset without sustained oscillation, commands end at
//              their setpoints, and the angle limits hold.
// ============================================================
//
// At Diamond you built commands as factory methods on the subsystem. This one is a real
// command class — the style Rebuilt-2026 uses for anything with state of its own. A command
// class has four methods you can override: initialize(), execute(), end(boolean) and
// isFinished(). Look them up in resource #4; the names say what they do.
//
// This command exists so you can watch a P controller behave, and feel what changing kP does
// before any of it matters.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import edu.wpi.first.math.controller.PIDController;
//   import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//   import frc.robot.subsystems.ArmSubsystem;

public class PTuneCommand extends Command {

    // STEP 1: Drive the motor to a position with P alone
    // WHAT:       Make this command take the arm and a target, with exactly this constructor:
    //               public PTuneCommand(ArmSubsystem arm, double targetRotations)
    //             Use a WPILib PIDController with only kP set (leave kI and kD at 0) to drive
    //             the motor's encoder position toward the target. Declare the subsystem as a
    //             requirement with addRequirements(...).
    // WHY:        P is proportional: the further away you are, the harder it pushes. That one
    //             idea is most of what a controller does, and everything else is a patch on
    //             its weaknesses. Feeling it directly — too weak, too strong, just right — is
    //             worth more than any amount of reading.
    // CONCEPTS:   PIDController, proportional control, setpoint vs measurement, error,
    //             command lifecycle, addRequirements
    // READ:       Guide > Robot Emerald > Resources #1 and #3
    // CHECKED BY: SourceScanCheck (a PIDController must appear), your reviewer
    // DONE WHEN:  running this command in simulation visibly moves the arm toward the target.

    // STEP 2: Make kP tunable while the robot is running
    // WHAT:       Read kP from SmartDashboard every loop instead of hard-coding it, so you
    //             can change it live in the Sim GUI. Put a default value there when the
    //             command starts, then read it back each loop.
    // WHY:        Tuning by editing code, rebuilding and redeploying takes two minutes a try.
    //             Tuning live takes two seconds. On a real robot, at a competition, with a
    //             queue behind you, that difference decides whether the mechanism gets tuned
    //             at all.
    // CONCEPTS:   SmartDashboard.getNumber, live tuning, NetworkTables, default values
    // READ:       Guide > Robot Emerald > Resources #3
    // CHECKED BY: SourceScanCheck (SmartDashboard.getNumber must appear)
    // DONE WHEN:  typing a new kP into the Sim GUI changes the arm's behaviour immediately,
    //             without a rebuild.

    // STEP 3: Record what you saw
    // WHAT:       Tune in simulation until you can produce all three of these, then write
    //             them in your PR description:
    //               - a kP that OVERSHOOTS (sails past the target, then comes back)
    //               - a kP that OSCILLATES (never settles, wobbles around the target)
    //               - your final kP
    //             One sentence for each saying what you saw.
    // WHY:        Every tuning session you will ever do is this loop: change a number, watch,
    //             describe what happened. Writing it down is what turns "I fiddled until it
    //             worked" into a skill you can use on a mechanism you have never seen.
    // CONCEPTS:   Overshoot, oscillation, steady-state behaviour, systematic tuning
    // READ:       Guide > Robot Emerald > Resources #1 and #2
    // CHECKED BY: your reviewer
    // DONE WHEN:  three kP values and three observations are in your PR.

}
