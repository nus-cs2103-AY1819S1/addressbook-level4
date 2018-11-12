package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAA_COW_REVIEWS;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Reviews;
import seedu.address.testutil.Assert;

public class XmlAdaptedReviewsTest {
    private static final String INVALID_RATING = "5.78";

    private static final List<XmlAdaptedUserReview> VALID_USER_REVIEWS_LIST =
            VALID_WAA_COW_REVIEWS.getUserReviewList().stream()
                    .map(XmlAdaptedUserReview::new)
                    .collect(Collectors.toList());

    @Test
    public void toModelType_validReviews() throws Exception {
        XmlAdaptedReviews reviews = new XmlAdaptedReviews(VALID_WAA_COW_REVIEWS);
        assertEquals(VALID_WAA_COW_REVIEWS, reviews.toModelType());
    }

    @Test
    public void toModelType_invalidRating() {
        XmlAdaptedReviews reviews = new XmlAdaptedReviews(INVALID_RATING, "3.0", VALID_USER_REVIEWS_LIST);
        String expectedMessage = Reviews.MESSAGE_OVERALL_RATING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reviews::toModelType);
    }
}
