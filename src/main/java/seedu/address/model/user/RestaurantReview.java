package seedu.address.model.user;

import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.WrittenReview;

/**
 * Represents a Restaurant's review written by a user in the address book.
 */
public class RestaurantReview {

    private Name restaurantName;
    private Rating rating;
    private WrittenReview review;

    /**
     * Construcrs a {@code RestaurantReview}.
     *
     * @param restaurantName a valid restaurant name.
     * @param rating a valid rating.
     * @param review a written review.
     */
    public RestaurantReview(Name restaurantName, Rating rating, WrittenReview review) {
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.review = review;
    }

    public String getRestaurantName() {
        return restaurantName.fullName;
    }

    public int getRating() {
        return rating.rating;
    }

    public String getWrittenReview() {
        return review.writtenReview;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RestaurantReview)) {
            return false;
        }

        RestaurantReview otherRr = (RestaurantReview) other;
        return getRestaurantName().equals(otherRr.getRestaurantName())
                && getRating() == otherRr.getRating()
                && getWrittenReview().equals(otherRr.getWrittenReview());
    }
}
