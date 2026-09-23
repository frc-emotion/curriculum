package frc.robot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks that the bindings live where they're supposed to.
 *
 * <p>Comments and quoted text are stripped before anything is scanned, so the STEP comments
 * describing what to write never count as the code itself.
 */
class SourceScanCheck {

    private static final String CONTAINER_PATH = "src/main/java/frc/robot/RobotContainer.java";

    private static String container;

    @BeforeAll
    static void readTheCode() {
        container = CheckSupport.stripCommentsAndStrings(CheckSupport.readSource(CONTAINER_PATH));
    }

    @Test
    @DisplayName("STEP 7: buttons are bound with whileTrue")
    void usesWhileTrue() {
        assertTrue(container.contains("whileTrue"),
                "STEP 7: expected `whileTrue` in RobotContainer. That's the binding that runs a command "
                        + "while a button is held and ends it when you let go.");
    }

    @Test
    @DisplayName("STEP 8: the subsystem has a default command")
    void usesDefaultCommand() {
        assertTrue(container.contains("setDefaultCommand"),
                "STEP 8: expected `setDefaultCommand` in RobotContainer. The default command is what the "
                        + "subsystem does when nothing else has claimed it.");
    }

    @Test
    @DisplayName("STEP 9: a Trigger watches the limit switch")
    void usesTrigger() {
        assertTrue(Pattern.compile("new\\s+Trigger\\s*\\(").matcher(container).find(),
                "STEP 9: expected `new Trigger(...)` in RobotContainer, watching the limit switch so the "
                        + "encoder resets the moment it's pressed.");
    }

    @Test
    @DisplayName("STEP 8: the joystick value arrives as a supplier")
    void defaultCommandUsesALambda() {
        assertTrue(container.contains("->"),
                "STEP 8: expected a lambda (`->`) in RobotContainer. The default command needs a "
                        + "DoubleSupplier so it reads the stick fresh every loop, not once at startup.");
    }
}
