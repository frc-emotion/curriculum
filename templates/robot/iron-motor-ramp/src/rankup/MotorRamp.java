package rankup;
import java.util.ArrayList;

public class MotorRamp {
    static ArrayList<Double> log = new ArrayList<Double>();
    public void setlog(ArrayList<Double> log) {
        this.log = log;
    }
    static double max(double[] values) {
        double maxValue = Double.NEGATIVE_INFINITY;
        for (double value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
    static ArrayList<Double> 
    log1 = new ArrayList<Double>();
    static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    static void speedMoter(double speed) {
        double clampsp = clamp(speed, -1.0, 1.0);
        System.out.println("Motor set to " + clampsp);
        try {
            log.add(clampsp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void speedMoter(double speed, int port) {
        double clampsp = clamp(speed, -1.0, 1.0);
        System.out.println("Motor " + port + " set to " + clampsp);
        log.add(clampsp);
    }
    static ArrayList<Double> rampUp(double target, double step) {
        ArrayList<Double> speeds = new ArrayList<Double>();
        double currentSpeed = 0.0;
        while (currentSpeed < target) {
            currentSpeed += step;
            if (currentSpeed > target) {
                currentSpeed = target;
            }
            speedMoter(currentSpeed);
            speeds.add(currentSpeed);
            if (currentSpeed == target) {
                break;
            }
        }
        return speeds;
    }
    static void printRampGrid(int motors, int steps) {
        for (int i = 1; i <= motors; i++) {
            System.out.print("Motor " + i + ": ");
            for (int j = 1; j <= steps; j++) {
                double speed = (double) j / steps;
                System.out.print(speed + " ");
            }
            System.out.println();
        }
    }
    static double average(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
    static boolean isWithinDeadband(double value, double deadband) {
        return Math.abs(value) <= deadband;
    }
    static void main(String[] args) {
        double[] swerveSpeeds = {0.5, 0.7, 0.9, 1.0};
        System.out.println("Average speed: " + average(swerveSpeeds));
        System.out.println("Max speed: " + max(swerveSpeeds));
        printRampGrid(3, 5);
        rampUp(1.0, 0.25);
        rampUp(0.9, 0.2);
        System.out.println("Speed log size: " + log.size());
    }
}