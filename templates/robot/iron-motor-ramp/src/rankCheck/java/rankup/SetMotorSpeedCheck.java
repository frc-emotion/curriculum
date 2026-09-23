package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Checks STEP 3, STEP 4 and STEP 6: setMotorSpeed, its overload, and the speed log. */
class SetMotorSpeedCheck {

    private static final double TOLERANCE = 1e-9;

    private static Method oneArg(int step) {
        return CheckSupport.findMethod(step, MotorRamp.class, "setMotorSpeed", double.class);
    }

    private static Method twoArg(int step) {
        return CheckSupport.findMethod(step, MotorRamp.class, "setMotorSpeed", double.class, int.class);
    }

    private static List<?> speedLog(int step) {
        Field field = CheckSupport.findField(step, MotorRamp.class, "speedLog");
        return CheckSupport.readList(step, field);
    }

    @Test
    @DisplayName("STEP 3: setMotorSpeed prints the speed it was given")
    void printsTheSpeed() {
        Method method = oneArg(3);
        String printed = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(3, method, 0.5));
        List<String> lines = CheckSupport.nonBlankLines(printed);

        assertFalse(lines.isEmpty(),
                "STEP 3: setMotorSpeed(0.5) didn't print anything. It should print a line shaped like "
                        + "`Motor set to 0.5`.");
        String line = lines.get(0);
        assertTrue(line.toLowerCase().contains("motor") && line.toLowerCase().contains("set to"),
                "STEP 3: setMotorSpeed(0.5) printed \"" + line + "\". Expected a line shaped like "
                        + "`Motor set to 0.5`.");
        List<Double> numbers = CheckSupport.numbersIn(line);
        assertFalse(numbers.isEmpty(),
                "STEP 3: setMotorSpeed(0.5) printed \"" + line + "\" with no number in it.");
        assertEquals(0.5, numbers.get(numbers.size() - 1), TOLERANCE,
                "STEP 3: setMotorSpeed(0.5) should print the speed 0.5, but the line was \"" + line + "\".");
    }

    @Test
    @DisplayName("STEP 3: setMotorSpeed clamps before it prints")
    void clampsBeforePrinting() {
        Method method = oneArg(3);

        String high = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(3, method, 1.5));
        List<Double> highNumbers = CheckSupport.numbersIn(lastLine(high, 3, "setMotorSpeed(1.5)"));
        assertEquals(1.0, highNumbers.get(highNumbers.size() - 1), TOLERANCE,
                "STEP 3: setMotorSpeed(1.5) should print 1.0, not 1.5. Clamp to -1.0..1.0 by calling your "
                        + "clamp method from step 2.");

        String low = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(3, method, -3.0));
        List<Double> lowNumbers = CheckSupport.numbersIn(lastLine(low, 3, "setMotorSpeed(-3.0)"));
        assertEquals(-1.0, lowNumbers.get(lowNumbers.size() - 1), TOLERANCE,
                "STEP 3: setMotorSpeed(-3.0) should print -1.0.");
    }

    @Test
    @DisplayName("STEP 4: there is a second setMotorSpeed that takes a port")
    void overloadExists() {
        assertTrue(CheckSupport.hasMethod(MotorRamp.class, "setMotorSpeed", double.class, int.class),
                "STEP 4: I couldn't find `static void setMotorSpeed(double speed, int port)`. Two methods "
                        + "can share a name as long as they take different things — that is overloading.");
        assertTrue(CheckSupport.hasMethod(MotorRamp.class, "setMotorSpeed", double.class),
                "STEP 4: the original `setMotorSpeed(double speed)` should still be here too. Overloading "
                        + "adds a version, it doesn't replace one.");
    }

    @Test
    @DisplayName("STEP 4: the overload prints the port and the clamped speed")
    void overloadPrintsPortAndSpeed() {
        Method method = twoArg(4);

        String printed = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(4, method, 0.5, 3));
        String line = lastLine(printed, 4, "setMotorSpeed(0.5, 3)");
        assertTrue(line.toLowerCase().contains("motor") && line.toLowerCase().contains("set to"),
                "STEP 4: setMotorSpeed(0.5, 3) printed \"" + line + "\". Expected a line shaped like "
                        + "`Motor 3 set to 0.5`.");
        List<Double> numbers = CheckSupport.numbersIn(line);
        assertTrue(numbers.size() >= 2,
                "STEP 4: setMotorSpeed(0.5, 3) printed \"" + line + "\". The line needs both the port and "
                        + "the speed on it.");
        assertEquals(3.0, numbers.get(0), TOLERANCE,
                "STEP 4: the port should come before the speed, like `Motor 3 set to 0.5`. Got \"" + line + "\".");
        assertEquals(0.5, numbers.get(numbers.size() - 1), TOLERANCE,
                "STEP 4: the speed should be the last number on the line. Got \"" + line + "\".");

        String clamped = CheckSupport.capturePrintedOutput(() -> CheckSupport.call(4, method, 2.0, 3));
        List<Double> clampedNumbers = CheckSupport.numbersIn(lastLine(clamped, 4, "setMotorSpeed(2.0, 3)"));
        assertEquals(1.0, clampedNumbers.get(clampedNumbers.size() - 1), TOLERANCE,
                "STEP 4: setMotorSpeed(2.0, 3) should print 1.0 — the overload has to clamp too. Call your "
                        + "clamp method instead of retyping the logic.");
    }

    @Test
    @DisplayName("STEP 6: every setMotorSpeed call adds one entry to speedLog")
    void logGrowsByOnePerCall() {
        List<?> log = speedLog(6);
        int before = log.size();

        Method method = oneArg(3);
        CheckSupport.capturePrintedOutput(() -> CheckSupport.call(3, method, 0.5));
        assertEquals(before + 1, speedLog(6).size(),
                "STEP 6: speedLog should have grown by exactly one after setMotorSpeed(0.5). Add the "
                        + "clamped speed to the list inside setMotorSpeed.");

        Method overload = twoArg(4);
        CheckSupport.capturePrintedOutput(() -> CheckSupport.call(4, overload, 0.25, 2));
        assertEquals(before + 2, speedLog(6).size(),
                "STEP 6: the two-argument setMotorSpeed should log too, so the list grows by one again.");
    }

    @Test
    @DisplayName("STEP 6: the log stores the clamped speed, not the raw one")
    void logStoresClampedValue() {
        Method method = oneArg(3);
        CheckSupport.capturePrintedOutput(() -> CheckSupport.call(3, method, 4.2));

        List<?> log = speedLog(6);
        Object last = log.get(log.size() - 1);
        assertTrue(last instanceof Number,
                "STEP 6: speedLog should hold numbers, but the last entry is " + last + ".");
        assertEquals(1.0, ((Number) last).doubleValue(), TOLERANCE,
                "STEP 6: after setMotorSpeed(4.2), the last logged value should be the clamped 1.0 — that is "
                        + "what the motor was actually told to do.");
    }

    private static String lastLine(String printed, int step, String what) {
        List<String> lines = CheckSupport.nonBlankLines(printed);
        assertFalse(lines.isEmpty(), "STEP " + step + ": " + what + " didn't print anything.");
        return lines.get(lines.size() - 1);
    }
}
