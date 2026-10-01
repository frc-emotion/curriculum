package rankup.subsystems;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  5
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Robot track > Gold tab)
// ============================================================
//
// The shared parent of every subsystem on this fake robot. At Platinum, WPILib's
// SubsystemBase plays exactly this role — it gives every subsystem a name and a periodic()
// that runs every loop, so each individual subsystem doesn't have to reinvent either.

public class MockSubsystem {

    // STEP 5: Build the shared parent
    // WHAT:       Give this class:
    //               - a private String field for the name
    //               - a constructor `public MockSubsystem(String name)` that stores it
    //               - `public String getName()`
    //               - `public void periodic()` that prints the subsystem's name
    // WHY:        Every subsystem needs a name and a heartbeat. Putting both here means
    //             Intake and Shooter get them for free — that is what inheritance buys you.
    //             Write it twice instead and you get to fix every bug twice.
    // CONCEPTS:   Superclasses, constructors, inheritance, the periodic pattern
    // DONE WHEN:  Intake and Shooter both extend this class and both get getName() without
    //             writing it themselves.

}
