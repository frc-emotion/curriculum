package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  1
// ============================================================
//
// A pretend motor controller. At Platinum this becomes a real TalonFX, and the shape barely
// changes: something owns a motor, nobody else touches it, and every speed gets clamped on
// the way in.

public class Mot {
    private final String name;
    private final int port;
    private double speed;

    public Mot(int port) {
        this("Mot-" + port, port);
    }

    public Mot(String name, int port) {
        this.name = name;
        this.port = port;
        this.speed = 0.0;
    }

    public void setSpeed(double speed) {
        this.speed = clamp(speed, -1.0, 1.0);
    }

    public double getSpeed() {
        return this.speed;
    }

    public String getName() {
        return this.name;
    }

    public int getPort() {
        return this.port;
    }

    private double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
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
    // DONE WHEN:  a new Motor starts at speed 0, setSpeed(1.5) leaves it at 1.0, and two
    //             Motor objects never affect each other.

}
