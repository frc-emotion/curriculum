package rankup;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
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

    private static final String SOURCE_PATH = "src/main/java/rankup/MotorRamp.java";

    private static String code;

    @BeforeAll
    static void readTheCode() {
        code = CheckSupport.stripCommentsAndStrings(CheckSupport.readSource(SOURCE_PATH));
    }

    @Test
    @DisplayName("STEP 5: rampUp uses break to stop at the target")
    void usesBreak() {
        String body = CheckSupport.methodBodyOf(code, "rampUp");
        assertTrue(Pattern.compile("\\bbreak\\b").matcher(body).find(),
                "STEP 5: expected `break` inside rampUp. When the next full step would overshoot, command "
                        + "the target itself and break out of the loop.");
    }

    @Test
    @DisplayName("STEP 6: the speed log is an ArrayList<Double>")
    void usesArrayListOfDouble() {
        assertTrue(Pattern.compile("ArrayList\\s*<\\s*Double\\s*>").matcher(code).find(),
                "STEP 6: expected an `ArrayList<Double>` in MotorRamp. The <Double> part is what tells Java "
                        + "what the list holds.");
        assertTrue(Pattern.compile("static\\s+(?:.*\\s+)?ArrayList\\s*<\\s*Double\\s*>\\s+speedLog")
                        .matcher(code).find(),
                "STEP 6: expected a class-level `static ArrayList<Double> speedLog` in MotorRamp.");
    }

    @Test
    @DisplayName("STEP 8: printRampGrid has a loop inside a loop")
    void usesNestedLoops() {
        String body = CheckSupport.methodBodyOf(code, "printRampGrid");
        assertTrue(hasNestedLoop(body),
                "STEP 8: expected a loop inside another loop in printRampGrid. The outer loop walks the "
                        + "motors; the inner one walks that motor's steps.");
    }

    /** True when a loop keyword appears inside the braces of another loop. */
    private static boolean hasNestedLoop(String body) {
        Matcher outer = Pattern.compile("\\b(for|while)\\s*\\(").matcher(body);
        while (outer.find()) {
            int open = body.indexOf('{', outer.end());
            if (open < 0) {
                continue;
            }
            int depth = 0;
            for (int i = open; i < body.length(); i++) {
                char c = body.charAt(i);
                if (c == '{') {
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0) {
                        String inside = body.substring(open + 1, i);
                        if (Pattern.compile("\\b(for|while)\\s*\\(").matcher(inside).find()) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }
}
