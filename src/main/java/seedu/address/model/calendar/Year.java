package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author GilgameshTC
/**
 * Represents a year in the calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)};
 */
public class Year {

    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "Year should be a 4 digit positive integer, and it should not be blank";

    public static final String YEAR_VALIDATION_REGEX = "^\\d{4}$";

    public final String year;

    /**
     * Constructs a {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        this.year = year;
    }

    public Year() {
        // default create a 2018 year obj
        // For Json parsing, have to have a default constructor
        this("2018");
    }
    /**
     * Returns true if a given string is a valid year.
     */
    public static boolean isValidYear(String test) {
        return test.matches(YEAR_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return year;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && year.equals(((Year) other).year)); // state check
    }

    @Override
    public int hashCode() {
        return year.hashCode();
    }
}
