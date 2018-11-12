package seedu.address.model.restaurant;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.model.user.Username;
import seedu.address.testutil.Assert;

public class ReviewsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Reviews(null, 5.0, null));
        Assert.assertThrows(NullPointerException.class, () -> new Reviews(null, 5.0, new ArrayList<>()));
    }

    @Test
    public void addUserReview_withValidInputs() {
        Reviews reviews = new Reviews();
        assertTrue(reviews.getRestaurantRatingValue().equals("0.00"));

        UserReview userReview1 = new UserReview(new Username("Test"), new Rating(4), new WrittenReview("Test"));
        reviews.addUserReview(userReview1);
        assertTrue(reviews.getRestaurantRatingValue().equals("4.00")); // Restaurant Rating after first review

        UserReview userReview2 = new UserReview(new Username("Test"), new Rating(5), new WrittenReview("Test"));
        UserReview userReview3 = new UserReview(new Username("Test"), new Rating(5), new WrittenReview("Test"));
        UserReview userReview4 = new UserReview(new Username("Test"), new Rating(5), new WrittenReview("Test"));
        reviews.addUserReview(userReview2);
        reviews.addUserReview(userReview3);
        reviews.addUserReview(userReview4);
        assertTrue(reviews.getRestaurantRatingValue().equals("4.75")); // Restaurant Rating after more reviews

        UserReview userReview5 = new UserReview(new Username("Test"), new Rating(1), new WrittenReview("Test"));
        UserReview userReview6 = new UserReview(new Username("Test"), new Rating(3), new WrittenReview("Test"));
        UserReview userReview7 = new UserReview(new Username("Test"), new Rating(1), new WrittenReview("Test"));
        reviews.addUserReview(userReview5);
        reviews.addUserReview(userReview6);
        reviews.addUserReview(userReview7);
        assertTrue(reviews.getRestaurantRatingValue().equals("3.43")); // Restaurant Rating with many decimal places.
    }

    @Test
    public void checkNotEmpty() {
        Reviews reviews = new Reviews();
        assertNotNull(reviews.getRestaurantRatingValue());
        assertNotNull(reviews.getUserReviewList());
    }
}
