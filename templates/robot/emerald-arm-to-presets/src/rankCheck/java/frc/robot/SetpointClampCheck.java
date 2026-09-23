package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.ArmSubsystem;

/**
 * Checks STEP 6: the arm refuses to accept a setpoint past its limits.
 *
 * <p>Runs entirely in simulation. This is the check that stands between a typo and a bent
 * bracket, so it is worth having a machine watch it.
 */
class SetpointClampCheck {

    private static final double TOLERANCE = 1e-6;

    @BeforeAll
    static void startHal() {
        CheckSupport.ensureHal();
    }

    private static double limit(String name) {
        Object value = CheckSupport.readFieldValue(6, CheckSupport.findField(6, Constants.class, name), null);
        return ((Number) value).doubleValue();
    }

    private static Object newArm() {
        Constructor<?> constructor = CheckSupport.findConstructor(1, ArmSubsystem.class);
        return CheckSupport.construct(1, constructor, new Object[0]);
    }

    private static double setpointAfterAsking(Object arm, double degrees) {
        CheckSupport.callOn(6, arm, "setSetpoint", new Class<?>[] {double.class}, degrees);
        Object setpoint = CheckSupport.callOn(6, arm, "getSetpointDegrees", new Class<?>[0]);
        return ((Number) setpoint).doubleValue();
    }

    @Test
    @DisplayName("STEP 6: asking for too high gives you MAX_ANGLE")
    void clampsHigh() {
        double max = limit("MAX_ANGLE");
        Object arm = newArm();
        assertEquals(max, setpointAfterAsking(arm, max + 90.0), TOLERANCE,
                "STEP 6: after setSetpoint(" + (max + 90.0) + "), getSetpointDegrees() should be MAX_ANGLE ("
                        + max + "). Clamp inside setSetpoint so nothing can get past it.");
    }

    @Test
    @DisplayName("STEP 6: asking for too low gives you MIN_ANGLE")
    void clampsLow() {
        double min = limit("MIN_ANGLE");
        Object arm = newArm();
        assertEquals(min, setpointAfterAsking(arm, min - 90.0), TOLERANCE,
                "STEP 6: after setSetpoint(" + (min - 90.0) + "), getSetpointDegrees() should be MIN_ANGLE ("
                        + min + ").");
    }

    @Test
    @DisplayName("STEP 6: a sensible angle is left alone")
    void leavesValidAnglesAlone() {
        double min = limit("MIN_ANGLE");
        double max = limit("MAX_ANGLE");
        double middle = (min + max) / 2.0;
        Object arm = newArm();
        assertEquals(middle, setpointAfterAsking(arm, middle), TOLERANCE,
                "STEP 6: " + middle + " degrees is within the limits, so setSetpoint should keep it exactly "
                        + "as asked. Clamping is for the values that are out of range.");
    }
}
