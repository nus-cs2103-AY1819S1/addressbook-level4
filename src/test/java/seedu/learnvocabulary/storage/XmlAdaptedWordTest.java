package seedu.learnvocabulary.storage;

import static org.junit.Assert.assertEquals;
import static seedu.learnvocabulary.storage.XmlAdaptedWord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.learnvocabulary.testutil.TypicalWords.DELIBERATE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.learnvocabulary.commons.exceptions.IllegalValueException;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.testutil.Assert;

public class XmlAdaptedWordTest {
    private static final String INVALID_NAME = "w@rd";
    private static final String INVALID_TAG = "#socool";

    private static final String VALID_NAME = DELIBERATE.getName().toString();
    private static final String VALID_MEANING = "Test";
    private static final List<XmlAdaptedTag> VALID_TAGS = DELIBERATE.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWordDetails_returnsWord() throws Exception {
        XmlAdaptedWord word = new XmlAdaptedWord(DELIBERATE);
        assertEquals(DELIBERATE, word.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedWord word =
                new XmlAdaptedWord(INVALID_NAME, VALID_MEANING, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, word::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedWord word = new XmlAdaptedWord(null, VALID_MEANING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, word::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedWord word =
                new XmlAdaptedWord(VALID_NAME, VALID_MEANING, invalidTags);
        Assert.assertThrows(IllegalValueException.class, word::toModelType);
    }

}
