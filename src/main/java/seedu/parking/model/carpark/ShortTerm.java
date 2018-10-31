package seedu.parking.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.parking.commons.util.AppUtil.checkArgument;

/**
 * Represents a car park's short term parking timing.
 * Guarantees: immutable; is valid as declared in {@link #isValidShortTerm(String)}
 */
public class ShortTerm {

    public static final String MESSAGE_SHORT_TERM_CONSTRAINTS =
            "Short term parking can take any values, and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String SHORT_TERM_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Constructs an {@code ShortTerm}.
     *
     * @param shortTerm A valid short term parking.
     */
    public ShortTerm(String shortTerm) {
        requireNonNull(shortTerm);
        checkArgument(isValidShortTerm(shortTerm), MESSAGE_SHORT_TERM_CONSTRAINTS);
        value = shortTerm;
    }

    /**
     * Returns true if a given string is a valid short term parking.
     */
    public static boolean isValidShortTerm(String test) {
        return test.matches(SHORT_TERM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShortTerm // instanceof handles nulls
                && value.equals(((ShortTerm) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
