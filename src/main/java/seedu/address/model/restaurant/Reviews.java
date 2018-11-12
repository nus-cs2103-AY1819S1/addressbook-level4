package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Restaurant's reviews in the address book.
 */
public class Reviews {
    public static final String MESSAGE_OVERALL_RATING_CONSTRAINTS =
            "Overall Rating must be a Double in two Decimal Places from 1.00 to 5.00 with the exception of 0.00.";

    private static DecimalFormat df = new DecimalFormat("#.00");
    private List<UserReview> userReviewList;
    private double totalRatings;
    private String restaurantRatingValue;

    /**
     * Constructs {@code Reviews}.
     */
    public Reviews() {
        userReviewList = new ArrayList<>();
        totalRatings = 0.00;
        this.restaurantRatingValue = "0.00";
    }

    /**
     * Constructs {@code Reviews}.
     *
     * @param restaurantRatingValue The overall rating of a restaurant.
     * @param totalRatings The total ratings a restaurant has received.
     * @param userReviewList A list of UserReview.
     */
    public Reviews(String restaurantRatingValue, double totalRatings, List<UserReview> userReviewList) {
        requireNonNull(restaurantRatingValue);
        checkArgument(isValidReviewsRating(restaurantRatingValue), MESSAGE_OVERALL_RATING_CONSTRAINTS);
        this.userReviewList = userReviewList;
        this.totalRatings = totalRatings;
        if (restaurantRatingValue.equals("0.00")) {
            this.totalRatings = 0.00;
            this.restaurantRatingValue = "0.00";
        } else {
            this.totalRatings = totalRatings;
            double average = totalRatings / userReviewList.size();
            this.restaurantRatingValue = df.format(average);
        }
    }

    /**
     * Used for Tests, Checks if the restaurantRating in Reviews is valid.
     *
     * @param test The overall rating of a restaurant to be checked.
     */
    public static boolean isValidReviewsRating(String test) {
        try {
            double testRating = Double.parseDouble(test);
            if (testRating < 0.0 || testRating > 5.0 || (testRating < 1.0) && (testRating > 0.0)) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getRestaurantRatingValue() {
        return restaurantRatingValue;
    }

    public String getRestaurantTotalRatings() {
        return Double.toString(totalRatings);
    }

    public List<UserReview> getUserReviewList() {
        return userReviewList;
    }

    /**
     * Adds a User Review to the Reviews Object of a restaurant.
     *
     * The restaurantRatingValue of a restaurant is the average rating of a restaurant and
     * is updated each time a {@code userReview} is added.
     */
    public Reviews addUserReview(UserReview userReview) {
        userReviewList.add(userReview);
        if (userReviewList.size() == 1) {
            totalRatings = (double) userReview.getRating();
            this.restaurantRatingValue = df.format(totalRatings);
            return this;
        }
        totalRatings = totalRatings + (double) userReview.getRating();
        Double tempCalculation = totalRatings / userReviewList.size();
        this.restaurantRatingValue = df.format(tempCalculation);
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reviews)) {
            return false;
        }

        return restaurantRatingValue.equals(((Reviews) other).restaurantRatingValue)
                && userReviewList.equals(((Reviews) other).userReviewList);
    }
}
