package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rankup.util.Motor;

/** Checks STEP 1: the Motor class. */
class MotorCheck {

    private static final double TOLERANCE = 1e-9;
    private static final Class<?>[] DOUBLE_ARG = {double.class};

    private static Object motor(String name, int port) {
        Constructor<?> constructor = CheckSupport.findConstructor(1, Motor.class, String.class, int.class);
        return CheckSupport.construct(1, constructor, name, port);
    }

    private static double speedOf(Object motor) {
        Object value = CheckSupport.callOn(1, motor, "getSpeed", new Class<?>[0]);
        assertTrue(value instanceof Number, "STEP 1: getSpeed() should return a double.");
        return ((Number) value).doubleValue();
    }

    private static void setSpeed(Object motor, double speed) {
        CheckSupport.callOn(1, motor, "setSpeed", DOUBLE_ARG, speed);
    }

    @Test
    @DisplayName("STEP 1: every field in Motor is private")
    void fieldsArePrivate() {
        Field[] fields = Motor.class.getDeclaredFields();
        assertTrue(fields.length >= 3,
                "STEP 1: Motor should have three fields — a name, a port and a speed. I found "
                        + fields.length + ".");
        for (Field field : fields) {
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    "STEP 1: the field `" + field.getName() + "` in Motor is "
                            + CheckSupport.modifiersOf(field.getModifiers()) + ", but it should be private. "
                            + "If anything outside Motor can write to it, setSpeed's clamp can be skipped.");
        }
    }

    @Test
    @DisplayName("STEP 1: a new motor remembers its name and port, and starts stopped")
    void constructorStoresNameAndPort() {
        Object intake = motor("Intake", 1);
        assertEquals("Intake", CheckSupport.callOn(1, intake, "getName", new Class<?>[0]),
                "STEP 1: getName() should give back the name the motor was built with.");
        assertEquals(1, CheckSupport.callOn(1, intake, "getPort", new Class<?>[0]),
                "STEP 1: getPort() should give back the port the motor was built with.");
        assertEquals(0.0, speedOf(intake), TOLERANCE,
                "STEP 1: a brand new Motor should be at speed 0.0. A motor that starts moving by itself is "
                        + "a bad surprise.");
    }

    @Test
    @DisplayName("STEP 1: setSpeed stores a normal speed")
    void setSpeedStores() {
        Object shooter = motor("Shooter", 2);
        setSpeed(shooter, 0.5);
        assertEquals(0.5, speedOf(shooter), TOLERANCE,
                "STEP 1: after setSpeed(0.5), getSpeed() should be 0.5.");
        setSpeed(shooter, -0.25);
        assertEquals(-0.25, speedOf(shooter), TOLERANCE,
                "STEP 1: after setSpeed(-0.25), getSpeed() should be -0.25. Negative is a direction, not an "
                        + "error.");
    }

    @Test
    @DisplayName("STEP 1: setSpeed clamps to -1.0 .. 1.0")
    void setSpeedClamps() {
        Object motor = motor("Test", 9);
        setSpeed(motor, 1.5);
        assertEquals(1.0, speedOf(motor), TOLERANCE,
                "STEP 1: setSpeed(1.5) should leave the motor at 1.0. Full power is the most a motor has.");
        setSpeed(motor, -2.0);
        assertEquals(-1.0, speedOf(motor), TOLERANCE,
                "STEP 1: setSpeed(-2.0) should leave the motor at -1.0.");
    }

    @Test
    @DisplayName("STEP 1: two motors are two separate things")
    void motorsAreIndependent() {
        Object intake = motor("Intake", 1);
        Object shooter = motor("Shooter", 2);

        setSpeed(intake, 0.8);

        assertEquals(0.8, speedOf(intake), TOLERANCE,
                "STEP 1: setting the intake's speed should change the intake.");
        assertEquals(0.0, speedOf(shooter), TOLERANCE,
                "STEP 1: setting the intake's speed changed the SHOOTER too. Each object needs its own copy "
                        + "of the field — check that `speed` is not declared static.");
    }
}
