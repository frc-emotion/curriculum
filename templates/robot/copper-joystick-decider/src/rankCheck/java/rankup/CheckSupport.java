package rankup;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

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
}
