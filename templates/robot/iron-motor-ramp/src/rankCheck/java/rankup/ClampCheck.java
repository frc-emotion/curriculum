package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 2: clamp. */
class ClampCheck {

    private static final double TOLERANCE = 1e-9;

    private static double clamp(double value, double min, double max) {
        Method method = CheckSupport.findMethod(2, MotorRamp.class, "clamp",
                double.class, double.class, double.class);
        return CheckSupport.callDouble(2, method, value, min, max);
    }

    @Test
    @DisplayName("STEP 2: too big comes back as the maximum")
    void clampsHigh() {
        assertEquals(1.0, clamp(1.5, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(1.5, -1.0, 1.0) should be 1.0. A motor cannot go past full power.");
        assertEquals(1.0, clamp(100.0, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(100.0, -1.0, 1.0) should be 1.0.");
    }

    @Test
    @DisplayName("STEP 2: too small comes back as the minimum")
    void clampsLow() {
        assertEquals(-1.0, clamp(-2.0, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(-2.0, -1.0, 1.0) should be -1.0.");
        assertEquals(-1.0, clamp(-50.0, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(-50.0, -1.0, 1.0) should be -1.0.");
    }

    @Test
    @DisplayName("STEP 2: a value already in range is left alone")
    void leavesInRangeAlone() {
        assertEquals(0.3, clamp(0.3, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(0.3, -1.0, 1.0) should be 0.3 — nothing to fix, so change nothing.");
        assertEquals(-0.7, clamp(-0.7, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(-0.7, -1.0, 1.0) should be -0.7.");
        assertEquals(0.0, clamp(0.0, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(0.0, -1.0, 1.0) should be 0.0.");
    }

    @Test
    @DisplayName("STEP 2: the limits themselves come back unchanged")
    void edgesAreUnchanged() {
        assertEquals(1.0, clamp(1.0, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(1.0, -1.0, 1.0) should be 1.0 — the limit is allowed, it is the maximum.");
        assertEquals(-1.0, clamp(-1.0, -1.0, 1.0), TOLERANCE,
                "STEP 2: clamp(-1.0, -1.0, 1.0) should be -1.0.");
    }

    @Test
    @DisplayName("STEP 2: the range is the caller's choice")
    void worksWithOtherRanges() {
        assertEquals(0.5, clamp(0.9, 0.0, 0.5), TOLERANCE,
                "STEP 2: clamp(0.9, 0.0, 0.5) should be 0.5. Use the min and max parameters, not -1 and 1.");
        assertEquals(10.0, clamp(4.0, 10.0, 20.0), TOLERANCE,
                "STEP 2: clamp(4.0, 10.0, 20.0) should be 10.0.");
    }
}
