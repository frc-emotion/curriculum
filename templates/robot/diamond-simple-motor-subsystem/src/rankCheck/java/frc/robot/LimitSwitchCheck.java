package frc.robot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.SimpleMotorSubsystem;

/**
 * Checks STEP 3: isAtLimit() really reads the switch.
 *
 * <p>This runs entirely in simulation — no roboRIO, no wiring. It toggles the simulated DIO
 * channel your limit switch is on and checks that isAtLimit() notices.
 *
 * <p>It deliberately does NOT check which way round the switch reads. Whether "pressed" means
 * true or false depends on how the real switch is wired, and only someone holding it can tell
 * you. Your reviewer checks that part on the bench.
 */
class LimitSwitchCheck {

    @BeforeAll
    static void startHal() {
        CheckSupport.ensureHal();
    }

    @Test
    @DisplayName("STEP 3: toggling the limit switch changes isAtLimit()")
    void isAtLimitFollowsTheSwitch() {
        Constructor<?> constructor = CheckSupport.findConstructor(1, SimpleMotorSubsystem.class);
        Object subsystem = CheckSupport.construct(1, constructor, new Object[0]);

        Field switchField = CheckSupport.fieldOfType(SimpleMotorSubsystem.class, DigitalInput.class);
        assertNotNull(switchField,
                "STEP 3: I couldn't find a DigitalInput field in SimpleMotorSubsystem, so there is no "
                        + "limit switch to test yet.");

        DigitalInput limitSwitch = (DigitalInput) CheckSupport.readFieldValue(3, switchField, subsystem);
        assertNotNull(limitSwitch,
                "STEP 3: the DigitalInput field is null. Build it in the constructor, using the DIO "
                        + "channel from Constants.");

        DIOSim simulatedSwitch = new DIOSim(limitSwitch.getChannel());

        simulatedSwitch.setValue(false);
        Object whenLow = CheckSupport.callOn(3, subsystem, "isAtLimit", new Class<?>[0]);

        simulatedSwitch.setValue(true);
        Object whenHigh = CheckSupport.callOn(3, subsystem, "isAtLimit", new Class<?>[0]);

        assertNotEquals(whenLow, whenHigh,
                "STEP 3: isAtLimit() gave the same answer (" + whenLow + ") whether the switch was on or "
                        + "off, so it isn't reading the DigitalInput. A limit switch that always says the "
                        + "same thing is worse than none — it looks like it's working.");
    }
}
