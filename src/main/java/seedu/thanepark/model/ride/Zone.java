package seedu.thanepark.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.util.AppUtil.checkArgument;

/**
 * Represents a Ride's thanepark in the thanepark book.
 * Guarantees: immutable; is valid as declared in {@link #isValidZone(String)}
 */
public class Zone {

    public static final String MESSAGE_ZONE_CONSTRAINTS =
            "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the thanepark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ZONE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Zone}.
     *
     * @param zone A valid thanepark.
     */
    public Zone(String zone) {
        requireNonNull(zone);
        checkArgument(isValidZone(zone), MESSAGE_ZONE_CONSTRAINTS);
        value = zone;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidZone(String test) {
        return test.matches(ZONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Zone // instanceof handles nulls
                && value.equals(((Zone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
