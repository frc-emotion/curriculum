
package rankup;

import java.util.ArrayList;

public class MotorRamp {

    static ArrayList<Double> speedLog = new ArrayList<>();

    public static void main(String[] args) {

        rampUp(1.0, 0.25);
        rampUp(0.9, 0.2);

        printRampGrid(3, 5);

        double[] speeds = {0.5, 0.7, 0.9, 0.6};

        System.out.println("Average speed: " + average(speeds));
        System.out.println("Maximum speed: " + max(speeds));

        System.out.println("Speed log size: " + speedLog.size());
    }

    // STEP 7: Average swerve module speeds
    static double average(double[] speeds) {

        double sum = 0.0;

        for (double speed : speeds) {
            sum += speed;
        }

        return sum / speeds.length;
    }

    // STEP 7: Maximum swerve module speed
    static double max(double[] speeds) {

        double maximum = speeds[0];

        for (double speed : speeds) {
            if (speed > maximum) {
                maximum = speed;
            }
        }

        return maximum;
    }

    // STEP 8: Print ramp grid
    static void printRampGrid(int motors, int steps) {

        for (int motor = 1; motor <= motors; motor++) {

            System.out.print("Motor " + motor + ": ");

            for (int step = 1; step <= steps; step++) {

                double speed = (double) step / steps;

                System.out.print(speed + " ");
            }

            System.out.println();
        }
    }

    // STEP 1: Deadband
    static boolean isWithinDeadband(double value, double deadband) {

        if (Math.abs(value) <= deadband) {
            return true;
        } else {
            return false;
        }
    }

    // STEP 2: Clamp
    static double clamp(double value, double min, double max) {

        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    // STEP 3: Set motor speed
    static void setMotorSpeed(double speed) {

        double clampedSpeed = clamp(speed, -1.0, 1.0);

        System.out.println("Motor set to " + clampedSpeed);

        speedLog.add(clampedSpeed);
    }

    // STEP 4: Overload setMotorSpeed
    static void setMotorSpeed(double speed, int port) {

        double clampedSpeed = clamp(speed, -1.0, 1.0);

        System.out.println("Motor " + port + " set to " + clampedSpeed);

        speedLog.add(clampedSpeed);
    }

    // STEP 5: Ramp up
    static ArrayList<Double> rampUp(double target, double step) {

        ArrayList<Double> speeds = new ArrayList<>();

        double currentSpeed = 0.0;

        while (currentSpeed < target) {

            currentSpeed += step;

            if (currentSpeed > target) {
                currentSpeed = target;
            }

            setMotorSpeed(currentSpeed);

            speeds.add(currentSpeed);

            if (currentSpeed == target) {
                break;
            }
        }

        return speeds;
    }
}
