package seedu.address.model.achievement.exceptions;

public class DateBreakPointsMismatchException extends RuntimeException {
    public DateBreakPointsMismatchException() {
        super("Next day break point and next week break point does not match.");
    }
}

