package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rankup.subsystems.Intake;
import rankup.subsystems.MockSubsystem;
import rankup.subsystems.Shooter;
import rankup.util.Mechanism;
import rankup.util.Motor;

/** Checks STEP 5 and STEP 6: the shared parent and the two subsystems. */
class SubsystemCheck {

    private static final double TOLERANCE = 1e-9;

    /** Reads a constant out of Constants by name, so this file compiles before you add them. */
    private static double constantDouble(String name) {
        Object value = CheckSupport.readFieldValue(2, CheckSupport.findField(2, Constants.class, name), null);
        return ((Number) value).doubleValue();
    }

    private static int constantInt(String name) {
        Object value = CheckSupport.readFieldValue(2, CheckSupport.findField(2, Constants.class, name), null);
        return ((Number) value).intValue();
    }

    private static Object build(int step, Class<?> type) {
        Constructor<?> constructor = CheckSupport.findConstructor(step, type);
        return CheckSupport.construct(step, constructor, new Object[0]);
    }

    private static double motorSpeedOf(int step, Object subsystem) {
        Field motorField = CheckSupport.fieldOfType(subsystem.getClass(), Motor.class);
        assertNotNull(motorField,
                "STEP 6: " + subsystem.getClass().getSimpleName() + " should have a Motor field of its own. "
                        + "A subsystem owns its hardware.");
        Object motor = CheckSupport.readFieldValue(step, motorField, subsystem);
        assertNotNull(motor,
                "STEP 6: " + subsystem.getClass().getSimpleName() + "'s Motor field is null. Build it in "
                        + "the constructor.");
        Object speed = CheckSupport.callOn(step, motor, "getSpeed", new Class<?>[0]);
        return ((Number) speed).doubleValue();
    }

    @Test
    @DisplayName("STEP 5: MockSubsystem takes a name and can report it")
    void parentHasNameAndPeriodic() {
        Constructor<?> constructor = CheckSupport.findConstructor(5, MockSubsystem.class, String.class);
        Object subsystem = CheckSupport.construct(5, constructor, "Elevator");

        assertEquals("Elevator", CheckSupport.callOn(5, subsystem, "getName", new Class<?>[0]),
                "STEP 5: getName() should give back the name the subsystem was built with.");
        assertTrue(CheckSupport.hasMethodAnywhere(MockSubsystem.class, "periodic"),
                "STEP 5: MockSubsystem should have a `public void periodic()`.");

        String printed = CheckSupport.capturePrintedOutput(
                () -> CheckSupport.callOn(5, subsystem, "periodic", new Class<?>[0]));
        assertTrue(printed.contains("Elevator"),
                "STEP 5: periodic() should print the subsystem's name. It printed: \"" + printed.trim() + "\"");
    }

    @Test
    @DisplayName("STEP 6: Intake and Shooter are MockSubsystems")
    void subsystemsExtendTheParent() {
        assertTrue(MockSubsystem.class.isAssignableFrom(Intake.class),
                "STEP 6: Intake should `extends MockSubsystem`, so it gets the name and periodic() for free.");
        assertTrue(MockSubsystem.class.isAssignableFrom(Shooter.class),
                "STEP 6: Shooter should `extends MockSubsystem`.");
    }

    @Test
    @DisplayName("STEP 6: Intake and Shooter are Mechanisms")
    void subsystemsImplementTheInterface() {
        assertTrue(Mechanism.class.isAssignableFrom(Intake.class),
                "STEP 6: Intake should `implements Mechanism`, so anything holding a Mechanism can stop it.");
        assertTrue(Mechanism.class.isAssignableFrom(Shooter.class),
                "STEP 6: Shooter should `implements Mechanism`.");
    }

    @Test
    @DisplayName("STEP 6: both subsystems override periodic themselves")
    void bothOverridePeriodic() {
        assertTrue(CheckSupport.hasMethod(Intake.class, "periodic"),
                "STEP 6: Intake should declare its own `@Override public void periodic()` that prints its "
                        + "motor speed. Inheriting the parent's isn't enough here.");
        assertTrue(CheckSupport.hasMethod(Shooter.class, "periodic"),
                "STEP 6: Shooter should declare its own `@Override public void periodic()`.");
    }

