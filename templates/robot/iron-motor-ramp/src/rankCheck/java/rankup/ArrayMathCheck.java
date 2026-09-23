package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 7: average and max over an array of module speeds. */
class ArrayMathCheck {

    private static final double TOLERANCE = 1e-9;

    private static double average(double[] speeds) {
        Method method = CheckSupport.findMethod(7, MotorRamp.class, "average", double[].class);
        return CheckSupport.callDouble(7, method, (Object) speeds);
    }

    private static double max(double[] speeds) {
        Method method = CheckSupport.findMethod(7, MotorRamp.class, "max", double[].class);
        return CheckSupport.callDouble(7, method, (Object) speeds);
    }

    @Test
    @DisplayName("STEP 7: average of four module speeds")
    void averagesFourModules() {
        assertEquals(0.5, average(new double[] {0.2, 0.4, 0.6, 0.8}), TOLERANCE,
                "STEP 7: average({0.2, 0.4, 0.6, 0.8}) should be 0.5.");
        assertEquals(0.75, average(new double[] {0.75, 0.75, 0.75, 0.75}), TOLERANCE,
                "STEP 7: average of four identical speeds should be that speed.");
    }

    @Test
    @DisplayName("STEP 7: average handles negatives and a single element")
    void averageEdgeCases() {
        assertEquals(0.0, average(new double[] {-0.5, 0.5}), TOLERANCE,
                "STEP 7: average({-0.5, 0.5}) should be 0.0. One module driving backwards is normal on a "
                        + "swerve drive.");
        assertEquals(0.3, average(new double[] {0.3}), TOLERANCE,
                "STEP 7: average of a one-element array is that element. Divide by the array's length, not "
                        + "by 4.");
    }

    @Test
    @DisplayName("STEP 7: max finds the fastest module")
    void findsMax() {
        assertEquals(0.8, max(new double[] {0.2, 0.8, 0.6, 0.4}), TOLERANCE,
                "STEP 7: max({0.2, 0.8, 0.6, 0.4}) should be 0.8.");
        assertEquals(0.9, max(new double[] {0.9, 0.1, 0.1, 0.1}), TOLERANCE,
                "STEP 7: max should find the biggest value wherever it sits, including first.");
        assertEquals(0.9, max(new double[] {0.1, 0.1, 0.1, 0.9}), TOLERANCE,
                "STEP 7: max should find the biggest value wherever it sits, including last.");
    }

    @Test
    @DisplayName("STEP 7: max works when every speed is negative")
    void maxWithAllNegatives() {
        assertEquals(-0.2, max(new double[] {-0.8, -0.2, -0.5}), TOLERANCE,
                "STEP 7: max({-0.8, -0.2, -0.5}) should be -0.2. Starting your search at 0 breaks this — "
                        + "start from the array's first element instead.");
    }

    @Test
    @DisplayName("STEP 7: a single-element array")
    void singleElement() {
        assertEquals(0.4, max(new double[] {0.4}), TOLERANCE,
                "STEP 7: max of a one-element array is that element.");
    }
}
