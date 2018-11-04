package seedu.address.model.achievement.exceptions;

/**
 * Signals current xp value and level in the {@code AchievementRecord} do not match.
 * Matching xp and level: xp value is within the range of minimum to maximum xp of that level, as specified in the
 * definition of the {@code Level} enum and the getMatchingLevel method in {@code AchievementRecord}.
 * i.e. level.minXp <= xp < level.maxXp
 */
public class XpLevelMismatchException extends RuntimeException {
    public static final String MESSAGE = "Xp value and level do not match.";

    public XpLevelMismatchException() {
        super(MESSAGE);
    }
}

