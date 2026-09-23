package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 7: driveModeName turns a mode number into a name. */
class DriveModeNameCheck {

    private static String name(int mode) {
        return CheckSupport.attempt(7, "driveModeName", () -> JoystickDecider.driveModeName(mode));
    }

    @Test
    @DisplayName("STEP 7: the modes we know about")
    void knownModes() {
        assertEquals("TANK", name(0),
                "STEP 7: driveModeName(0) should be \"TANK\".");
        assertEquals("ARCADE", name(1),
                "STEP 7: driveModeName(1) should be \"ARCADE\".");
    }

    @Test
    @DisplayName("STEP 7: anything else is UNKNOWN")
    void unknownModes() {
        assertEquals("UNKNOWN", name(7),
                "STEP 7: driveModeName(7) should be \"UNKNOWN\" — that's what `default` is for.");
        assertEquals("UNKNOWN", name(-1),
                "STEP 7: driveModeName(-1) should be \"UNKNOWN\". Mode numbers can arrive wrong; say so "
                        + "instead of guessing.");
        assertEquals("UNKNOWN", name(2),
                "STEP 7: driveModeName(2) should be \"UNKNOWN\".");
    }
}
