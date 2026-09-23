package rankup;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// FILE:        Main.java
// STEPS HERE:  3, 7, 8, 9
// GUIDE:       GUIDE_URL  (section "Robot Gold")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: all fields are private, no port numbers appear outside Constants, and you can
//              explain in review what the lambda and method reference are doing.
// ============================================================
//
// This is the fake robot's driver station: where the objects get created, wired to buttons,
// and ticked. Nothing here talks to real hardware, but the shape is exactly what
// RobotContainer.java looks like at Platinum and in Rebuilt-2026.
//
// Imports you will need as you go (add them yourself, under the package line):
//   import java.util.ArrayList;
//   import rankup.subsystems.Intake;
//   import rankup.subsystems.MockSubsystem;
//   import rankup.subsystems.Shooter;
//   import rankup.util.Button;
//   import rankup.util.Motor;

public class Main {

    public static void main(String[] args) {

        // STEP 3: Two motors, proving objects are separate things
        // WHAT:       Create two Motor objects with different names and ports, set a speed on
        //             one of them only, then print both speeds with labels.
        // WHY:        This is the whole idea of a class versus an object. Motor is the
        //             blueprint; each `new Motor(...)` is a separate physical motor with its
        //             own state. Changing one must not change the other — which sounds
        //             obvious until the day someone makes the speed field static and the
        //             whole robot moves at once.
        // CONCEPTS:   Classes vs objects, `new`, constructors, object state
        // READ:       Guide > Robot Gold > Resources #1
        // CHECKED BY: MotorCheck (two motors stay independent)
        // DONE WHEN:  the printout shows one motor's speed changed and the other's didn't.

        // STEP 7 (continued): bind two buttons
        // WHAT:       Create two Button objects and press them:
        //               - one built with a lambda:            () -> intake.run()
        //               - one built with a method reference:  shooter::stop
        //             Press each one and show in the output that something happened.
        // WHY:        This is how every control on the real robot is wired. In Rebuilt-2026 a
        //             button press is handed a chunk of code to run later, exactly like this.
        // CONCEPTS:   Lambdas, method references, Runnable, passing behaviour as a value
        // READ:       Guide > Robot Gold > Resources #3 and #4
        // CHECKED BY: SourceScanCheck (both `->` and `::` must appear in Main)
        // DONE WHEN:  pressing the buttons visibly runs the intake and stops the shooter.

        // STEP 8 (continued): drive the intake from a supplier
        // WHAT:       Call intake.runAtSpeed(...) and pass a lambda that returns a pretend
        //             joystick value.
        // WHY:        A DoubleSupplier is a promise to produce a number *later*, whenever the
        //             subsystem asks. That is how a real joystick gets read fresh every loop
        //             instead of once at startup — you'll do exactly this at Diamond.
        // CONCEPTS:   DoubleSupplier, lambdas that return a value, deferred evaluation
        // READ:       Guide > Robot Gold > Resources #3 and #4
        // CHECKED BY: RunAtSpeedCheck
        // DONE WHEN:  the intake's motor ends up at the value your lambda returned.

        // STEP 9: Five robot ticks
        // WHAT:       Put all your subsystems into an ArrayList<MockSubsystem>, then loop 5
        //             times. Each time round, call periodic() on every subsystem in the list.
        //             Press a button somewhere between ticks so you can see the effect.
        // WHY:        This is the robot loop, ~50 times a second on a real robot. Holding
        //             subsystems in one list and ticking them all is exactly what WPILib's
        //             CommandScheduler does for you at Platinum.
        // CONCEPTS:   ArrayList of a supertype, polymorphism, looping, the periodic pattern
        // READ:       Guide > Robot Gold > Resources #2
        // CHECKED BY: your reviewer
        // DONE WHEN:  running the program prints 5 rounds of subsystem output, and the
        //             printed speeds change after a button press.

        // STEP 10: Class vs object, in your own words
        // WHAT:       In your pull request description, write one sentence giving a
        //             real-world analogy for the difference between a class and an object.
        // WHY:        If you can explain it in one sentence without saying "blueprint", you
        //             actually understand it. (You may say blueprint. But try not to.)
        // CONCEPTS:   Classes vs objects
        // READ:       Guide > Robot Gold > Resources #1
        // CHECKED BY: your reviewer
        // DONE WHEN:  the sentence is in your PR description.

    }
}
