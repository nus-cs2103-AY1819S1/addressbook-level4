package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USER_REVIEW;
import static seedu.address.storage.XmlAdaptedUserReview.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.Username;
import seedu.address.testutil.Assert;

public class XmlAdaptedUserReviewTest {
    private static final String INVALID_RATING = "50";
    private static final String INVALID_USERNAME = " ";
    private static final String INVALID_WRITTEN_REVIEW = " ";

    private static final String VALID_RATING = Integer.toString(VALID_USER_REVIEW.getRating());
    private static final String VALID_USERNAME = VALID_USER_REVIEW.getUsername();
    private static final String VALID_WRITTEN_REVIEW = VALID_USER_REVIEW.getWrittenReview();

    @Test
    public void toModelType_validUserReviews() throws Exception {
        XmlAdaptedUserReview userReview = new XmlAdaptedUserReview(VALID_USER_REVIEW);
        assertEquals(VALID_USER_REVIEW, userReview.toModelType());
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        XmlAdaptedUserReview userReview =
                new XmlAdaptedUserReview(INVALID_RATING, VALID_USERNAME, VALID_WRITTEN_REVIEW);
        String expectedMessage = Rating.MESSAGE_RATING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userReview::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        XmlAdaptedUserReview userReview = new XmlAdaptedUserReview(
                null, VALID_USERNAME, VALID_WRITTEN_REVIEW);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userReview::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        XmlAdaptedUserReview userReview =
                new XmlAdaptedUserReview(VALID_RATING, INVALID_USERNAME, VALID_WRITTEN_REVIEW);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userReview::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        XmlAdaptedUserReview userReview = new XmlAdaptedUserReview(
                VALID_RATING, null, VALID_WRITTEN_REVIEW);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userReview::toModelType);
    }

    @Test
    public void toModelType_invalidWrittenReview_throwsIllegalValueException() {
        XmlAdaptedUserReview userReview =
                new XmlAdaptedUserReview(VALID_RATING, VALID_USERNAME, INVALID_WRITTEN_REVIEW);
        String expectedMessage = WrittenReview.MESSAGE_REVIEW_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userReview::toModelType);
    }

    @Test
    public void toModelType_nullWrittenReview_throwsIllegalValueException() {
        XmlAdaptedUserReview userReview = new XmlAdaptedUserReview(
                VALID_RATING, VALID_USERNAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WrittenReview.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, userReview::toModelType);
    }
}
