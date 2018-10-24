package seedu.parking.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.parking.commons.util.AppUtil.checkArgument;

/**
 * Represents a car park's free parking.
 * Guarantees: immutable; is valid as declared in {@link #isValidFreePark(String)}
 */
public class FreeParking {

    public static final String MESSAGE_FREE_PARK_CONSTRAINTS =
            "Car park free parking can take any values, and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FREE_PARK_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code FreeParking}.
     *
     * @param freePark A valid free parking.
     */
    public FreeParking(String freePark) {
        requireNonNull(freePark);
        checkArgument(isValidFreePark(freePark), MESSAGE_FREE_PARK_CONSTRAINTS);
        value = freePark;
    }

    /**
     * Returns true if a given string is a valid free parking.
     */
    public static boolean isValidFreePark(String test) {
        return test.matches(FREE_PARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FreeParking // instanceof handles nulls
                && value.equals(((FreeParking) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
