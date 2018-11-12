package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.UserReview;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly adapted version of the XmlAdaptedUserReview.
 */
public class XmlAdaptedUserReview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's Reviews's UserReview's "
            + "%s field is missing!";

    @XmlElement(required = true)
    private String rating;
    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String writtenReview;

    /**
     * Constructs a XmlAdaptedReviews.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUserReview() {}

    /**
     * Constructs a {@code XmlAdaptedUserReview} with the given details.
     */
    public XmlAdaptedUserReview(String rating, String username, String writtenReview) {
        this.rating = rating;
        this.username = username;
        this.writtenReview = writtenReview;
    }

    /**
     * Converts UserReview into this class for JAXB use.
     */
    public XmlAdaptedUserReview(UserReview userReview) {
        rating = String.valueOf(userReview.getRating());
        username = userReview.getUsername();
        writtenReview = userReview.getWrittenReview();
    }

    /**
     * Converts this jaxb-friendly adapted UserReview object into the model's UserReview object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted UserReview
     */
    public UserReview toModelType() throws IllegalValueException {
        // For Rating
        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        int savedRating;
        try {
            savedRating = Integer.parseInt(rating);
            if (!Rating.isValidRating(savedRating)) {
                throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
            }
        } catch (NumberFormatException e) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }

        final Rating modelRating = new Rating(savedRating);

        // For Username
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        // For WrittenReview
        if (writtenReview == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WrittenReview.class.getSimpleName()));
        }
        if (!WrittenReview.isValidWrittenReview(writtenReview)) {
            throw new IllegalValueException(WrittenReview.MESSAGE_REVIEW_CONSTRAINTS);
        }
        final WrittenReview modelWrittenReview = new WrittenReview(writtenReview);

        return new UserReview(modelUsername, modelRating, modelWrittenReview);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUserReview)) {
            return false;
        }

        return rating.equals(((XmlAdaptedUserReview) other).rating)
                && username.equals(((XmlAdaptedUserReview) other).username)
                && writtenReview.equals(((XmlAdaptedUserReview) other).writtenReview);
    }
}
