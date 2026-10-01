package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

// ============================================================
// RANK:        Robot Emerald: Arm to Presets
// STEPS HERE:  none — this file is plumbing
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Robot track > Emerald tab)
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
