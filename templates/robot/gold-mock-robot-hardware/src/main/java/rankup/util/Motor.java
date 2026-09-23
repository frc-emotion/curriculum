package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// FILE:        util/Motor.java
// STEPS HERE:  1
// GUIDE:       GUIDE_URL  (section "Robot Gold")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: all fields are private, no port numbers appear outside Constants, and you can
//              explain in review what the lambda and method reference are doing.
// ============================================================
//
// A pretend motor controller. At Platinum this becomes a real TalonFX, and the shape barely
// changes: something owns a motor, nobody else touches it, and every speed gets clamped on
// the way in.

public class Motor {

    // STEP 1: Build the Motor class
    // WHAT:       Give this class:
    //               - three PRIVATE fields: a String `name`, an int `port`, a double `speed`
    //               - a constructor `public Motor(String name, int port)` that stores both
    //                 and starts the speed at 0
    //               - `public void setSpeed(double speed)` that clamps to -1.0 .. 1.0 before
    //                 storing it
    //               - `public double getSpeed()`
    //               - `public String getName()`
    //               - `public int getPort()`
    // WHY:        This is encapsulation, and it is the most useful idea at this rank. The
    //             fields are private, so the only way to change the speed is to go through
    //             setSpeed — which means the clamp can never be skipped. If `speed` were
    //             public, any line of code anywhere could write 5.0 into it and nothing
    //             would stop it.
    // CONCEPTS:   Classes, private fields, constructors, `this`, getters, encapsulation
    // READ:       Guide > Robot Gold > Resources #1
    // CHECKED BY: MotorCheck (fields private, clamping, two motors independent)
    // DONE WHEN:  a new Motor starts at speed 0, setSpeed(1.5) leaves it at 1.0, and two
    //             Motor objects never affect each other.

}
