package seedu.learnvocabulary.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.learnvocabulary.commons.exceptions.IllegalValueException;
import seedu.learnvocabulary.commons.util.XmlUtil;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.testutil.TypicalWords;

public class XmlSerializableLearnVocabularyTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableLearnVocabularyTest");
    private static final Path TYPICAL_WORD_FILE = TEST_DATA_FOLDER.resolve("typicalWordsLearnVocabulary.xml");
    private static final Path INVALID_WORD_FILE = TEST_DATA_FOLDER.resolve("invalidWordLearnVocabulary.xml");
    private static final Path DUPLICATE_WORD_FILE = TEST_DATA_FOLDER.resolve("duplicateWordLearnVocabulary.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalWordsFile_success() throws Exception {
        XmlSerializableLearnVocabulary dataFromFile = XmlUtil.getDataFromFile(TYPICAL_WORD_FILE,
                XmlSerializableLearnVocabulary.class);
        LearnVocabulary learnVocabularyFromFile = dataFromFile.toModelType();
        LearnVocabulary typicalWordsLearnVocabulary = TypicalWords.getTypicalLearnVocabulary();
        assertEquals(learnVocabularyFromFile, typicalWordsLearnVocabulary);
    }

    @Test
    public void toModelType_invalidWordFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLearnVocabulary dataFromFile = XmlUtil.getDataFromFile(INVALID_WORD_FILE,
                XmlSerializableLearnVocabulary.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateWords_throwsIllegalValueException() throws Exception {
        XmlSerializableLearnVocabulary dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_WORD_FILE,
                XmlSerializableLearnVocabulary.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableLearnVocabulary.MESSAGE_DUPLICATE_WORD);
        dataFromFile.toModelType();
    }

}


