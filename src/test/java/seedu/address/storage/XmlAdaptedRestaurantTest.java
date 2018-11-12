package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedRestaurant.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_A;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedRestaurantTest {
    private static final String INVALID_NAME = "Hw@ngs";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = RESTAURANT_A.getName().toString();
    private static final String VALID_PHONE = RESTAURANT_A.getPhone().toString();
    private static final String VALID_ADDRESS = RESTAURANT_A.getAddress().toString();
    private static final XmlAdaptedReviews VALID_REVIEWS = new XmlAdaptedReviews(RESTAURANT_A.getReviews());
    private static final List<XmlAdaptedTag> VALID_TAGS = RESTAURANT_A.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRestaurantDetails_returnsRestaurant() throws Exception {
        XmlAdaptedRestaurant restaurant = new XmlAdaptedRestaurant(RESTAURANT_A);
        assertEquals(RESTAURANT_A, restaurant.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRestaurant restaurant =
                new XmlAdaptedRestaurant(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_TAGS, VALID_REVIEWS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRestaurant restaurant = new XmlAdaptedRestaurant(
                null, VALID_PHONE, VALID_ADDRESS, VALID_TAGS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedRestaurant restaurant =
                new XmlAdaptedRestaurant(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_TAGS, VALID_REVIEWS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedRestaurant restaurant = new XmlAdaptedRestaurant(
                VALID_NAME, null, VALID_ADDRESS, VALID_TAGS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedRestaurant restaurant =
                new XmlAdaptedRestaurant(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_TAGS, VALID_REVIEWS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedRestaurant restaurant = new XmlAdaptedRestaurant(
                VALID_NAME, VALID_PHONE, null, VALID_TAGS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRestaurant restaurant =
                new XmlAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_ADDRESS, invalidTags, VALID_REVIEWS);
        Assert.assertThrows(IllegalValueException.class, restaurant::toModelType);
    }

}
