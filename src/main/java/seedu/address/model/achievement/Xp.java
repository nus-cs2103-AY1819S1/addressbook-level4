package seedu.address.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the experience point(xp) earned by the user while using the task manager.
 * Guarantees: xp value is immutable and valid as declared in {@link #isValidXp(Integer)}
 */
public class Xp {

    public static final String MESSAGE_XP_CONSTRAINTS =
            "Xp should have positive integer values below " + AchievementRecord.MAX_INT_VALUE;
    private final Integer xp;

    /**
     * Constructs an Xp of value 0.
     */
    public Xp() {
        this.xp = 0;
    }

    /**
     * Constructs a {@code Xp}.
     *
     * @param xp A valid xp value.
     */
    public Xp(Integer xp) {
        requireNonNull(xp);
        checkArgument(isValidXp(xp), MESSAGE_XP_CONSTRAINTS);
        this.xp = xp;
    }

    /**
     * Returns true if a given integer is a valid xp.
     */
    public static boolean isValidXp(Integer test) {
        return test < AchievementRecord.MAX_INT_VALUE && test >= 0;
    }

    /**
     * @return the {@code Integer} value representing the Xp.
     */
    public Integer getXp() {
        return this.xp;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Xp // instanceof handles nulls
                && xp.equals(((Xp) other).xp)); // state check
    }

    @Override
    public int hashCode() {
        return xp.hashCode();
    }

}
