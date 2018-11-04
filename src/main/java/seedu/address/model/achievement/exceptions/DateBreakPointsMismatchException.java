package seedu.address.model.achievement.exceptions;

/**
 * Signals that {@code nextDayBreakPoint} and {@code nextWeekBreakPoint} in the {@code AchievementRecord} do not match.
 * Matching breakpoints: {@code nextDayBreakPoint} is at most 6 days after {@code nextWeekBreakPoint} and not before
 * {@code nextWeekBreakPoint}.
 */
public class DateBreakPointsMismatchException extends RuntimeException {
    public DateBreakPointsMismatchException() {
        super("Next day break point and next week break point does not match.");
    }
}

