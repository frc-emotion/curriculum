package rankup;

// ============================================================
// RANK:        Robot Copper: Joystick Direction Decider
// FILE:        JoystickDecider.java
// STEPS HERE:  1 to 8
// GUIDE:       GUIDE_URL  (section "Robot Copper")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: the output is correct for every test value, including exactly 0.1 and -0.1,
//              and constants use `final` instead of repeated numbers.
// ============================================================
//
// You are writing the thinking part of a drive program. A driver pushes a joystick; your
// code decides what that means. Every robot we have ever built starts with a version of
// this, and every one of them has been driven into a wall by a program that got it wrong.
//
// About the three methods below: you have not been taught how to WRITE a method yet —
// that is Iron. At Copper, the method signatures are already here and you only fill in
// what goes between their braces. Do not rename them or change what they take and return;
// the checks call them by name.
//
// Each unfinished method throws an exception on purpose, so you can run the program from
// minute one and watch the checks tell you what is left.

public class JoystickDecider {

    // STEP 2: Declare DEADBAND
    // WHAT:       Declare a class-level constant named DEADBAND, of type double, equal to 0.1.
    //             Write it here, inside the class but outside any method, as:
    //             a `static final double` named DEADBAND.
    // WHY:        A joystick that is "centered" never reads exactly 0.0 — worn springs and
    //             cheap sensors mean it sits at 0.02 or -0.05. Without a deadband, a robot
    //             sitting untouched creeps across the field. That is a real match-losing bug.
    // CONCEPTS:   Constants, `final`, class-level (static) fields, naming in ALL_CAPS
    // READ:       Guide > Robot Copper > Resources #2
    //             (`static` is explained properly at Gold. For now: it means the constant
    //              belongs to the class itself, so every method here can use it.)
    // CHECKED BY: SourceScanCheck (DEADBAND must be declared `final`)
    // DONE WHEN:  DEADBAND exists here and the methods below use it instead of typing 0.1.

    // STEP 5: Declare MAX_SPEED
    // WHAT:       Declare a class-level constant named MAX_SPEED, of type double, equal to 0.8,
    //             as a `static final double`.
    // WHY:        Full power is rarely what you want. Capping speed is how we protect a
    //             mechanism, a battery, and the person standing next to the robot. Changing
    //             one constant is how you retune the whole robot in one place.
    // CONCEPTS:   Constants, `final`, scaling a value by multiplication
    // READ:       Guide > Robot Copper > Resources #2
    // CHECKED BY: ScaleSpeedCheck, SourceScanCheck (MAX_SPEED must be declared `final`)
    // DONE WHEN:  MAX_SPEED exists here and scaleSpeed uses it.

    public static void main(String[] args) {

        // STEP 1: Declare and print four variables
        // WHAT:       Inside main, declare four local variables and print each one with a
        //             label so a human can tell what they are:
        //               motorSpeed  (double)
        //               motorPort   (int)
        //               isEnabled   (boolean)
        //               robotName   (String)
        //             Pick sensible values yourself. A label means output like
        //             `Motor speed: 0.5`, not a bare `0.5`.
        // WHY:        These four types cover nearly everything a robot program tracks: how
        //             fast (double), which port (int), is it on (boolean), and what is it
        //             called (String). Printing with labels is the debugging tool you will
        //             reach for more than any other, all season.
        // CONCEPTS:   Variable declaration, primitive types, String, System.out.println
        // READ:       Guide > Robot Copper > Resources #2
        // CHECKED BY: your reviewer (paste the output into your PR)
        // DONE WHEN:  `./gradlew run` prints four labelled lines.

        // STEP 5 (continued): print a scaled value
        // WHAT:       After the methods work, call scaleSpeed with a joystick value and print
        //             the result with a label.
        // WHY:        Seeing the scaled number next to the raw one is how you check that a
        //             speed cap is actually doing something.
        // CONCEPTS:   Calling a method, printing a returned value
        // READ:       Guide > Robot Copper > Resources #2
        // CHECKED BY: your reviewer
        // DONE WHEN:  `./gradlew run` prints a scaled speed.

        // STEP 8: Call decideDirection with every test value
        // WHAT:       Call decideDirection once for each of these joystick values, one call
        //             per line, printing the value and the answer:
        //               0.5, -0.8, 0.05, 0.1, -0.1, 1.0
        //             Then paste the output into your pull request description.
        //             (Yes, six separate lines. Loops are Iron; write them out.)
        // WHY:        0.1 and -0.1 are exactly on the deadband edge, and edge cases are where
        //             robot code breaks. Reading your own output is how you catch that before
        //             a match does.
        // CONCEPTS:   Calling methods, arguments, reading your own output
        // READ:       Guide > Robot Copper > Resources #4
        // CHECKED BY: your reviewer (the pasted output must match what your code prints)
        // DONE WHEN:  six lines of output are in your PR description.

    }

