package rankup;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpers shared by the rank checks.
 *
 * <p>This file is part of the grader, not the assessment. You do not need to read it, and
 * you should not edit it — if you change the grader, your reviewer will notice.
 */
final class CheckSupport {

    private CheckSupport() {
    }

    /**
     * Calls a method you were asked to write, and turns the "not done yet" exception into a
     * readable failure naming the step, instead of a stack trace.
     */
    static <T> T attempt(int step, String description, Supplier<T> call) {
        try {
            return call.get();
        } catch (UnsupportedOperationException e) {
            return fail("STEP " + step + ": " + description
                    + " — not written yet. The method still throws UnsupportedOperationException.");
        }
    }

    /** Reads a source file from the project so a check can look at what you wrote. */
    static String readSource(String relativePath) {
        try {
            return Files.readString(Path.of(relativePath));
        } catch (IOException e) {
            return fail("Could not read " + relativePath + ". Did the file get renamed or deleted? "
                    + "Run the check from inside your rank folder.");
        }
    }

    /**
     * Removes comments and string literals from Java source.
     *
     * <p>Every source scan runs on the stripped version, so the STEP comments describing what
     * to write can never be mistaken for the code itself.
     */
    static String stripCommentsAndStrings(String source) {
        StringBuilder out = new StringBuilder(source.length());
        int i = 0;
        int n = source.length();
        while (i < n) {
            char c = source.charAt(i);
            char next = (i + 1 < n) ? source.charAt(i + 1) : '\0';

            if (c == '/' && next == '/') {
                while (i < n && source.charAt(i) != '\n') {
                    i++;
                }
            } else if (c == '/' && next == '*') {
                i += 2;
                while (i + 1 < n && !(source.charAt(i) == '*' && source.charAt(i + 1) == '/')) {
                    if (source.charAt(i) == '\n') {
                        out.append('\n');
                    }
                    i++;
                }
                i = Math.min(i + 2, n);
            } else if (c == '"') {
                out.append("\"\"");
                i++;
                while (i < n && source.charAt(i) != '"') {
                    if (source.charAt(i) == '\\') {
                        i++;
                    }
                    i++;
                }
                i++;
            } else if (c == '\'') {
                out.append("''");
                i++;
                while (i < n && source.charAt(i) != '\'') {
                    if (source.charAt(i) == '\\') {
                        i++;
                    }
                    i++;
                }
                i++;
            } else {
                out.append(c);
                i++;
            }
        }
        return out.toString();
    }

