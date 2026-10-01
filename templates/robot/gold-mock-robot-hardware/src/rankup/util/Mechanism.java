package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  4
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Robot track > Gold tab)
// ============================================================
//
// An interface is a promise: "whatever I am, I can do these things." Code that only needs
// to stop a mechanism can take a Mechanism and never care whether it got an intake, a
// shooter, or something nobody has built yet.

public interface Mechanism {

    // STEP 4: Declare what every mechanism can do
    // WHAT:       Declare two methods in this interface — no bodies, just the signatures
    //             ending in a semicolon:
    //               void stop();
    //               String getName();
    // WHY:        In a real emergency stop you want to loop over everything on the robot and
    //             stop it, without a giant if/else asking what each thing is. An interface
    //             is how you say "these are all stoppable" and mean it.
    // CONCEPTS:   Interfaces, abstract methods, contracts, why an interface has no bodies
    // DONE WHEN:  Mechanism declares stop() and getName(), and Intake and Shooter both
    //             implement it.

}
