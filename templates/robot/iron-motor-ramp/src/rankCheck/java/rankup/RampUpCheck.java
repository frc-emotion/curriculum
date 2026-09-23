package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 5: rampUp walks the speed up and lands exactly on the target. */
class RampUpCheck {

    private static final double TOLERANCE = 1e-9;

    private static List<Double> ramp(double target, double step) {
        Method method = CheckSupport.findMethod(5, MotorRamp.class, "rampUp", double.class, double.class);
        Object[] result = new Object[1];
        CheckSupport.capturePrintedOutput(() -> result[0] = CheckSupport.call(5, method, target, step));

        Object value = result[0];
        if (!(value instanceof List)) {
            return org.junit.jupiter.api.Assertions.fail(
                    "STEP 5: rampUp should return an ArrayList<Double> of the speeds it set, but it returned "
                            + (value == null ? "null" : value.getClass().getSimpleName()) + ".");
        }
        List<Double> speeds = new ArrayList<>();
        for (Object item : (List<?>) value) {
            if (!(item instanceof Number)) {
                return org.junit.jupiter.api.Assertions.fail(
                        "STEP 5: rampUp's list should hold numbers, but it has " + item + " in it.");
            }
            speeds.add(((Number) item).doubleValue());
        }
        return speeds;
    }

    private static void assertRamp(double target, double step, double[] expected) {
        List<Double> actual = ramp(target, step);
        String got = actual.toString();

        assertEquals(expected.length, actual.size(),
                "STEP 5: rampUp(" + target + ", " + step + ") should return " + expected.length
                        + " speeds, but it returned " + actual.size() + ": " + got);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual.get(i), TOLERANCE,
                    "STEP 5: rampUp(" + target + ", " + step + ") value #" + (i + 1) + " should be "
                            + expected[i] + ". Got " + got);
        }
    }

    @Test
    @DisplayName("STEP 5: a ramp that divides evenly")
    void evenRamp() {
        assertRamp(1.0, 0.25, new double[] {0.25, 0.5, 0.75, 1.0});
    }

    @Test
    @DisplayName("STEP 5: a ramp whose last step has to be shortened")
    void unevenRampLandsOnTarget() {
        assertRamp(0.9, 0.2, new double[] {0.2, 0.4, 0.6, 0.8, 0.9});
    }

    @Test
    @DisplayName("STEP 5: the first speed is one step, never zero")
    void startsAtOneStep() {
        List<Double> speeds = ramp(1.0, 0.25);
        assertFalse(speeds.isEmpty(), "STEP 5: rampUp(1.0, 0.25) came back empty.");
        assertEquals(0.25, speeds.get(0), TOLERANCE,
                "STEP 5: the first speed should be 0.25 — one step up from a standstill. Starting at 0.0 "
                        + "means the list records a command that never changed anything.");
    }

    @Test
    @DisplayName("STEP 5: the last speed is exactly the target")
    void endsExactlyOnTarget() {
        List<Double> speeds = ramp(0.9, 0.2);
        assertFalse(speeds.isEmpty(), "STEP 5: rampUp(0.9, 0.2) came back empty.");
        assertEquals(0.9, speeds.get(speeds.size() - 1), TOLERANCE,
                "STEP 5: the last speed should be exactly 0.9. 0.8 + 0.2 would overshoot, so the final step "
                        + "gets shortened to land on the target.");
        for (double speed : speeds) {
            assertTrue(speed <= 0.9 + TOLERANCE,
                    "STEP 5: rampUp(0.9, 0.2) commanded " + speed + ", which is past the target of 0.9. "
                            + "A ramp must never overshoot.");
        }
    }

    @Test
    @DisplayName("STEP 5: rampUp calls setMotorSpeed on the way up")
    void callsSetMotorSpeed() {
        Method method = CheckSupport.findMethod(5, MotorRamp.class, "rampUp", double.class, double.class);
        String printed = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(5, method, 1.0, 0.25));
        List<String> lines = CheckSupport.nonBlankLines(printed);
        assertTrue(lines.size() >= 4,
                "STEP 5: rampUp(1.0, 0.25) should call setMotorSpeed for each of its 4 speeds, so it should "
                        + "print 4 lines. It printed " + lines.size() + ".");
    }
}
