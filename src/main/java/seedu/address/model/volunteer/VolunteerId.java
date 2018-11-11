package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents an Volunteer's Id in the application. Volunteer Id is used in identifying record entries.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class VolunteerId {
    public static final String MESSAGE_ID_CONSTRAINTS =
            "NRIC should only start with letters S, T, F or G, followed by 7 digits, ending with an alphabet,"
                    + "and it should not be blank";
    public static final String ID_VALIDATION_REGEX = "(?i)^[STFG]\\d{7}[A-Z]$(?-i)";

    public final String id;
    /**
     * Constructs an {@code id}.
     *
     * @param id A valid id.
     */
    public VolunteerId(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_ID_CONSTRAINTS);
        this.id = id.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid Id.
     */
    public static boolean isValidId(String test) {
        return test.matches(ID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VolunteerId // instanceof handles nulls
                && id.equals(((VolunteerId) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
