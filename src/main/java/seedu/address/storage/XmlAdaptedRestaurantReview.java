package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.RestaurantReview;

/**
 * JAXB-friendly adapted version of the XmlAdaptedRestaurantReview.
 */
public class XmlAdaptedRestaurantReview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's Reviews's "
            + "%s field is missing!";

    @XmlElement(required = true)
    private String rating;
    @XmlElement(required = true)
    private String restaurantName;
    @XmlElement(required = true)
    private String writtenReview;

    /**
     * Constructs a XmlAdaptedReviews.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRestaurantReview() {}

    /**
     * Constructs a {@code XmlAdaptedRestaurantReview} with the given details.
     */
    public XmlAdaptedRestaurantReview(String rating, String restaurantName, String writtenReview) {
        this.rating = rating;
        this.restaurantName = restaurantName;
        this.writtenReview = writtenReview;
    }

    /**
     * Converts RestaurantReview into this class for JAXB use.
     */
    public XmlAdaptedRestaurantReview(RestaurantReview restaurantReview) {
        rating = String.valueOf(restaurantReview.getRating());
        restaurantName = restaurantReview.getRestaurantName();
        writtenReview = restaurantReview.getWrittenReview();
    }

    /**
     * Converts this jaxb-friendly adapted RestaurantReview object into the model's RestaurantReview object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted RestaurantReview
     */
    public RestaurantReview toModelType() throws IllegalValueException {
        int savedRating = Integer.parseInt(rating);
        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(savedRating)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        final Rating modelRating = new Rating(savedRating);

        if (restaurantName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(restaurantName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelRestaurantName = new Name(restaurantName);

        if (writtenReview == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WrittenReview.class.getSimpleName()));
        }
        final WrittenReview modelWrittenReview = new WrittenReview(writtenReview);

        return new RestaurantReview(modelRestaurantName, modelRating, modelWrittenReview);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRestaurantReview)) {
            return false;
        }

        return rating.equals(((XmlAdaptedRestaurantReview) other).rating)
                && restaurantName.equals(((XmlAdaptedRestaurantReview) other).restaurantName)
                && writtenReview.equals(((XmlAdaptedRestaurantReview) other).writtenReview);
    }
}
