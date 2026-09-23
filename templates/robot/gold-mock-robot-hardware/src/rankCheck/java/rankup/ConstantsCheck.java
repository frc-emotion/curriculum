package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 2: the Constants class. */
class ConstantsCheck {

    private static Field constant(String name) {
        return CheckSupport.findField(2, Constants.class, name);
    }

    private static void assertPublicStaticFinal(String name) {
        Field field = constant(name);
        int modifiers = field.getModifiers();
        assertTrue(Modifier.isPublic(modifiers),
                "STEP 2: " + name + " is " + CheckSupport.modifiersOf(modifiers)
                        + ". It needs to be public so the subsystems can read it.");
        assertTrue(Modifier.isStatic(modifiers),
                "STEP 2: " + name + " needs to be static — it belongs to the robot, not to an object you "
                        + "have to build first.");
        assertTrue(Modifier.isFinal(modifiers),
                "STEP 2: " + name + " needs to be final. A port that can change while the robot is running "
                        + "is not a constant.");
    }

    private static Object valueOf(String name) {
        return CheckSupport.readFieldValue(2, constant(name), null);
    }

    @Test
    @DisplayName("STEP 2: the motor ports are public static final ints")
    void portsAreConstants() {
        assertPublicStaticFinal("INTAKE_MOTOR_PORT");
        assertPublicStaticFinal("SHOOTER_MOTOR_PORT");
        assertEquals(int.class, constant("INTAKE_MOTOR_PORT").getType(),
                "STEP 2: INTAKE_MOTOR_PORT should be an int — ports are whole numbers.");
        assertEquals(int.class, constant("SHOOTER_MOTOR_PORT").getType(),
                "STEP 2: SHOOTER_MOTOR_PORT should be an int.");
    }

    @Test
    @DisplayName("STEP 2: the ports have the values the wiring diagram says")
    void portValues() {
        assertEquals(1, valueOf("INTAKE_MOTOR_PORT"),
                "STEP 2: INTAKE_MOTOR_PORT should be 1.");
        assertEquals(2, valueOf("SHOOTER_MOTOR_PORT"),
                "STEP 2: SHOOTER_MOTOR_PORT should be 2.");
    }

    @Test
    @DisplayName("STEP 2: the intake speed is a public static final double")
    void intakeSpeedIsAConstant() {
        assertPublicStaticFinal("INTAKE_SPEED");
        assertEquals(double.class, constant("INTAKE_SPEED").getType(),
                "STEP 2: INTAKE_SPEED should be a double — speeds are fractions of full power.");
        assertEquals(0.8, (Double) valueOf("INTAKE_SPEED"), 1e-9,
                "STEP 2: INTAKE_SPEED should be 0.8.");
    }
}
