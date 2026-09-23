package rankup;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 1: isWithinDeadband. */
class DeadbandCheck {

    private static boolean within(double value, double deadband) {
        Method method = CheckSupport.findMethod(1, MotorRamp.class, "isWithinDeadband", double.class, double.class);
        return CheckSupport.callBoolean(1, method, value, deadband);
    }

    @Test
    @DisplayName("STEP 1: small wobbles count as inside the deadband")
    void insideIsTrue() {
        assertTrue(within(0.05, 0.1),
                "STEP 1: isWithinDeadband(0.05, 0.1) should be true — 0.05 is a wobble, not a push.");
        assertTrue(within(-0.05, 0.1),
                "STEP 1: isWithinDeadband(-0.05, 0.1) should be true. Distance from zero is what matters, "
                        + "not which side of zero.");
        assertTrue(within(0.0, 0.1),
                "STEP 1: isWithinDeadband(0.0, 0.1) should be true.");
    }

    @Test
    @DisplayName("STEP 1: a real push is outside the deadband")
    void outsideIsFalse() {
        assertFalse(within(0.5, 0.1),
                "STEP 1: isWithinDeadband(0.5, 0.1) should be false — that is a deliberate push.");
        assertFalse(within(-0.5, 0.1),
                "STEP 1: isWithinDeadband(-0.5, 0.1) should be false.");
        assertFalse(within(0.11, 0.1),
                "STEP 1: isWithinDeadband(0.11, 0.1) should be false — just past the edge is outside.");
    }

    @Test
    @DisplayName("STEP 1: exactly on the edge counts as inside")
    void edgeIsInside() {
        assertTrue(within(0.1, 0.1),
                "STEP 1: isWithinDeadband(0.1, 0.1) should be true. Exactly on the edge counts as inside — "
                        + "that means the comparison includes the edge itself.");
        assertTrue(within(-0.1, 0.1),
                "STEP 1: isWithinDeadband(-0.1, 0.1) should be true. Same edge, other side.");
    }

    @Test
    @DisplayName("STEP 1: the deadband size is the caller's choice")
    void deadbandIsAParameter() {
        assertTrue(within(0.15, 0.2),
                "STEP 1: isWithinDeadband(0.15, 0.2) should be true. Use the `deadband` parameter, not a "
                        + "hard-coded 0.1.");
        assertFalse(within(0.15, 0.05),
                "STEP 1: isWithinDeadband(0.15, 0.05) should be false — with a small deadband, 0.15 is a push.");
    }
}
