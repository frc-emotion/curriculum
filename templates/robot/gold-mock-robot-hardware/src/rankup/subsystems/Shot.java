package rankup.subsystems;

import rankup.Cfg;
import rankup.util.Thing;
import rankup.util.Mot;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  6
// ============================================================
//
// The thing that throws game pieces. Same shape as the Intake, which is the point: once you
// know one subsystem you know them all.
//
// Imports you will need (add them yourself, under the package line):
//   import rankup.Constants;
//   import rankup.util.Mechanism;
//   import rankup.util.Motor;

public class Shot extends Sub implements Thing {
    private final Mot motor;

    public Shot() {
        super("Shot");
        motor = new Mot(Cfg.SHOT_MOTOR_PORT);
        println("Shot subsystem created");
    }

    public void run() {
        motor.setSpeed(Cfg.SHOT_SPEED);
    }

    public void stop() {
        motor.setSpeed(0.0);
    }

    @Override
    public void periodic() {
        println(getName() + " speed: " + motor.getSpeed());
    }
}
