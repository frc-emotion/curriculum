package rankup;

import java.util.ArrayList;
import rankup.subsystems.Grab;
import rankup.subsystems.Sub;
import rankup.subsystems.Shot;
import rankup.util.Btn;

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

public class App {
    public static void main(String[] args) {
        Grab grab = new Grab();
        Shot shot = new Shot();
        ArrayList<Sub> parts = new ArrayList<>();

        Btn grabBtn = new Btn(() -> grab.run());
        Btn shotBtn = new Btn(shot::stop);

        grabBtn.press();
        shotBtn.press();

        grab.runAtSpeed(() -> 0.42);

        parts.add(grab);
        parts.add(shot);

        for (int i = 0; i < 5; i++) {
            for (Sub part : parts) {
                part.periodic();
            }
        }

        System.out.println("Class vs object: A class is like a cookie cutter, and an object is the cookie you make with it.");
    }
}
