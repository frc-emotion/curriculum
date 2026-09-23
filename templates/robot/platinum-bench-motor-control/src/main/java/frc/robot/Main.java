package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

// ============================================================
// RANK:        Robot Platinum: Bench Motor Control
// FILE:        Main.java
// STEPS HERE:  none — this file is plumbing
// GUIDE:       GUIDE_URL  (section "Robot Platinum")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew build
// PASSES WHEN: the sim demo and bench demo both work, no numbers are hard-coded outside
//              Constants, and the motor stops when disabled.
// ============================================================
//
// Every Java program starts at main. On a robot, main's only job is to hand control to
// WPILib, which then calls the methods in Robot.java at the right times.
//
// You never need to change this file. Rebuilt-2026 has the same one.

public final class Main {
    private Main() {
    }

    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }
}
