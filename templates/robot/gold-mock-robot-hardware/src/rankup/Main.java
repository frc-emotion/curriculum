package rankup;

import java.util.ArrayList;
import rankup.subsystems.Intake;
import rankup.subsystems.MockSubsystem;
import rankup.subsystems.Shooter;
import rankup.util.Button;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  3, 7, 8, 9
// ============================================================
//
// This is the fake robot's driver station: where the objects get created, wired to buttons,
// and ticked. Nothing here talks to real hardware, but the shape is exactly what
// RobotContainer.java looks like at Platinum and in Rebuilt-2026.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import java.util.ArrayList;
//   import rankup.subsystems.Grab;
//   import rankup.subsystems.Sub;
//   import rankup.subsystems.Shot;
//   import rankup.util.Btn;
//   import rankup.util.Motor;

public class Main {
    public static void main(String[] args) {
        Intake grab = new Intake();
        Shooter shot = new Shooter();
        ArrayList<MockSubsystem> parts = new ArrayList<>();

        Button grabBtn = new Button(() -> grab.run());
        Button shotBtn = new Button(shot::stop);

        grabBtn.press();
        shotBtn.press();

        grab.runAtSpeed(() -> 0.42);

        parts.add(grab);
        parts.add(shot);

        for (int i = 0; i < 5; i++) {
            for (MockSubsystem part : parts) {
                part.periodic();
            }
        }

        System.out.println("Class vs object: A class is like a cookie cutter, and an object is the cookie you make with it.");
    }
}
