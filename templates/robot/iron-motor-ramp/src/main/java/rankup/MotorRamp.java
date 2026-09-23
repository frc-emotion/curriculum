package rankup;

// ============================================================
// RANK:        Robot Iron: Motor Ramp Simulator
// FILE:        MotorRamp.java
// STEPS HERE:  1 to 9
// GUIDE:       GUIDE_URL  (section "Robot Iron")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: both ramps stop exactly at their targets, no logic is copy-pasted where a
//              method call would work, and each method does one clearly named job.
// ============================================================
//
// Slamming a motor from 0 to full power is how you strip a gear, brown out the radio, and
// tip a robot over. Real code ramps: it walks the speed up a little at a time. That is what
// you are building — a simulator for the ramp, so you can watch the numbers without
// breaking anything.
//
// At Copper the method signatures were written for you. Not here. Writing the method — the
// name, what it takes, what it gives back — is the skill this rank is about. Each STEP
// comment tells you the exact signature the checks look for. Type it exactly: same name,
// same parameter types, same order, same return type.
//
// Bring your DEADBAND idea from Copper with you. Step 1 is the same thought, except this
// time the deadband is a parameter, so the caller decides how big it is.
//
// You will need this import once you get to step 5:
//   import java.util.ArrayList;
// Add it yourself, just below the `package` line.

public class MotorRamp {

    // STEP 6: Declare the speed log
    // WHAT:       Declare a class-level list named speedLog:
    //               static ArrayList<Double> speedLog
    //             Create the list right where you declare it, so it is ready to use.
    //             Every call to setMotorSpeed adds the clamped speed it was given.
    // WHY:        Every real robot logs what it commanded, because when something goes wrong
    //             in a match the log is the only witness. This is a tiny version of the
    //             telemetry in Rebuilt-2026.
    // CONCEPTS:   ArrayList, generics (the <Double> part), class-level state, autoboxing
    // READ:       Guide > Robot Iron > Resources #4
    // CHECKED BY: SetMotorSpeedCheck, SourceScanCheck (an ArrayList<Double> must appear)
    // DONE WHEN:  the list grows by exactly one entry per setMotorSpeed call, and main
    //             prints its size at the end.

    public static void main(String[] args) {

        // STEP 7: Swerve module speeds, average and max
        // WHAT:       In main, create a `double[]` holding 4 swerve module speeds (pick your
        //             own values). Then write these two methods and call them on it:
        //               static double average(double[] speeds)
        //               static double max(double[] speeds)
        //             Print both results with labels.
        // WHY:        A swerve drive has four modules. "Is one module dragging?" is answered
        //             by comparing each one against the average, and "are we saturating?" by
        //             looking at the max. Arrays are how you hold a fixed set of things like
        //             that.
        // CONCEPTS:   Arrays, array length, looping over an array, accumulating a total
        // READ:       Guide > Robot Iron > Resources #4
        // CHECKED BY: ArrayMathCheck
        // DONE WHEN:  average and max give the right answers for any array you hand them,
        //             including an array with one element.

        // STEP 8 (continued): call printRampGrid
        // WHAT:       Call printRampGrid(3, 5) from main.
        // WHY:        Three motors, five steps each — a small version of what it looks like
        //             to bring a whole drivetrain up together.
        // CONCEPTS:   Calling a method with arguments
        // READ:       Guide > Robot Iron > Resources #1
        // CHECKED BY: RampGridCheck
        // DONE WHEN:  running the program prints three motor lines.

        // STEP 9: Run both ramps
        // WHAT:       In main, call rampUp(1.0, 0.25) and then rampUp(0.9, 0.2). Paste both
        //             outputs into your pull request description.
        //             At the end of main, print the size of speedLog.
        // WHY:        The second ramp is the interesting one: 0.2 does not divide 0.9 evenly,
        //             so the last step has to be shortened. Reading your own output is how
        //             you catch an off-by-one before a mechanism does.
        // CONCEPTS:   Calling methods, reading output, edge cases
        // READ:       Guide > Robot Iron > Resources #1 and #2
        // CHECKED BY: your reviewer (the pasted output must match what your code prints)
        // DONE WHEN:  both ramps' output is in your PR, and the last line prints how many
        //             speeds were logged in total.

    }

    // STEP 1: Write isWithinDeadband
    // WHAT:       Write this method:
    //               static boolean isWithinDeadband(double value, double deadband)
    //             It returns true when the value is within the deadband of zero — that is,
    //             when the distance from zero is less than or equal to the deadband.
    //             Exactly on the edge counts as inside, same as Copper.
    // WHY:        Same idea as your Copper DEADBAND, with one difference: the size is now a
    //             parameter, so the caller decides. A drivetrain wants a bigger deadband than
    //             an arm, and now one method serves both.
    // CONCEPTS:   Declaring a method, parameters, return types, boolean expressions, Math.abs
    // READ:       Guide > Robot Iron > Resources #3
    // CHECKED BY: DeadbandCheck
    // DONE WHEN:  0.05 with deadband 0.1 is true, 0.5 is false, and exactly 0.1 is true.

