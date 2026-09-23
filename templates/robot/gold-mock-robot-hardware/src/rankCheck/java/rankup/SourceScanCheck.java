package rankup;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks the things a behaviour test can't see.
 *
 * <p>Comments and text inside quotes are removed before anything is scanned, so the STEP
 * comments explaining what to write never count as the code itself.
 */
class SourceScanCheck {

    private static String read(String path) {
        return CheckSupport.stripCommentsAndStrings(CheckSupport.readSource(path));
    }

    @Test
    @DisplayName("STEP 7: Main wires a button with a lambda")
    void mainUsesALambda() {
        String main = read("src/main/java/rankup/Main.java");
        assertTrue(main.contains("->"),
                "STEP 7: expected a lambda (`->`) in Main. Build one Button with something like "
                        + "`() -> intake.run()`.");
    }

    @Test
    @DisplayName("STEP 7: Main wires a button with a method reference")
    void mainUsesAMethodReference() {
        String main = read("src/main/java/rankup/Main.java");
        assertTrue(Pattern.compile("\\w\\s*::\\s*\\w").matcher(main).find(),
                "STEP 7: expected a method reference (`::`) in Main. Build the other Button with something "
                        + "like `shooter::stop`.");
    }

    @Test
    @DisplayName("STEP 2: no port numbers are typed into the subsystems")
    void subsystemsUseConstants() {
        assertNoNumberLiteralsInNewMotor("Intake", "src/main/java/rankup/subsystems/Intake.java");
        assertNoNumberLiteralsInNewMotor("Shooter", "src/main/java/rankup/subsystems/Shooter.java");
    }

    @Test
    @DisplayName("STEP 6: the subsystems read their values from Constants")
    void subsystemsMentionConstants() {
        for (String name : new String[] {"Intake", "Shooter"}) {
            String source = read("src/main/java/rankup/subsystems/" + name + ".java");
            assertTrue(source.contains("Constants"),
                    "STEP 6: " + name + " should get its port and speed from Constants. I don't see "
                            + "Constants mentioned in the file at all.");
        }
    }

    /** Fails if `new Motor(...)` is handed a raw number instead of a constant. */
    private static void assertNoNumberLiteralsInNewMotor(String name, String path) {
        String source = read(path);
        Matcher creation = Pattern.compile("new\\s+Motor\\s*\\(([^)]*)\\)").matcher(source);
        while (creation.find()) {
            String arguments = creation.group(1);
            assertTrue(!Pattern.compile("(?<![\\w.])\\d").matcher(arguments).find(),
                    "STEP 2: " + name + " passes a number straight into `new Motor(" + arguments.trim()
                            + ")`. Port numbers belong in Constants — pass the constant instead, so there "
                            + "is one place to change it when the robot gets rewired.");
        }
    }
}
