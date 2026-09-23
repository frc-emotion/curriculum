package frc.robot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ctre.phoenix6.hardware.TalonFX;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.SimpleMotorSubsystem;

/** Checks STEP 1: the motor lives in the subsystem, privately. */
class MotorPrivacyCheck {

    @Test
    @DisplayName("STEP 1: the subsystem owns a TalonFX")
    void subsystemHasTheMotor() {
        Field motor = CheckSupport.fieldOfType(SimpleMotorSubsystem.class, TalonFX.class);
        assertNotNull(motor,
                "STEP 1: I couldn't find a TalonFX field in SimpleMotorSubsystem. Move the motor here "
                        + "from your Platinum RobotContainer — a subsystem owns its hardware.");
    }

    @Test
    @DisplayName("STEP 1: the TalonFX is private")
    void motorIsPrivate() {
        Field motor = CheckSupport.fieldOfType(SimpleMotorSubsystem.class, TalonFX.class);
        assertNotNull(motor, "STEP 1: I couldn't find a TalonFX field in SimpleMotorSubsystem.");
        assertTrue(Modifier.isPrivate(motor.getModifiers()),
                "STEP 1: the TalonFX field `" + motor.getName() + "` is "
                        + CheckSupport.modifiersOf(motor.getModifiers()) + ", but it must be private. "
                        + "If anything outside the subsystem can reach the motor, two pieces of code can "
                        + "command it at once and the scheduler can't referee.");
    }

    @Test
    @DisplayName("STEP 1: nothing outside the subsystem mentions TalonFX")
    void nothingElseTouchesTheMotor() {
        String[] paths = {
            "src/main/java/frc/robot/RobotContainer.java",
            "src/main/java/frc/robot/Robot.java",
        };
        for (String path : paths) {
            String code = CheckSupport.stripCommentsAndStrings(CheckSupport.readSource(path));
            assertTrue(!code.contains("TalonFX"),
                    "STEP 1: " + path.substring(path.lastIndexOf('/') + 1) + " still mentions TalonFX. "
                            + "Only the subsystem should know the motor exists — everything else goes "
                            + "through setSpeed, stop and the command factories.");
        }
    }
}
