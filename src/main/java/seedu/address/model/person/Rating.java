package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rating number in SSENISUB.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {

    public static final Rating DEFAULT_INITIAL_RATING = new Rating("0");
    public static final String MESSAGE_CONSTRAINTS =
            "Ratings should only contain numbers, and it should be between 1 to 10";
    public static final String VALIDATION_REGEX = "[0-9]|10";
    public static final String VALIDATION_INPUT_REGEX = "[1-9]|10";
    public final String value;


    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_CONSTRAINTS);
        value = rating;
    }

    /**
     * Returns true if a given string is a valid rating number.
     */
    public static boolean isValidRating(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid rating number.
     */
    public static boolean isValidInputRating(String test) {
        return test.matches(VALIDATION_INPUT_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && value.equals(((Rating) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
