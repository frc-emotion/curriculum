package rankup;

// ============================================================
// RANK:        Robot Copper: Joystick Direction Decider
// STEPS HERE:  1 to 8
// ============================================================
//
// You are writing the thinking part of a drive program. A driver pushes a joystick; your
// code decides what that means. Every robot we have ever built starts with a version of
// this, and every one of them has been driven into a wall by a program that got it wrong.
//
// About the three methods below: you have not been taught how to WRITE a method yet —
// that is Iron. At Copper, the method signatures are already here and you only fill in
// what goes between their braces. Do not rename them or change what they take and return;
// your reviewer and later ranks expect these exact names.
//
// Each unfinished method throws an exception on purpose, so you can run the program from
// minute one and see which methods are still unfinished.


public class JoystickDecider {
    static final double JOYSTICK_DEADZONE = 0.1;
    static final double MAX_SPEED = 1.0;

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
        // DONE WHEN:  Hitting the run button prints four labelled lines.

        // STEP 5 (continued): print a scaled value
        // WHAT:       After the methods work, call scaleSpeed with a joystick value and print
        //             the result with a label.
        // WHY:        Seeing the scaled number next to the raw one is how you check that a
        //             speed cap is actually doing something.
        // CONCEPTS:   Calling a method, printing a returned value
        // DONE WHEN:  Hitting the run button prints a scaled speed.

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
        // DONE WHEN:  six lines of output are in your PR description.

    }

    static String decideDirection(double joystickValue, boolean isEnabled) {
            if (!isEnabled) {
                System.out.println("Disabled");
                return "Disabled";
            } 
            if (Math.abs(joystickValue) > JOYSTICK_DEADZONE) {
                System.out.println(joystickValue > 0 ? "FORWARD" : "BACKWARD");
                return joystickValue > 0 ? "FORWARD" : "BACKWARD";
            } else {
                System.out.println("STOP");
                return "STOP";
            }

    }
    

    static double scaleSpeed(double joystickValue) {
        return JoystickDecider.MAX_SPEED * joystickValue;
    }

    static String driveModeName(int driveMode) {
        switch (driveMode) {
            case 0:
                return "Tank";
            case 1:
                return "Arcade";
            default:
                return "idk yo";
        }
    }
}
