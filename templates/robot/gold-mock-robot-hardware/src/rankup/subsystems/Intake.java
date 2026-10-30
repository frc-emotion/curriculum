package rankup.subsystems;

import java.util.function.DoubleSupplier;
import rankup.Constants;
import rankup.util.Thing;
import rankup.util.Mot;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  6, 8
// ============================================================
//
// The thing that picks game pieces up. On the real robot it is one motor and a lot of
// opinions about wheel material.
//
// Imports you will need (add them yourself, under the package line):
//   import java.util.function.DoubleSupplier;
//   import rankup.Constants;
//   import rankup.util.Mechanism;
//   import rankup.util.Motor;

public class Intake extends MockSubsystem implements Thing {
    private final Mot motor;

    public Intake() {
        super("Grab");
        motor = new Mot(Constants.GRAB_MOTOR_PORT);
        println("Grab subsystem created");
    }

    public void run() {
        motor.setSpeed(Constants.GRAB_SPEED);
    }

    public void stop() {
        motor.setSpeed(0.0);
    }

    @Override
    public void periodic() {
        println(getName() + " speed: " + motor.getSpeed());
    }

    public void runAtSpeed(DoubleSupplier speed) {
        motor.setSpeed(speed.getAsDouble());
    }
}