    /**
     * Returns the body of a method, found by a snippet of its declaration (for example
     * "String decideDirection"). Returns an empty string if the method isn't there.
     */
    static String methodBody(String strippedSource, String declarationSnippet) {
        int start = strippedSource.indexOf(declarationSnippet);
        if (start < 0) {
            return "";
        }
        int open = strippedSource.indexOf('{', start);
        if (open < 0) {
            return "";
        }
        int depth = 0;
        for (int i = open; i < strippedSource.length(); i++) {
            char c = strippedSource.charAt(i);
            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) {
                    return strippedSource.substring(open + 1, i);
                }
            }
        }
        return strippedSource.substring(open + 1);
    }

    /**
     * Returns the body of every method declared with this name, joined together.
     *
     * <p>Only declarations count: a call like `rampUp(1.0, 0.25);` is followed by a semicolon,
     * while a declaration is followed by an opening brace. Returns an empty string if the
     * method isn't declared anywhere.
     */
    static String methodBodyOf(String strippedSource, String methodName) {
        StringBuilder combined = new StringBuilder();
        Matcher declaration = Pattern
                .compile("\\b" + Pattern.quote(methodName) + "\\s*\\([^)]*\\)\\s*\\{")
                .matcher(strippedSource);
        while (declaration.find()) {
            int open = declaration.end() - 1;
            int depth = 0;
            for (int i = open; i < strippedSource.length(); i++) {
                char c = strippedSource.charAt(i);
                if (c == '{') {
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0) {
                        combined.append(strippedSource, open + 1, i).append('\n');
                        break;
                    }
                }
            }
        }
        return combined.toString();
    }

    // ------------------------------------------------------------
    // Reflection: the checks look your methods up by name instead of calling them
    // directly, so this file still compiles before you have written them.
    // ------------------------------------------------------------

    /** Finds one of your methods, or fails with a message naming the step and the signature. */
    static Method findMethod(int step, Class<?> owner, String name, Class<?>... parameterTypes) {
        try {
            Method method = owner.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            return fail("STEP " + step + ": I couldn't find a method called `" + name + "("
                    + describe(parameterTypes) + ")` in " + owner.getSimpleName() + ". "
                    + "Check the name, the parameter types and their order — the checks look it up exactly.");
        }
    }

    /** True if a method exists, without failing when it doesn't. */
    static boolean hasMethod(Class<?> owner, String name, Class<?>... parameterTypes) {
        try {
            owner.getDeclaredMethod(name, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /** Finds one of your fields, or fails with a message naming the step. */
    static Field findField(int step, Class<?> owner, String name) {
        try {
            Field field = owner.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            return fail("STEP " + step + ": I couldn't find a field called `" + name + "` in "
                    + owner.getSimpleName() + ".");
        }
    }

    /** Calls one of your static methods and unwraps the real error if it throws. */
    static Object call(int step, Method method, Object... args) {
        try {
            return method.invoke(null, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            return fail("STEP " + step + ": calling `" + method.getName() + "` threw "
                    + cause.getClass().getSimpleName()
                    + (cause.getMessage() == null ? "" : (": " + cause.getMessage()))
                    + ". Fix that before the rest of this check can run.");
        } catch (IllegalAccessException e) {
            return fail("STEP " + step + ": I couldn't call `" + method.getName() + "`. Is it `static`?");
        }
    }

    static double callDouble(int step, Method method, Object... args) {
        Object result = call(step, method, args);
        if (!(result instanceof Number)) {
            return fail("STEP " + step + ": `" + method.getName() + "` should return a number, but it "
                    + "returned " + describeValue(result) + ".");
        }
        return ((Number) result).doubleValue();
    }

    static boolean callBoolean(int step, Method method, Object... args) {
        Object result = call(step, method, args);
        if (!(result instanceof Boolean)) {
            return fail("STEP " + step + ": `" + method.getName() + "` should return a boolean, but it "
                    + "returned " + describeValue(result) + ".");
        }
        return (Boolean) result;
    }

    /** Reads a static field of yours as a List, or fails helpfully. */
    static List<?> readList(int step, Field field) {
        try {
            Object value = field.get(null);
            if (value == null) {
                return fail("STEP " + step + ": `" + field.getName() + "` is null. It needs to be created "
                        + "where you declare it, not just declared.");
            }
            if (!(value instanceof List)) {
                return fail("STEP " + step + ": `" + field.getName() + "` should be a list, but it is "
                        + describeValue(value) + ".");
            }
            return (List<?>) value;
        } catch (IllegalAccessException e) {
            return fail("STEP " + step + ": I couldn't read `" + field.getName() + "`. Is it `static`?");
        }
    }


    // ------------------------------------------------------------
    // Objects: making them and poking at them, for the ranks with classes in them
    // ------------------------------------------------------------

    /** Finds a constructor, or fails with a message naming the step. */
    static Constructor<?> findConstructor(int step, Class<?> owner, Class<?>... parameterTypes) {
        try {
            Constructor<?> constructor = owner.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            return fail("STEP " + step + ": I couldn't find a constructor `" + owner.getSimpleName() + "("
                    + describe(parameterTypes) + ")`. Check the parameter types and their order.");
        }
    }

    /** Builds one of your objects. */
    static Object construct(int step, Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            return fail("STEP " + step + ": building a " + constructor.getDeclaringClass().getSimpleName()
                    + " threw " + cause.getClass().getSimpleName()
                    + (cause.getMessage() == null ? "" : (": " + cause.getMessage())) + ".");
        } catch (ReflectiveOperationException e) {
            return fail("STEP " + step + ": I couldn't build a "
                    + constructor.getDeclaringClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    /** Calls a method on one of your objects. */
    static Object callOn(int step, Object target, String name, Class<?>[] parameterTypes, Object... args) {
        Method method = findMethodAnywhere(step, target.getClass(), name, parameterTypes);
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            return fail("STEP " + step + ": calling `" + name + "` on a "
                    + target.getClass().getSimpleName() + " threw " + cause.getClass().getSimpleName()
                    + (cause.getMessage() == null ? "" : (": " + cause.getMessage())) + ".");
        } catch (IllegalAccessException e) {
            return fail("STEP " + step + ": I couldn't call `" + name + "` on a "
                    + target.getClass().getSimpleName() + ". Is it public?");
        }
    }

    /** Like findMethod, but also looks at inherited methods. */
    static Method findMethodAnywhere(int step, Class<?> owner, String name, Class<?>... parameterTypes) {
        Class<?> current = owner;
        while (current != null) {
            try {
                Method method = current.getDeclaredMethod(name, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                current = current.getSuperclass();
            }
        }
        return fail("STEP " + step + ": I couldn't find a method called `" + name + "("
                + describe(parameterTypes) + ")` on " + owner.getSimpleName()
                + " or anything it inherits from.");
    }

    /** True when a class, or something it inherits from, has this method. */
    static boolean hasMethodAnywhere(Class<?> owner, String name, Class<?>... parameterTypes) {
        Class<?> current = owner;
        while (current != null) {
            try {
                current.getDeclaredMethod(name, parameterTypes);
                return true;
            } catch (NoSuchMethodException e) {
                current = current.getSuperclass();
            }
        }
        return false;
    }

    /** The first field of a given type declared on a class, or null. */
    static Field fieldOfType(Class<?> owner, Class<?> type) {
        for (Field field : owner.getDeclaredFields()) {
            if (type.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    /** Reads a field off one of your objects. */
    static Object readFieldValue(int step, Field field, Object target) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException e) {
            return fail("STEP " + step + ": I couldn't read the field `" + field.getName() + "`.");
        }
    }

    /** A readable list of modifiers, for error messages. */
    static String modifiersOf(int modifiers) {
        String text = Modifier.toString(modifiers);
        return text.isEmpty() ? "package-private" : text;
    }

    // ------------------------------------------------------------
    // Console output
    // ------------------------------------------------------------

    /** Runs something and gives back whatever it printed. */
    static String capturePrintedOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            action.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static final Pattern NUMBER = Pattern.compile("-?\\d+(?:\\.\\d+)?(?:[eE][-+]?\\d+)?");

    /** Every number in a line of text, in order. */
    static List<Double> numbersIn(String text) {
        List<Double> numbers = new ArrayList<>();
        Matcher matcher = NUMBER.matcher(text);
        while (matcher.find()) {
            numbers.add(Double.parseDouble(matcher.group()));
        }
        return numbers;
    }

    /** Lines that actually have something on them. */
    static List<String> nonBlankLines(String text) {
        List<String> lines = new ArrayList<>();
        for (String line : text.split("\\R")) {
            if (!line.isBlank()) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    // ------------------------------------------------------------
    // Small helpers for readable messages
    // ------------------------------------------------------------

    private static String describe(Class<?>[] types) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(types[i].getSimpleName());
        }
        return sb.toString();
    }

    private static String describeValue(Object value) {
        return value == null ? "null" : (value.getClass().getSimpleName() + " (" + value + ")");
    }
}