    // STEP 3: Fill in decideDirection
    // WHAT:       Fill in the body of decideDirection so it returns:
    //               "FORWARD"  when the joystick is above the deadband,
    //               "BACKWARD" when it is below the negative deadband,
    //               "STOP"     otherwise.
    //             Use Math.abs to decide whether the stick is inside the deadband.
    //             Exactly 0.1 and exactly -0.1 must both be "STOP" — the deadband edge
    //             counts as stopped.
    // WHY:        This is the first decision every teleop program makes. Getting the edge
    //             wrong is what makes a robot twitch when nobody is touching the controls.
    // CONCEPTS:   if / else if / else, comparison operators, Math.abs, returning a value
    // READ:       Guide > Robot Copper > Resources #4
    // CHECKED BY: DecideDirectionCheck, SourceScanCheck (Math.abs must appear)
    // DONE WHEN:  every value from step 8 gives the answer you'd expect, and 0.1 and -0.1
    //             both give "STOP".

    // STEP 4: Handle the disabled case
    // WHAT:       When isEnabled is false, decideDirection returns "DISABLED" no matter what
    //             the joystick says.
    // WHY:        A disabled robot must not move, ever. On a real field the referees can
    //             disable you mid-match, and "the driver was still pushing the stick" is not
    //             an excuse the robot gets to make.
    // CONCEPTS:   boolean logic, early return, order of checks
    // READ:       Guide > Robot Copper > Resources #4
    // CHECKED BY: DecideDirectionCheck
    // DONE WHEN:  any joystick value with isEnabled false returns "DISABLED".

    // STEP 6: Rewrite the STOP check with a ternary
    // WHAT:       Rewrite the part of decideDirection that decides between stopped and moving
    //             so it uses the ternary operator ( condition ? valueIfTrue : valueIfFalse )
    //             on a single line, instead of an if/else.
    // WHY:        Ternaries show up constantly in robot code, usually for exactly this: pick
    //             one of two values. You need to be able to read one at a glance, which means
    //             writing a few yourself first.
    // CONCEPTS:   Ternary (conditional) operator, expressions vs statements
    // READ:       Guide > Robot Copper > Resources #14
    // CHECKED BY: SourceScanCheck (a ternary must appear in decideDirection),
    //             DecideDirectionCheck (behaviour must not change)
    // DONE WHEN:  a ternary is doing the stop-or-move decision and every check still passes.

    static String decideDirection(double joystickValue, boolean isEnabled) {
        throw new UnsupportedOperationException("STEP 3 not done yet");
    }

    // STEP 5 (continued): fill in scaleSpeed
    // WHAT:       Fill in scaleSpeed so it returns the joystick value multiplied by MAX_SPEED.
    // WHY:        This is how every speed cap on the robot is applied: one multiply, one
    //             constant, one place to change it when the drivers say it's too fast.
    // CONCEPTS:   Arithmetic operators, using a constant, returning a value
    // READ:       Guide > Robot Copper > Resources #2
    // CHECKED BY: ScaleSpeedCheck
    // DONE WHEN:  scaleSpeed(1.0) gives 0.8 and scaleSpeed(-0.5) gives -0.4.

    static double scaleSpeed(double joystickValue) {
        throw new UnsupportedOperationException("STEP 5 not done yet");
    }

    // STEP 7: Fill in driveModeName with a switch
    // WHAT:       Fill in driveModeName using a `switch` statement on driveMode:
    //               0 returns "TANK"
    //               1 returns "ARCADE"
    //               anything else returns "UNKNOWN"
    // WHY:        Real robots have modes — tank, arcade, field-oriented, climb. A switch is
    //             how you pick between a fixed list of them without a stack of if/else.
    // CONCEPTS:   switch, case, default, why `break` or `return` matters in a switch
    // READ:       Guide > Robot Copper > Resources #7
    // CHECKED BY: DriveModeNameCheck, SourceScanCheck (a `switch` must appear)
    // DONE WHEN:  0, 1, and something silly like 7 all give the right answer.

    static String driveModeName(int driveMode) {
        throw new UnsupportedOperationException("STEP 7 not done yet");
    }
}
