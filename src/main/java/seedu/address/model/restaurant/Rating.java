package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's Rating of a restaurant in a User Review.
 */
public class Rating {

    public static final String MESSAGE_RATING_CONSTRAINTS =
            "Rating must be a positive integer from 1 to 5 where 1 is the lowest rating and 5, the highest rating.";

    public final int rating;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(int rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        this.rating = rating;
    }

    /**
     * Returns true if a rating is a valid rating.
     */
    public static boolean isValidRating(int test) {
        if (test < 1 || test > 5) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rating)) {
            return false;
        }

        return rating == ((Rating) other).rating;
    }
}
