package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// FILE:        util/Button.java
// STEPS HERE:  7
// GUIDE:       GUIDE_URL  (section "Robot Gold")
// RUN:         ./gradlew run        CHECK: ./gradlew rankCheck
// PASSES WHEN: all fields are private, no port numbers appear outside Constants, and you can
//              explain in review what the lambda and method reference are doing.
// ============================================================
//
// A button doesn't know what it does. It is handed a chunk of code when it's built, and runs
// that code when pressed. That "code as a value" idea is the whole reason WPILib's
// command-based framework works the way it does.

public class Button {

    // STEP 7: Build the Button class
    // WHAT:       Give this class:
    //               - a private field holding a Runnable
    //               - a constructor `public Button(Runnable action)` that stores it
    //               - `public void press()` that runs the stored action
    //             A Runnable is java.lang.Runnable — already available, no import needed.
    //             It has exactly one method, run(), which takes nothing and returns nothing.
    // WHY:        This is the smallest possible version of WPILib's trigger bindings. Once
    //             you see that "a button holds a piece of code" you'll recognise
    //             `whileTrue(...)` at Diamond immediately.
    // CONCEPTS:   Functional interfaces, Runnable, storing behaviour in a field, lambdas,
    //             method references
    // READ:       Guide > Robot Gold > Resources #3 and #4
    // CHECKED BY: ButtonCheck (press() must run whatever it was given)
    // DONE WHEN:  a Button built with a lambda runs that lambda when pressed, and so does
    //             one built with a method reference.

}
