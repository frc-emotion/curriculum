package frc.robot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks the things a behaviour test can't see.
 *
 * <p>Comments and quoted text are stripped before anything is scanned, so the STEP comments
 * describing what to write never count as the code itself.
 */
class SourceScanCheck {

    private static String arm;
    private static String tuneCommand;
    private static String constants;
    private static String container;
    private static String everything;

    @BeforeAll
    static void readTheCode() {
        arm = read("src/main/java/frc/robot/subsystems/ArmSubsystem.java");
        tuneCommand = read("src/main/java/frc/robot/commands/PTuneCommand.java");
        constants = read("src/main/java/frc/robot/Constants.java");
        container = read("src/main/java/frc/robot/RobotContainer.java");
        everything = arm + '\n' + tuneCommand + '\n' + container;
    }

    private static String read(String path) {
        return CheckSupport.stripCommentsAndStrings(CheckSupport.readSource(path));
    }

    @Test
    @DisplayName("STEP 1: there is a PIDController somewhere")
    void usesPidController() {
        assertTrue(everything.contains("PIDController"),
                "STEP 1: I couldn't find a PIDController anywhere. PTuneCommand needs one to drive the arm "
                        + "toward its target.");
    }

    @Test
    @DisplayName("STEP 2: kP can be changed from the dashboard")
    void readsGainsFromDashboard() {
        assertTrue(Pattern.compile("SmartDashboard\\s*\\.\\s*getNumber").matcher(everything).find(),
                "STEP 2: I couldn't find `SmartDashboard.getNumber`. Read kP from the dashboard each loop "
                        + "so you can tune it live instead of rebuilding every time.");
    }

    @Test
    @DisplayName("STEP 4: the arm knows when it has arrived")
    void usesToleranceOrAtSetpoint() {
        assertTrue(everything.contains("setTolerance") || everything.contains("atSetpoint"),
                "STEP 4: I couldn't find `setTolerance` or `atSetpoint`. \"Close enough\" has to be defined "
                        + "somewhere, or a command waiting to arrive waits forever.");
    }

    @Test
    @DisplayName("STEP 4: the gear ratio lives in Constants")
    void hasGearRatio() {
        assertTrue(constants.contains("GEAR_RATIO"),
                "STEP 4: I couldn't find GEAR_RATIO in Constants. The value is written at the top of "
                        + "sim/ArmSimHelper.java.");
        assertTrue(arm.contains("GEAR_RATIO"),
                "STEP 4: ArmSubsystem doesn't use GEAR_RATIO. Encoder rotations have to be divided down "
                        + "before they mean anything as an arm angle.");
    }

    @Test
    @DisplayName("STEP 6: the angle limits live in Constants")
    void hasAngleLimits() {
        assertTrue(constants.contains("MIN_ANGLE") && constants.contains("MAX_ANGLE"),
                "STEP 6: I couldn't find MIN_ANGLE and MAX_ANGLE in Constants. Match the hard stops listed "
                        + "at the top of sim/ArmSimHelper.java.");
    }

    @Test
    @DisplayName("STEP 7: the three presets live in Constants")
    void hasPresets() {
        for (String name : new String[] {"STOW_ANGLE", "INTAKE_ANGLE", "SCORE_ANGLE"}) {
            assertTrue(constants.contains(name),
                    "STEP 7: I couldn't find " + name + " in Constants. All three preset angles belong "
                            + "there, not typed into the bindings.");
        }
    }
}
