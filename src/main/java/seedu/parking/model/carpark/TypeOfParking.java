package seedu.parking.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.parking.commons.util.AppUtil.checkArgument;

/**
 * Represents the type of parking system for a car park.
 * Guarantees: immutable; is valid as declared in {@link #isValidTypePark(String)}
 */
public class TypeOfParking {

    public static final String MESSAGE_TYPE_PARK_CONSTRAINTS =
            "Type of parking system can take any values, and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TYPE_PARK_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Constructs an {@code TypeOfParking}.
     *
     * @param typePark A valid type of parking.
     */
    public TypeOfParking(String typePark) {
        requireNonNull(typePark);
        checkArgument(isValidTypePark(typePark), MESSAGE_TYPE_PARK_CONSTRAINTS);
        value = typePark;
    }

    /**
     * Returns true if a given string is a valid type of parking.
     */
    public static boolean isValidTypePark(String test) {
        return test.matches(TYPE_PARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TypeOfParking // instanceof handles nulls
                && value.equals(((TypeOfParking) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
