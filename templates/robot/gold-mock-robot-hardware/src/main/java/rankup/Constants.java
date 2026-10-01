package rankup;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  2
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Robot track > Gold tab)
// ============================================================
//
// Every number that describes the robot lives here: CAN IDs, ports, speeds, gear ratios.
// Rebuilt-2026 has a whole Constants folder for exactly this reason. When a motor gets
// rewired at competition, you change one line here instead of hunting through the code at
// 11pm in a hotel lobby.

public final class Constants {

    // STEP 2: Add the ports and the intake speed
    // WHAT:       Add three constants to this class:
    //               public static final int    INTAKE_MOTOR_PORT   = 1
    //               public static final int    SHOOTER_MOTOR_PORT  = 2
    //               public static final double INTAKE_SPEED        = 0.8
    //             Your Shooter needs a running speed too (step 6). Add a constant for it
    //             here as well, named however you like — just don't type the number
    //             anywhere else.
    // WHY:        `new Motor("Intake", 1)` tells a reader nothing. `new Motor("Intake",
    //             Constants.INTAKE_MOTOR_PORT)` tells them everything. And when the port
    //             changes, there is exactly one place to change it.
    // CONCEPTS:   `public static final`, constants as shared configuration, naming
    // DONE WHEN:  the subsystems build their motors from these constants and no port number
    //             is typed anywhere else.

}
