// ============================================================
// RANK:        Robot Diamond: Simple Motor Subsystem
// FILE:        commands/package-info.java
// STEPS HERE:  none — this file explains why this folder is empty
// GUIDE:       GUIDE_URL  (section "Robot Diamond")
// RUN:         ./gradlew simulateJava    CHECK: ./gradlew rankCheck
// PASSES WHEN: nothing outside the subsystem touches the TalonFX directly, the limit switch
//              reliably blocks forward motion, and all bindings live in RobotContainer.
// ============================================================
//
// This folder is empty on purpose.
//
// A command is "a thing the robot does": run the intake, drive to a spot, raise the arm. In
// Rebuilt-2026 the big ones live here as their own classes, one file each.
//
// At this rank you build commands a different way: as FACTORY METHODS on the subsystem
// itself (step 6). A factory method is just a method that returns a Command. It is shorter,
// it keeps the subsystem's business next to the subsystem, and WPILib gives you helpers on
// SubsystemBase to make them — which is why `runForward()` can be a few lines instead of a
// whole file.
//
// You write a command class the long way at Emerald, when you build PTuneCommand. Both
// styles are normal; you'll see both in our robot code.

package frc.robot.commands;
