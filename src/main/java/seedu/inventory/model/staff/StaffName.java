package seedu.inventory.model.staff;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Staff's name in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class StaffName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*$";

    public final String fullName;

    /**
     * Constructs a {@code StaffName}.
     *
     * @param name A valid name.
     */
    public StaffName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String name) {
        return name.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StaffName)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        return fullName.equals(((StaffName) other).fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
