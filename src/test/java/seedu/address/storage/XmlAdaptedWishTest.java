package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedWish.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalWishes.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wish.Date;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Url;
import seedu.address.testutil.Assert;

public class XmlAdaptedWishTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRICE = "2.,asdaxczxc.231";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE = "222/1/2019";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ID = "6b46cf8e-adf5-4c39-8885-0a3131a80c9ezzzz";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PRICE = BENSON.getPrice().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final String VALID_ADDRESS = BENSON.getUrl().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_ID = "6b46cf8e-adf5-4c39-8885-0a3131a80c9e";

    @Test
    public void toModelType_validWishDetails_returnsWish() throws Exception {
        XmlAdaptedWish wish = new XmlAdaptedWish(BENSON);
        assertEquals(BENSON, wish.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(INVALID_NAME, VALID_PRICE, VALID_DATE, VALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(null, VALID_PRICE, VALID_DATE, VALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, INVALID_PRICE, VALID_DATE, VALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(VALID_NAME, null, VALID_DATE, VALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, VALID_PRICE, INVALID_DATE, VALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(VALID_NAME, VALID_PRICE, null, VALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, VALID_PRICE, VALID_DATE, INVALID_ADDRESS, VALID_TAGS, VALID_ID);
        String expectedMessage = Url.MESSAGE_URL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(VALID_NAME, VALID_PRICE, VALID_DATE, null, VALID_TAGS, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, VALID_PRICE, VALID_DATE, VALID_ADDRESS, invalidTags, VALID_ID);
        Assert.assertThrows(IllegalValueException.class, wish::toModelType);
    }

}
