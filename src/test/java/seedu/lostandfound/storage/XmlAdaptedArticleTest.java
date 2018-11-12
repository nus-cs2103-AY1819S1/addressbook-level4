package seedu.lostandfound.storage;

import static org.junit.Assert.assertEquals;
import static seedu.lostandfound.storage.XmlAdaptedArticle.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.lostandfound.testutil.TypicalArticles.WALLET;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.lostandfound.commons.exceptions.IllegalValueException;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.testutil.Assert;

public class XmlAdaptedArticleTest {
    private static final String INVALID_NAME = "W@llet";
    private static final String INVALID_FINDER = "J*ames";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = WALLET.getName().toString();
    private static final String VALID_FINDER = WALLET.getFinder().toString();
    private static final String VALID_PHONE = WALLET.getPhone().toString();
    private static final String VALID_EMAIL = WALLET.getEmail().toString();
    private static final String VALID_DESCRIPTION = WALLET.getDescription().toString();
    private static final String VALID_IMAGE = WALLET.getImage().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = WALLET.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    private static final boolean FALSE_ISRESOLVED = false;
    private static final String DEFAULT_OWNER = "Not Claimed";

    @Test
    public void toModelType_validArticleDetails_returnsArticle() throws Exception {
        XmlAdaptedArticle article = new XmlAdaptedArticle(WALLET);
        assertEquals(WALLET, article.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedArticle article =
                new XmlAdaptedArticle(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION,
                        VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedArticle article = new XmlAdaptedArticle(
                null, VALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION, VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER,
                FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedArticle article =
                new XmlAdaptedArticle(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION,
                        VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedArticle article = new XmlAdaptedArticle(VALID_NAME, null, VALID_EMAIL, VALID_DESCRIPTION,
                VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedArticle article =
                new XmlAdaptedArticle(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_DESCRIPTION,
                        VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedArticle article = new XmlAdaptedArticle(VALID_NAME, VALID_PHONE, null, VALID_DESCRIPTION,
                VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedArticle article =
                new XmlAdaptedArticle(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_DESCRIPTION,
                        VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedArticle article = new XmlAdaptedArticle(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, article::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedArticle article =
                new XmlAdaptedArticle(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION,
                        VALID_IMAGE, VALID_FINDER, DEFAULT_OWNER, FALSE_ISRESOLVED, invalidTags);
        Assert.assertThrows(IllegalValueException.class, article::toModelType);
    }

}
