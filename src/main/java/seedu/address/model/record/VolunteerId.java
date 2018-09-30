package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * This is just a placeholder class for development purposes.
 * Actual record VolunteerId should come from the volunteer package
 */
public class VolunteerId {
    public static final String MESSAGE_VOLUNTEERID_CONSTRAINTS = "VolunteerId can take in numerals only.";
    public static final String VOLUNTEERID_VALIDATION_REGEX = "\\p{Digit}+";

    public final String value;

    /**
     * Constructs a {@code VolunteerId}.
     *
     * @param id A valid hour.
     */
    public VolunteerId(String id) {
        requireNonNull(id);
        checkArgument(isValidVolunteerId(id), MESSAGE_VOLUNTEERID_CONSTRAINTS);
        this.value = id;
    }

    /**
     * Returns true if a given string is a valid volunteerId.
     */
    public static boolean isValidVolunteerId(String test) {
        return test.matches(VOLUNTEERID_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VolunteerId // instanceof handles nulls
                && value.equals(((VolunteerId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }
}
