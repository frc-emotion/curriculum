package rankup.util;

// ============================================================
// RANK:        Robot Gold: Mock Robot Hardware
// STEPS HERE:  7
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Robot track > Gold tab)
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
    // DONE WHEN:  a Button built with a lambda runs that lambda when pressed, and so does
    //             one built with a method reference.

}
