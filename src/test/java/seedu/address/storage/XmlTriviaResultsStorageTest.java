package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTriviaResults.RESULT_FLAT_EARTH_WRONG;
import static seedu.address.testutil.TypicalTriviaResults.RESULT_FORCES_WRONG;
import static seedu.address.testutil.TypicalTriviaResults.getTypicalTriviaResults;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TriviaResults;

public class XmlTriviaResultsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlTriviaResultsStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Test
    public void readTriviaBundle_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTriviaResults(null);
    }

    private Optional<ReadOnlyTriviaResults> readTriviaResults(String filePath) throws Exception {
        return new XmlTriviaResultsStorage(Paths.get(filePath)).readTriviaResults(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTriviaResults("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTriviaResults("NotXmlFormatTriviaResults.xml");
    }

    @Test
    public void readTriviaResults_invalidResult_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTriviaResults("invalidResultTriviaResults.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidResultTriviaResults_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTriviaResults("invalidAndValidResultTriviaResults.xml");
    }

    @Test
    public void readAndSaveTriviaResults_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTriviaResults.xml");
        TriviaResults original = getTypicalTriviaResults();
        XmlTriviaResultsStorage xmlTriviaResultsStorage = new XmlTriviaResultsStorage(filePath);

        //Save in new file and read back
        xmlTriviaResultsStorage.saveTriviaResults(original, filePath);
        ReadOnlyTriviaResults readBack = xmlTriviaResultsStorage.readTriviaResults(filePath).get();
        assertEquals(original, new TriviaResults(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTriviaResult(RESULT_FORCES_WRONG);
        xmlTriviaResultsStorage.saveTriviaResults(original, filePath);
        readBack = xmlTriviaResultsStorage.readTriviaResults(filePath).get();
        assertEquals(original, new TriviaResults(readBack));

        //Save and read without specifying file path
        original.addTriviaResult(RESULT_FLAT_EARTH_WRONG);
        xmlTriviaResultsStorage.saveTriviaResults(original); //file path not specified
        readBack = xmlTriviaResultsStorage.readTriviaResults().get(); //file path not specified
        assertEquals(original, new TriviaResults(readBack));

    }

    @Test
    public void saveTriviaResults_nullTriviaResults_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTriviaResults(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveTriviaResults(ReadOnlyTriviaResults triviaResults, String filePath) {
        try {
            new XmlTriviaResultsStorage(Paths.get(filePath))
                    .saveTriviaResults(triviaResults, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTriviaResults(new TriviaResults(), null);
    }
}
