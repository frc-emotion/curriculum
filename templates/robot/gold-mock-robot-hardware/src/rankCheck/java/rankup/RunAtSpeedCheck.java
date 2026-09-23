package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.function.DoubleSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rankup.subsystems.Intake;
import rankup.util.Motor;

/** Checks STEP 8: runAtSpeed takes a supplier and uses what it supplies. */
class RunAtSpeedCheck {

    private static final double TOLERANCE = 1e-9;

    private static Object newIntake() {
        Constructor<?> constructor = CheckSupport.findConstructor(8, Intake.class);
        return CheckSupport.construct(8, constructor, new Object[0]);
    }

    private static double motorSpeedOf(Object intake) {
        Field motorField = CheckSupport.fieldOfType(intake.getClass(), Motor.class);
        assertNotNull(motorField, "STEP 8: Intake should have a Motor field.");
        Object motor = CheckSupport.readFieldValue(8, motorField, intake);
        assertNotNull(motor, "STEP 8: Intake's Motor field is null.");
        return ((Number) CheckSupport.callOn(8, motor, "getSpeed", new Class<?>[0])).doubleValue();
    }

    @Test
    @DisplayName("STEP 8: runAtSpeed sets the motor to what the supplier returns")
    void usesTheSuppliedValue() {
        Object intake = newIntake();
        DoubleSupplier joystick = () -> 0.42;

        CheckSupport.capturePrintedOutput(
                () -> CheckSupport.callOn(8, intake, "runAtSpeed", new Class<?>[] {DoubleSupplier.class},
                        joystick));

        assertEquals(0.42, motorSpeedOf(intake), TOLERANCE,
                "STEP 8: after runAtSpeed(() -> 0.42), the intake's motor should be at 0.42. Ask the "
                        + "supplier for its value with getAsDouble().");
    }

    @Test
    @DisplayName("STEP 8: the supplier is asked each time, not once")
    void asksEachTime() {
        Object intake = newIntake();
        double[] stick = {0.3};
        DoubleSupplier joystick = () -> stick[0];

        CheckSupport.capturePrintedOutput(
                () -> CheckSupport.callOn(8, intake, "runAtSpeed", new Class<?>[] {DoubleSupplier.class},
                        joystick));
        assertEquals(0.3, motorSpeedOf(intake), TOLERANCE,
                "STEP 8: the first call should use 0.3.");

        stick[0] = -0.6;
        CheckSupport.capturePrintedOutput(
                () -> CheckSupport.callOn(8, intake, "runAtSpeed", new Class<?>[] {DoubleSupplier.class},
                        joystick));
        assertEquals(-0.6, motorSpeedOf(intake), TOLERANCE,
                "STEP 8: the driver moved the stick, so the second call should use -0.6. That is the point "
                        + "of a supplier — you ask it again and get the current value.");
    }
}
