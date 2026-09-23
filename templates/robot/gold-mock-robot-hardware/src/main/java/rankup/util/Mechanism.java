package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// FILE:        util/Mechanism.java
// STEPS HERE:  4
// GUIDE:       GUIDE_URL  (section "Robot Gold")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: all fields are private, no port numbers appear outside Constants, and you can
//              explain in review what the lambda and method reference are doing.
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
    // READ:       Guide > Robot Gold > Resources #2
    // CHECKED BY: MechanismCheck
    // DONE WHEN:  Mechanism declares stop() and getName(), and Intake and Shooter both
    //             implement it.

}
