package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 5: scaleSpeed caps the joystick with MAX_SPEED. */
class ScaleSpeedCheck {

    private static final double TOLERANCE = 1e-9;

    private static double scale(double joystick) {
        return CheckSupport.attempt(5, "scaleSpeed", () -> JoystickDecider.scaleSpeed(joystick));
    }

    @Test
    @DisplayName("STEP 5: full stick gives MAX_SPEED")
    void fullStick() {
        assertEquals(0.8, scale(1.0), TOLERANCE,
                "STEP 5: scaleSpeed(1.0) should be 0.8 — the stick is all the way forward, so you get "
                        + "exactly MAX_SPEED.");
        assertEquals(-0.8, scale(-1.0), TOLERANCE,
                "STEP 5: scaleSpeed(-1.0) should be -0.8.");
    }

    @Test
    @DisplayName("STEP 5: half stick gives half of MAX_SPEED")
    void partialStick() {
        assertEquals(0.4, scale(0.5), TOLERANCE,
                "STEP 5: scaleSpeed(0.5) should be 0.4 — half the stick, half the capped speed.");
        assertEquals(-0.4, scale(-0.5), TOLERANCE,
                "STEP 5: scaleSpeed(-0.5) should be -0.4.");
        assertEquals(0.16, scale(0.2), TOLERANCE,
                "STEP 5: scaleSpeed(0.2) should be 0.16.");
    }

    @Test
    @DisplayName("STEP 5: a centered stick stays at zero")
    void centeredStick() {
        assertEquals(0.0, scale(0.0), TOLERANCE,
                "STEP 5: scaleSpeed(0.0) should be 0.0 — scaling zero by anything is still zero.");
    }
}