    @Test
    @DisplayName("STEP 6: each subsystem's Motor is private")
    void motorsArePrivate() {
        for (Class<?> type : new Class<?>[] {Intake.class, Shooter.class}) {
            Field motorField = CheckSupport.fieldOfType(type, Motor.class);
            assertNotNull(motorField,
                    "STEP 6: " + type.getSimpleName() + " should have its own Motor field.");
            assertTrue(Modifier.isPrivate(motorField.getModifiers()),
                    "STEP 6: " + type.getSimpleName() + "'s Motor field is "
                            + CheckSupport.modifiersOf(motorField.getModifiers())
                            + ", but it should be private. Nothing outside the subsystem should touch the "
                            + "motor directly.");
        }
    }

    @Test
    @DisplayName("STEP 6: the intake runs at the speed from Constants")
    void intakeRunsAtConstantSpeed() {
        Object intake = build(6, Intake.class);
        CheckSupport.capturePrintedOutput(() -> CheckSupport.callOn(6, intake, "run", new Class<?>[0]));
        assertEquals(constantDouble("INTAKE_SPEED"), motorSpeedOf(6, intake), TOLERANCE,
                "STEP 6: after run(), the intake's motor should be at Constants.INTAKE_SPEED.");
    }

    @Test
    @DisplayName("STEP 6: the shooter runs at some non-zero speed")
    void shooterRuns() {
        Object shooter = build(6, Shooter.class);
        CheckSupport.capturePrintedOutput(() -> CheckSupport.callOn(6, shooter, "run", new Class<?>[0]));
        assertNotEquals(0.0, motorSpeedOf(6, shooter),
                "STEP 6: after run(), the shooter's motor should be turning. Use a speed constant from "
                        + "Constants rather than typing the number here.");
    }

    @Test
    @DisplayName("STEP 6: stop() really stops both subsystems")
    void stopStops() {
        Object intake = build(6, Intake.class);
        CheckSupport.capturePrintedOutput(() -> {
            CheckSupport.callOn(6, intake, "run", new Class<?>[0]);
            CheckSupport.callOn(6, intake, "stop", new Class<?>[0]);
        });
        assertEquals(0.0, motorSpeedOf(6, intake), TOLERANCE,
                "STEP 6: after stop(), the intake's motor should be at 0.0.");

        Object shooter = build(6, Shooter.class);
        CheckSupport.capturePrintedOutput(() -> {
            CheckSupport.callOn(6, shooter, "run", new Class<?>[0]);
            CheckSupport.callOn(6, shooter, "stop", new Class<?>[0]);
        });
        assertEquals(0.0, motorSpeedOf(6, shooter), TOLERANCE,
                "STEP 6: after stop(), the shooter's motor should be at 0.0.");
    }

    @Test
    @DisplayName("STEP 6: each subsystem's motor uses its port from Constants")
    void motorsUseTheRightPorts() {
        Object intake = build(6, Intake.class);
        Field intakeMotor = CheckSupport.fieldOfType(Intake.class, Motor.class);
        assertNotNull(intakeMotor, "STEP 6: Intake should have a Motor field.");
        Object motor = CheckSupport.readFieldValue(6, intakeMotor, intake);
        assertEquals(constantInt("INTAKE_MOTOR_PORT"), CheckSupport.callOn(6, motor, "getPort", new Class<?>[0]),
                "STEP 6: the intake's motor should be built with Constants.INTAKE_MOTOR_PORT.");

        Object shooter = build(6, Shooter.class);
        Field shooterMotor = CheckSupport.fieldOfType(Shooter.class, Motor.class);
        assertNotNull(shooterMotor, "STEP 6: Shooter should have a Motor field.");
        Object motor2 = CheckSupport.readFieldValue(6, shooterMotor, shooter);
        assertEquals(constantInt("SHOOTER_MOTOR_PORT"), CheckSupport.callOn(6, motor2, "getPort", new Class<?>[0]),
                "STEP 6: the shooter's motor should be built with Constants.SHOOTER_MOTOR_PORT.");
    }

    @Test
    @DisplayName("STEP 6: periodic() reports the motor's speed")
    void periodicPrintsSpeed() {
        Object intake = build(6, Intake.class);
        CheckSupport.capturePrintedOutput(() -> CheckSupport.callOn(6, intake, "run", new Class<?>[0]));
        String printed = CheckSupport.capturePrintedOutput(
                () -> CheckSupport.callOn(6, intake, "periodic", new Class<?>[0]));
        double expected = constantDouble("INTAKE_SPEED");
        boolean printedTheSpeed = CheckSupport.numbersIn(printed).stream()
                .anyMatch(number -> Math.abs(number - expected) < 1e-6);
        assertTrue(printedTheSpeed,
                "STEP 6: Intake's periodic() should print its motor's current speed (" + expected
                        + " after run()). It printed: \"" + printed.trim() + "\"");
    }
}
