package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  4
// ============================================================
//
// An interface is a promise: "whatever I am, I can do these things." Code that only needs
// to stop a mechanism can take a Mechanism and never care whether it got an intake, a
// shooter, or something nobody has built yet.

public interface Mechanism {

    void stop();
    String getName();


}