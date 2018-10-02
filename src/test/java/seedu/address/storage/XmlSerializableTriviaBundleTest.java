package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.TriviaBundle;
import seedu.address.testutil.TypicalCards;

public class XmlSerializableTriviaBundleTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTriviaBundleTest");
    private static final Path TYPICAL_CARDS_FILE = TEST_DATA_FOLDER.resolve("typicalCardsTriviaBundle.xml");
    private static final Path INVALID_CARD_FILE = TEST_DATA_FOLDER.resolve("invalidCardTriviaBundle.xml");
    private static final Path DUPLICATE_CARD_FILE = TEST_DATA_FOLDER.resolve("duplicateCardTriviaBundle.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCardsFile_success() throws Exception {
        XmlSerializableTriviaBundle dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CARDS_FILE,
                XmlSerializableTriviaBundle.class);
        TriviaBundle TriviaBundleFromFile = dataFromFile.toModelType();
        TriviaBundle typicalCardsTriviaBundle = TypicalCards.getTypicalTriviaBundle();
        assertEquals(TriviaBundleFromFile, typicalCardsTriviaBundle);
    }

    @Test
    public void toModelType_invalidCardFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTriviaBundle dataFromFile = XmlUtil.getDataFromFile(INVALID_CARD_FILE,
                XmlSerializableTriviaBundle.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableTriviaBundle dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_CARD_FILE,
                XmlSerializableTriviaBundle.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableTriviaBundle.MESSAGE_DUPLICATE_CARD);
        dataFromFile.toModelType();
    }
}
