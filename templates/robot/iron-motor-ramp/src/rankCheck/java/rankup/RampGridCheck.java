package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 8: printRampGrid draws one line per motor with nested loops. */
class RampGridCheck {

    private static final double TOLERANCE = 1e-9;

    private static List<String> grid(int motors, int steps) {
        Method method = CheckSupport.findMethod(8, MotorRamp.class, "printRampGrid", int.class, int.class);
        String printed = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(8, method, motors, steps));
        return CheckSupport.nonBlankLines(printed);
    }

    @Test
    @DisplayName("STEP 8: one line per motor")
    void oneLinePerMotor() {
        assertEquals(3, grid(3, 5).size(),
                "STEP 8: printRampGrid(3, 5) should print exactly 3 lines, one per motor.");
        assertEquals(4, grid(4, 3).size(),
                "STEP 8: printRampGrid(4, 3) should print exactly 4 lines. Use the `motors` parameter for "
                        + "the outer loop.");
    }

    @Test
    @DisplayName("STEP 8: each line has one speed per step")
    void oneSpeedPerStep() {
        List<String> lines = grid(3, 5);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            List<Double> numbers = CheckSupport.numbersIn(line);
            // The motor number itself is the first number on the line.
            assertEquals(6, numbers.size(),
                    "STEP 8: line " + (i + 1) + " is \"" + line + "\". With 5 steps it should read like "
                            + "`Motor 1: 0.2 0.4 0.6 0.8 1.0` — the motor number plus 5 speeds.");
        }
    }

    @Test
    @DisplayName("STEP 8: motors are numbered from 1")
    void motorsAreNumberedFromOne() {
        List<String> lines = grid(3, 5);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            assertTrue(line.toLowerCase().startsWith("motor"),
                    "STEP 8: each line should start with `Motor`. Line " + (i + 1) + " is \"" + line + "\".");
            List<Double> numbers = CheckSupport.numbersIn(line);
            assertEquals(i + 1, numbers.get(0), TOLERANCE,
                    "STEP 8: line " + (i + 1) + " should be for motor " + (i + 1)
                            + ", but it says \"" + line + "\". Motors count from 1, not 0.");
        }
    }

    @Test
    @DisplayName("STEP 8: the speeds climb to full power")
    void speedsClimbToOne() {
        List<String> lines = grid(2, 5);
        for (String line : lines) {
            List<Double> numbers = CheckSupport.numbersIn(line);
            List<Double> speeds = numbers.subList(1, numbers.size());
            assertEquals(0.2, speeds.get(0), TOLERANCE,
                    "STEP 8: with 5 steps the first speed should be 0.2 (that is 1 divided by 5). Line: \""
                            + line + "\"");
            assertEquals(1.0, speeds.get(speeds.size() - 1), TOLERANCE,
                    "STEP 8: the last speed on each line should be 1.0 — the ramp finishes at full power. "
                            + "Line: \"" + line + "\"");
        }
    }
}
