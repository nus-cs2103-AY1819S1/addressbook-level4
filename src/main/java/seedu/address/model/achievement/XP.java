package seedu.address.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the experience point(xp) earned by the user while using the task manager.
 * Guarantees: xp value is immutable and valid as declared in {@link #isValidXP(Integer)}
 */
public class XP {
    
    public static final String MESSAGE_XP_CONSTRAINTS =
            "XP should have positive integer values.";
    private final Integer xp;

    /**
     * Constructs an XP of value 0.
     */
    public XP() {
        this.xp = 0;
    }

    /**
     * Constructs a {@code XP}.
     *
     * @param xp A valid xp value.
     */
    public XP(Integer xp) {
        requireNonNull(xp);
        checkArgument(isValidXP(xp), MESSAGE_XP_CONSTRAINTS);
        this.xp = xp;
    }

    /**
     * Getter of the xp value.
     * @return xp
     */
    public Integer getXp() {
        return this.xp;
    }

    /**
     * Returns true if a given integer is a valid xp.
     */
    public static boolean isValidXP(Integer test) {
        return test instanceof Integer && test >= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof XP // instanceof handles nulls
                && xp.equals(((XP) other).xp)); // state check
    }

    @Override
    public int hashCode() {
        return xp.hashCode();
    }

}
