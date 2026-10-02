package rankup;

//git add all of your file changes 
//git commit with a message
//git push -u origin your branch name on github


public class JoystickDecider {
    static final double JOYSTICK_DEADZONE = 0.1;
    static final double MAX_SPEED = 1.0;

    public static void main(String[] args) {
            double motorSpeed = 1.0;
            int motorPort = 1;
            boolean isEnabled = true;
            String robotName = "Copper";
            double joystickValue = -0.5;

            System.out.println("Motor speed: " + motorSpeed);
            System.out.println("Motor port: " + motorPort);
            System.out.println("Is enabled: " + isEnabled);
            System.out.println("Robot name: " + robotName);
            System.out.println("Scaled speed: " + scaleSpeed(joystickValue));

            decideDirection(joystickValue, isEnabled);
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
