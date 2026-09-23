package rankup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rankup.util.Mechanism;

/** Checks STEP 4: the Mechanism interface. */
class MechanismCheck {

    @Test
    @DisplayName("STEP 4: Mechanism promises stop()")
    void declaresStop() {
        assertTrue(CheckSupport.hasMethod(Mechanism.class, "stop"),
                "STEP 4: Mechanism should declare `void stop();`. In an interface that is the whole line — "
                        + "no body, just a semicolon.");
        Method stop = CheckSupport.findMethod(4, Mechanism.class, "stop");
        assertEquals(void.class, stop.getReturnType(),
                "STEP 4: stop() should return void.");
    }

    @Test
    @DisplayName("STEP 4: Mechanism promises getName()")
    void declaresGetName() {
        assertTrue(CheckSupport.hasMethod(Mechanism.class, "getName"),
                "STEP 4: Mechanism should declare `String getName();`.");
        Method getName = CheckSupport.findMethod(4, Mechanism.class, "getName");
        assertEquals(String.class, getName.getReturnType(),
                "STEP 4: getName() should return a String.");
    }

    @Test
    @DisplayName("STEP 4: Mechanism really is an interface")
    void isAnInterface() {
        assertTrue(Mechanism.class.isInterface(),
                "STEP 4: Mechanism should stay an interface. An interface is a promise about what something "
                        + "can do; a class is a thing that does it.");
    }
}
