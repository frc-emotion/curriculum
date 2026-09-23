package rankup;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks the things a behaviour test can't see: which language features you used.
 *
 * <p>Comments and text inside quotes are removed before anything is scanned, so the STEP
 * comments explaining what to write never count as the code itself.
 */
class SourceScanCheck {

    private static final String SOURCE_PATH = "src/main/java/rankup/JoystickDecider.java";

    private static String code;

    @BeforeAll
    static void readTheCode() {
        code = CheckSupport.stripCommentsAndStrings(CheckSupport.readSource(SOURCE_PATH));
    }

    @Test
    @DisplayName("STEP 2: DEADBAND is a final constant")
    void deadbandIsFinal() {
        assertTrue(Pattern.compile("static\\s+final\\s+double\\s+DEADBAND\\b").matcher(code).find(),
                "STEP 2: expected a class-level `static final double DEADBAND` in JoystickDecider. "
                        + "Check the spelling and that it is declared outside every method.");
    }

    @Test
    @DisplayName("STEP 5: MAX_SPEED is a final constant")
    void maxSpeedIsFinal() {
        assertTrue(Pattern.compile("static\\s+final\\s+double\\s+MAX_SPEED\\b").matcher(code).find(),
                "STEP 5: expected a class-level `static final double MAX_SPEED` in JoystickDecider.");
    }

    @Test
    @DisplayName("STEP 3: decideDirection uses Math.abs")
    void usesMathAbs() {
        String body = CheckSupport.methodBodyOf(code, "decideDirection");
        assertTrue(body.contains("Math.abs"),
                "STEP 3: expected Math.abs in decideDirection. Use it to ask how far the stick is from "
                        + "centre, so one check covers both directions.");
    }

    @Test
    @DisplayName("STEP 6: decideDirection uses a ternary operator")
    void usesTernary() {
        String body = CheckSupport.methodBodyOf(code, "decideDirection");
        assertTrue(Pattern.compile("\\?[^;{}]*:").matcher(body).find(),
                "STEP 6: expected a ternary operator (? :) in decideDirection. Rewrite the stop-or-move "
                        + "decision as a single line that picks between two values.");
    }

    @Test
    @DisplayName("STEP 7: driveModeName uses a switch")
    void usesSwitch() {
        String body = CheckSupport.methodBodyOf(code, "driveModeName");
        assertTrue(Pattern.compile("\\bswitch\\s*\\(").matcher(body).find(),
                "STEP 7: expected a `switch` in driveModeName. A chain of if/else works, but this step is "
                        + "about learning switch.");
    }

    @Test
    @DisplayName("STEP 2 and 5: the constants are actually used")
    void constantsAreUsed() {
        String decide = CheckSupport.methodBodyOf(code, "decideDirection");
        String scale = CheckSupport.methodBodyOf(code, "scaleSpeed");
        assertTrue(decide.contains("DEADBAND"),
                "STEP 2: decideDirection should use DEADBAND instead of typing 0.1 into the comparison. "
                        + "That's the whole point of naming it.");
        assertTrue(scale.contains("MAX_SPEED"),
                "STEP 5: scaleSpeed should use MAX_SPEED instead of typing 0.8.");
    }
}
