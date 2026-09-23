package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 3, STEP 4 and STEP 6: what decideDirection answers. */
class DecideDirectionCheck {

    private static String decide(int step, double joystick, boolean enabled) {
        return CheckSupport.attempt(step, "decideDirection", () -> JoystickDecider.decideDirection(joystick, enabled));
    }

    @Test
    @DisplayName("STEP 3: pushing the stick forward means FORWARD")
    void forward() {
        assertEquals("FORWARD", decide(3, 0.5, true),
                "STEP 3: decideDirection(0.5, true) should be \"FORWARD\" — 0.5 is well above the deadband.");
        assertEquals("FORWARD", decide(3, 1.0, true),
                "STEP 3: decideDirection(1.0, true) should be \"FORWARD\" — the stick is pushed all the way.");
    }

    @Test
    @DisplayName("STEP 3: pulling the stick back means BACKWARD")
    void backward() {
        assertEquals("BACKWARD", decide(3, -0.8, true),
                "STEP 3: decideDirection(-0.8, true) should be \"BACKWARD\" — negative means backward.");
        assertEquals("BACKWARD", decide(3, -1.0, true),
                "STEP 3: decideDirection(-1.0, true) should be \"BACKWARD\".");
    }

    @Test
    @DisplayName("STEP 3: a nudge inside the deadband means STOP")
    void insideDeadbandStops() {
        assertEquals("STOP", decide(3, 0.05, true),
                "STEP 3: decideDirection(0.05, true) should be \"STOP\" — 0.05 is inside the 0.1 deadband.");
        assertEquals("STOP", decide(3, -0.05, true),
                "STEP 3: decideDirection(-0.05, true) should be \"STOP\" — inside the deadband, either direction.");
        assertEquals("STOP", decide(3, 0.0, true),
                "STEP 3: decideDirection(0.0, true) should be \"STOP\" — nobody is touching the stick.");
    }

    @Test
    @DisplayName("STEP 3: exactly 0.1 and exactly -0.1 are STOP")
    void deadbandEdgeIsStop() {
        assertEquals("STOP", decide(3, 0.1, true),
                "STEP 3: decideDirection(0.1, true) should be \"STOP\". Exactly on the deadband counts as "
                        + "stopped, so the comparison needs to include the edge itself.");
        assertEquals("STOP", decide(3, -0.1, true),
                "STEP 3: decideDirection(-0.1, true) should be \"STOP\". Same edge, other direction.");
    }

    @Test
    @DisplayName("STEP 3: just past the deadband moves")
    void justOutsideDeadbandMoves() {
        assertEquals("FORWARD", decide(3, 0.11, true),
                "STEP 3: decideDirection(0.11, true) should be \"FORWARD\" — that is past the deadband.");
        assertEquals("BACKWARD", decide(3, -0.11, true),
                "STEP 3: decideDirection(-0.11, true) should be \"BACKWARD\".");
    }

    @Test
    @DisplayName("STEP 4: a disabled robot never moves")
    void disabledAlwaysWins() {
        assertEquals("DISABLED", decide(4, 0.5, false),
                "STEP 4: decideDirection(0.5, false) should be \"DISABLED\". A disabled robot does not move, "
                        + "no matter what the driver is doing.");
        assertEquals("DISABLED", decide(4, -0.8, false),
                "STEP 4: decideDirection(-0.8, false) should be \"DISABLED\".");
        assertEquals("DISABLED", decide(4, 0.0, false),
                "STEP 4: decideDirection(0.0, false) should be \"DISABLED\" — disabled beats stopped.");
        assertEquals("DISABLED", decide(4, 1.0, false),
                "STEP 4: decideDirection(1.0, false) should be \"DISABLED\".");
    }
}
