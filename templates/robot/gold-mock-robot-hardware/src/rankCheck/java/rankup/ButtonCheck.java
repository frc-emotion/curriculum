package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rankup.util.Button;

/** Checks STEP 7: a Button runs whatever code it was given. */
class ButtonCheck {

    private static Object button(Runnable action) {
        Constructor<?> constructor = CheckSupport.findConstructor(7, Button.class, Runnable.class);
        return CheckSupport.construct(7, constructor, action);
    }

    @Test
    @DisplayName("STEP 7: pressing a button runs its action")
    void pressRunsTheAction() {
        AtomicInteger presses = new AtomicInteger();
        Object button = button(presses::incrementAndGet);

        CheckSupport.callOn(7, button, "press", new Class<?>[0]);

        assertEquals(1, presses.get(),
                "STEP 7: press() should run the Runnable the Button was built with. It didn't run at all.");
    }

    @Test
    @DisplayName("STEP 7: pressing twice runs it twice")
    void pressIsRepeatable() {
        AtomicInteger presses = new AtomicInteger();
        Object button = button(presses::incrementAndGet);

        CheckSupport.callOn(7, button, "press", new Class<?>[0]);
        CheckSupport.callOn(7, button, "press", new Class<?>[0]);
        CheckSupport.callOn(7, button, "press", new Class<?>[0]);

        assertEquals(3, presses.get(),
                "STEP 7: three presses should run the action three times, but it ran " + presses.get()
                        + " time(s). A Button stores the action and runs it on every press.");
    }

    @Test
    @DisplayName("STEP 7: building a button does not run its action")
    void constructingDoesNotRun() {
        AtomicInteger presses = new AtomicInteger();
        button(presses::incrementAndGet);

        assertEquals(0, presses.get(),
                "STEP 7: just building a Button ran its action. The whole point is that the code is stored "
                        + "now and run later, when the button is actually pressed.");
    }

    @Test
    @DisplayName("STEP 7: two buttons keep their own actions")
    void buttonsAreIndependent() {
        AtomicInteger first = new AtomicInteger();
        AtomicInteger second = new AtomicInteger();
        Object buttonA = button(first::incrementAndGet);
        Object buttonB = button(second::incrementAndGet);

        CheckSupport.callOn(7, buttonA, "press", new Class<?>[0]);

        assertEquals(1, first.get(), "STEP 7: pressing the first button should run the first action.");
        assertTrue(second.get() == 0,
                "STEP 7: pressing the first button also ran the second button's action. Each Button needs "
                        + "its own field — check it isn't static.");
    }
}