    // STEP 2: Write clamp
    // WHAT:       Write this method:
    //               static double clamp(double value, double min, double max)
    //             It returns the value, except never below min and never above max.
    // WHY:        Every number that reaches hardware gets clamped. A motor takes -1.0 to 1.0;
    //             hand it 1.5 and, depending on the controller, you get an error or something
    //             worse. One small method protects every place you set a speed.
    // CONCEPTS:   Declaring a method, multiple parameters, comparisons, returning early
    // READ:       Guide > Robot Iron > Resources #3
    // CHECKED BY: ClampCheck
    // DONE WHEN:  clamp(1.5, -1, 1) is 1.0, clamp(-2, -1, 1) is -1.0, clamp(0.3, -1, 1) is
    //             0.3, and the values exactly on the limits come back unchanged.

    // STEP 3: Write setMotorSpeed
    // WHAT:       Write this method:
    //               static void setMotorSpeed(double speed)
    //             It clamps the speed to the range -1.0 to 1.0 by calling your clamp method,
    //             prints one line in exactly this shape:
    //               Motor set to <clamped speed>
    //             and adds the clamped value to speedLog (step 6).
    // WHY:        This stands in for the real `motor.set(...)` you'll write at Platinum. The
    //             habit of clamping and logging *inside* the one method that talks to
    //             hardware is what keeps the rest of the code from having to remember.
    // CONCEPTS:   void methods, calling your own methods, printing, side effects
    // READ:       Guide > Robot Iron > Resources #3
    // CHECKED BY: SetMotorSpeedCheck
    // DONE WHEN:  setMotorSpeed(1.5) prints the clamped value, not 1.5, and speedLog grows
    //             by one.

    // STEP 4: Overload setMotorSpeed
    // WHAT:       Write a second method with the same name and different parameters:
    //               static void setMotorSpeed(double speed, int port)
    //             It prints one line in exactly this shape:
    //               Motor <port> set to <clamped speed>
    //             and also adds the clamped value to speedLog. It must reuse clamp — do not
    //             retype the clamping logic.
    // WHY:        Overloading is how one idea gets two convenient front doors. It is all over
    //             WPILib: the same method name taking different things. Reusing clamp instead
    //             of copying it means a future fix happens in one place.
    // CONCEPTS:   Method overloading, method signatures, reuse over copy-paste
    // READ:       Guide > Robot Iron > Resources #3
    // CHECKED BY: SetMotorSpeedCheck (both versions must exist and both must clamp)
    // DONE WHEN:  setMotorSpeed(2.0, 3) prints the port and the clamped speed, and logs it.

    // STEP 5: Write rampUp
    // WHAT:       Write this method:
    //               static ArrayList<Double> rampUp(double target, double step)
    //             Starting from 0, add `step` each time round the loop, call setMotorSpeed
    //             with each new speed, and collect those speeds into a list that you return.
    //             The first element must be `step`. The LAST element must be exactly
    //             `target`: when the next full step would overshoot, use the target itself
    //             instead and `break` out of the loop.
    //             Examples of the exact lists expected:
    //               rampUp(1.0, 0.25)  ->  [0.25, 0.5, 0.75, 1.0]
    //               rampUp(0.9, 0.2)   ->  [0.2, 0.4, 0.6, 0.8, 0.9]
    // WHY:        This is the shape of nearly every ramp, soft-start and slew limiter on a
    //             real robot. And the last step is the interesting one: if you ignore the
    //             overshoot, you command more than you meant to, every single time.
    // CONCEPTS:   Loops, accumulating a value, `break`, building an ArrayList, returning a
    //             collection
    // READ:       Guide > Robot Iron > Resources #1 and #2
    // CHECKED BY: RampUpCheck, SourceScanCheck (`break` must appear)
    // DONE WHEN:  both example ramps come out exactly right, ending on the target.

    // STEP 8: Write printRampGrid
    // WHAT:       Write this method:
    //               static void printRampGrid(int motors, int steps)
    //             Using a loop inside a loop, print one line per motor, numbered from 1,
    //             each showing `steps` evenly spaced speeds from 1/steps up to 1.0:
    //               Motor 1: 0.2 0.4 0.6 0.8 1.0
    //               Motor 2: 0.2 0.4 0.6 0.8 1.0
    //               Motor 3: 0.2 0.4 0.6 0.8 1.0
    //             (That example is printRampGrid(3, 5).)
    // WHY:        Nested loops are how you walk a grid: every motor, every step. You will see
    //             the same shape in swerve code that touches four modules and two values each.
    // CONCEPTS:   Nested loops, the outer/inner relationship, print vs println
    // READ:       Guide > Robot Iron > Resources #1
    // CHECKED BY: RampGridCheck, SourceScanCheck (a loop inside a loop must appear)
    // DONE WHEN:  printRampGrid(3, 5) prints exactly three lines, each with five speeds
    //             ending at 1.0.

}
