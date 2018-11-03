package seedu.address.model.achievement.exceptions;

public class DateBreakpointsMismatchException extends RuntimeException {
    public DateBreakpointsMismatchException() {
        super("Next day break point and next week break point does not match.");
    }
}

