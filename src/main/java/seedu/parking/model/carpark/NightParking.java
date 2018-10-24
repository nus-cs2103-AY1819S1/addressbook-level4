package seedu.parking.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.parking.commons.util.AppUtil.checkArgument;

/**
 * Represents a car park's night parking.
 * Guarantees: immutable; is valid as declared in {@link #isValidNightPark(String)}
 */
public class NightParking {

    public static final String MESSAGE_NIGHT_PARK_CONSTRAINTS =
            "Car park night parking can take any values, and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NIGHT_PARK_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code NightParking}.
     *
     * @param nightPark A valid night parking.
     */
    public NightParking(String nightPark) {
        requireNonNull(nightPark);
        checkArgument(isValidNightPark(nightPark), MESSAGE_NIGHT_PARK_CONSTRAINTS);
        value = nightPark;
    }

    /**
     * Returns true if a given string is a valid night parking.
     */
    public static boolean isValidNightPark(String test) {
        return test.matches(NIGHT_PARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NightParking // instanceof handles nulls
                && value.equals(((NightParking) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
