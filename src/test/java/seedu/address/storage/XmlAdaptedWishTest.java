package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedWish.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalWishes.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedWishTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getUrl().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWishDetails_returnsWish() throws Exception {
        XmlAdaptedWish wish = new XmlAdaptedWish(BENSON);
        assertEquals(BENSON, wish.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Url.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedWish wish = new XmlAdaptedWish(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, wish::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedWish wish =
                new XmlAdaptedWish(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, wish::toModelType);
    }

}
