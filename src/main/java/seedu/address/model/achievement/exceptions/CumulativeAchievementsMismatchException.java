package seedu.address.model.achievement.exceptions;

/**
 * Signals that the cumulative achievement fields in the {@code AchievementRecord} do not match.
 * Matching cumulative achievement fields: For the same achievement field, for example number of tasks completed,
 * today's achievement is no greater than this week's achievement, which is no greater than all-time achievement.
 */
public class CumulativeAchievementsMismatchException extends RuntimeException {
    public CumulativeAchievementsMismatchException() {
        super("Cumulative achievement fields do not match.");
    }
}
