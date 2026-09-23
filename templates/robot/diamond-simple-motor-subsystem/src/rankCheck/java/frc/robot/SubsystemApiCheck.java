package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.SimpleMotorSubsystem;

/** Checks STEP 2, STEP 3 and STEP 6: the subsystem's public verbs. */
class SubsystemApiCheck {

    @BeforeAll
    static void startHal() {
        CheckSupport.ensureHal();
    }

    @Test
    @DisplayName("STEP 2: the subsystem has setSpeed and stop")
    void hasSetSpeedAndStop() {
        assertTrue(CheckSupport.hasMethod(SimpleMotorSubsystem.class, "setSpeed", double.class),
                "STEP 2: I couldn't find `public void setSpeed(double speed)` in SimpleMotorSubsystem.");
        assertTrue(CheckSupport.hasMethod(SimpleMotorSubsystem.class, "stop"),
                "STEP 2: I couldn't find `public void stop()` in SimpleMotorSubsystem.");
    }

    @Test
    @DisplayName("STEP 2: the subsystem reports position and velocity")
    void hasEncoderGetters() {
        assertTrue(CheckSupport.hasMethod(SimpleMotorSubsystem.class, "getPositionRotations"),
                "STEP 2: I couldn't find `public double getPositionRotations()`. The rest of the robot "
                        + "needs a way to ask where the mechanism is without touching the motor.");
        assertTrue(CheckSupport.hasMethod(SimpleMotorSubsystem.class, "getVelocityRps"),
                "STEP 2: I couldn't find `public double getVelocityRps()`.");

        Method position = CheckSupport.findMethod(2, SimpleMotorSubsystem.class, "getPositionRotations");
        assertEquals(double.class, position.getReturnType(),
                "STEP 2: getPositionRotations() should return a double.");
        Method velocity = CheckSupport.findMethod(2, SimpleMotorSubsystem.class, "getVelocityRps");
        assertEquals(double.class, velocity.getReturnType(),
                "STEP 2: getVelocityRps() should return a double.");
    }

    @Test
    @DisplayName("STEP 2: the subsystem reports whether it is at the limit")
    void hasIsAtLimit() {
        assertTrue(CheckSupport.hasMethod(SimpleMotorSubsystem.class, "isAtLimit"),
                "STEP 2: I couldn't find `public boolean isAtLimit()` in SimpleMotorSubsystem.");
        Method atLimit = CheckSupport.findMethod(2, SimpleMotorSubsystem.class, "isAtLimit");
        assertEquals(boolean.class, atLimit.getReturnType(),
                "STEP 2: isAtLimit() should return a boolean.");
    }

    @Test
    @DisplayName("STEP 2: the subsystem does not hand out its motor")
    void doesNotExposeTheMotor() {
        for (Method method : SimpleMotorSubsystem.class.getDeclaredMethods()) {
            String returnType = method.getReturnType().getSimpleName();
            assertTrue(!returnType.equals("TalonFX"),
                    "STEP 2: `" + method.getName() + "()` hands out the TalonFX itself. A subsystem that "
                            + "gives away its hardware has stopped being a subsystem — anything could then "
                            + "command the motor behind your back. Expose verbs, not the motor.");
        }
    }

    @Test
    @DisplayName("STEP 3: the subsystem owns a limit switch")
    void hasLimitSwitch() {
        assertTrue(CheckSupport.fieldOfType(SimpleMotorSubsystem.class, DigitalInput.class) != null,
                "STEP 3: I couldn't find a DigitalInput field in SimpleMotorSubsystem. That is the limit "
                        + "switch — build it from the DIO channel in Constants.");
    }

    @Test
    @DisplayName("STEP 6: there are two command factories")
    void hasCommandFactories() {
        for (String name : new String[] {"runForward", "runReverse"}) {
            assertTrue(CheckSupport.hasMethod(SimpleMotorSubsystem.class, name),
                    "STEP 6: I couldn't find `public Command " + name + "()` in SimpleMotorSubsystem.");
            Method factory = CheckSupport.findMethod(6, SimpleMotorSubsystem.class, name);
            assertTrue(Command.class.isAssignableFrom(factory.getReturnType()),
                    "STEP 6: " + name + "() should return a Command, but it returns "
                            + factory.getReturnType().getSimpleName() + ".");
        }
    }
}
