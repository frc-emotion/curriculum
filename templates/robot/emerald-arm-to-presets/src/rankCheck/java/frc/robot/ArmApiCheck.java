package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.ArmSubsystem;

/** Checks STEP 4: the arm's public vocabulary is in degrees. */
class ArmApiCheck {

    @BeforeAll
    static void startHal() {
        CheckSupport.ensureHal();
    }

    @Test
    @DisplayName("STEP 4: the arm takes a setpoint in degrees")
    void hasSetSetpoint() {
        assertTrue(CheckSupport.hasMethod(ArmSubsystem.class, "setSetpoint", double.class),
                "STEP 4: I couldn't find `public void setSetpoint(double targetDegrees)` in ArmSubsystem.");
    }

    @Test
    @DisplayName("STEP 4: the arm reports its setpoint and its angle")
    void reportsSetpointAndAngle() {
        assertTrue(CheckSupport.hasMethod(ArmSubsystem.class, "getSetpointDegrees"),
                "STEP 4: I couldn't find `public double getSetpointDegrees()` in ArmSubsystem.");
        assertTrue(CheckSupport.hasMethod(ArmSubsystem.class, "getAngleDegrees"),
                "STEP 4: I couldn't find `public double getAngleDegrees()`. This is the one that converts "
                        + "encoder rotations into a real arm angle using GEAR_RATIO.");

        Method setpoint = CheckSupport.findMethod(4, ArmSubsystem.class, "getSetpointDegrees");
        assertEquals(double.class, setpoint.getReturnType(),
                "STEP 4: getSetpointDegrees() should return a double.");
        Method angle = CheckSupport.findMethod(4, ArmSubsystem.class, "getAngleDegrees");
        assertEquals(double.class, angle.getReturnType(),
                "STEP 4: getAngleDegrees() should return a double.");
    }

    @Test
    @DisplayName("STEP 4: the arm can say whether it has arrived")
    void hasAtSetpoint() {
        assertTrue(CheckSupport.hasMethod(ArmSubsystem.class, "atSetpoint"),
                "STEP 4: I couldn't find `public boolean atSetpoint()`. Commands need this to know when "
                        + "to finish.");
        Method atSetpoint = CheckSupport.findMethod(4, ArmSubsystem.class, "atSetpoint");
        assertEquals(boolean.class, atSetpoint.getReturnType(),
                "STEP 4: atSetpoint() should return a boolean.");
    }
}
