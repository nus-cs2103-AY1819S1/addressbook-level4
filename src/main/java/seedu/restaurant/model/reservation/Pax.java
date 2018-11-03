package seedu.restaurant.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

//@@author m4dkip
/**
 * Represents a Reservation's Pax value in the restaurant book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPax(String)}
 */
public class Pax {


    public static final String MESSAGE_PAX_CONSTRAINTS =
            "Pax value should only contain numbers, and it should be at least 1 digit long";
    public static final String PAX_VALIDATION_REGEX = "\\d{1,}";
    private final String value;

    /**
     * Constructs a {@code Pax}.
     *
     * @param pax A valid pax value.
     */
    public Pax(String pax) {
        requireNonNull(pax);
        checkArgument(isValidPax(pax), MESSAGE_PAX_CONSTRAINTS);
        value = pax;
    }

    /**
     * Returns true if a given string is a valid pax number.
     */
    public static boolean isValidPax(String test) {
        return test.matches(PAX_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Pax // instanceof handles nulls
                    && value.equals(((Pax) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
