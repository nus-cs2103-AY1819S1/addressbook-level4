package seedu.thanepark.ui.exceptions;

/**
 * Represents an error which occurs during handling of UI features. Non-fatal.
 */

public class AccessibilityException extends Exception {
    public AccessibilityException(String message) {
        super(message);
    }
}
