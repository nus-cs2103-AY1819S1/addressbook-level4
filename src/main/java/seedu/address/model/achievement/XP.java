package seedu.address.model.achievement;

import static java.util.Objects.requireNonNull;

/**
 * Represents the experience point(xp) earned by the user while using the task manager.
 */
public class XP {
    
    public static final String MESSAGE_XP_CONSTRAINTS =
            "XP should have integer values.";
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
        this.xp = xp;
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
