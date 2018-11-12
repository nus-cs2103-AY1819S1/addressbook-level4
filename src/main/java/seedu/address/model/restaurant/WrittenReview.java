package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's writtenReview for a restaurant.
 */
public class WrittenReview {

    public static final String MESSAGE_REVIEW_CONSTRAINTS =
            "Written Reviews can take any values, and it should not be blank.";

    /*
     * The first character of the review must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String WRITTEN_REVIEW_VALIDATION_REGEX = "[^\\s].*";

    public final String writtenReview;

    /**
     * Constructs a {@code WrittenReview}.
     *
     * @param review a written review.
     */
    public WrittenReview(String review) {
        requireNonNull(review);
        checkArgument(isValidWrittenReview(review), MESSAGE_REVIEW_CONSTRAINTS);
        this.writtenReview = review;
    }

    /**
     * Returns true if a given string is a valid review.
     */
    public static boolean isValidWrittenReview(String test) {
        return test.matches(WRITTEN_REVIEW_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WrittenReview)) {
            return false;
        }

        return writtenReview.equals(((WrittenReview) other).writtenReview);
    }
}
