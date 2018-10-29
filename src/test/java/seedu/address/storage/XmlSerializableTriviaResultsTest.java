package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.test.TriviaResults;
import seedu.address.testutil.TypicalTriviaResults;

public class XmlSerializableTriviaResultsTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTriviaResultsTest");
    private static final Path TYPICAL_RESULTS_FILE = TEST_DATA_FOLDER.resolve("typicalResultsTriviaResults.xml");
    private static final Path INVALID_RESULT_FILE = TEST_DATA_FOLDER.resolve("invalidResultTriviaResults.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalResultsFile_success() throws Exception {
        XmlSerializableTriviaResults dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RESULTS_FILE,
                XmlSerializableTriviaResults.class);
        TriviaResults triviaBundleFromFile = dataFromFile.toModelType();
        TriviaResults typicalCardsTriviaBundle = TypicalTriviaResults.getTypicalTriviaResults();
        assertEquals(triviaBundleFromFile, typicalCardsTriviaBundle);
    }

    @Test
    public void toModelType_invalidResultFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTriviaResults dataFromFile = XmlUtil.getDataFromFile(INVALID_RESULT_FILE,
                XmlSerializableTriviaResults.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
