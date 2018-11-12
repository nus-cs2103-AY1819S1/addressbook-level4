package seedu.address.model.restaurant;

import seedu.address.model.user.Username;

/**
 * Represents a User's review for a restaurant in the address book.
 */
public class UserReview {

    private Rating rating;
    private Username username;
    private WrittenReview review;

    /**
     * Constructs {@code UserReview}.
     *
     * @param username A valid Username.
     * @param rating A valid Rating.
     * @param review A valid WrittenReview.
     */
    public UserReview(Username username, Rating rating, WrittenReview review) {
        this.username = username;
        this.rating = rating;
        this.review = review;
    }

    public int getRating() {
        return rating.rating;
    }

    public String getUsername() {
        return username.username;
    }

    public String getWrittenReview() {
        return review.writtenReview;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserReview)) {
            return false;
        }

        return rating.equals(((UserReview) other).rating)
                && username.equals(((UserReview) other).username)
                && review.equals(((UserReview) other).review);
    }
}
