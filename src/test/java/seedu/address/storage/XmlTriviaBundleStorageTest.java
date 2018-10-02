package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;

public class XmlTriviaBundleStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlTriviaBundleStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTriviaBundle_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTriviaBundle(null);
    }

    private Optional<ReadOnlyTriviaBundle> readTriviaBundle(String filePath) throws Exception {
        return new XmlTriviaBundleStorage(Paths.get(filePath)).readTriviaBundle(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTriviaBundle("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTriviaBundle("NotXmlFormatTriviaBundle.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readTriviaBundle_invalidCardTriviaBundle_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTriviaBundle("invalidCardTriviaBundle.xml");
    }

    @Test
    public void readTriviaBundle_invalidAndValidCardTriviaBundle_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTriviaBundle("invalidAndValidCardTriviaBundle.xml");
    }

    @Test
    public void readAndSaveTriviaBundle_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTriviaBundle.xml");
        TriviaBundle original = getTypicalTriviaBundle();
        XmlTriviaBundleStorage xmlTriviaBundleStorage = new XmlTriviaBundleStorage(filePath);

        // Save in new file and read back
        xmlTriviaBundleStorage.saveTriviaBundle(original, filePath);
        ReadOnlyTriviaBundle readBack = xmlTriviaBundleStorage.readTriviaBundle(filePath).get();
        assertEquals(original, new TriviaBundle(readBack));

        // TODO To enable this after delete command is implemented on trivia.
        // Modify data, overwrite exiting file, and read back
        //        original.addCard(Q_TALLEST_BUILDING);
        //        original.removeCard(Q_DENISTY_FORMULA);
        //        xmlTriviaBundleStorage.saveTriviaBundle(original, filePath);
        //        readBack = xmlTriviaBundleStorage.readTriviaBundle(filePath).get();
        //        assertEquals(original, new TriviaBundle(readBack));

        // Save and read without specifying file path
        original.addCard(Q_FLAT_EARTH);
        xmlTriviaBundleStorage.saveTriviaBundle(original); //file path not specified
        readBack = xmlTriviaBundleStorage.readTriviaBundle().get(); //file path not specified
        assertEquals(original, new TriviaBundle(readBack));

    }

    @Test
    public void saveTriviaBundle_nullTriviaBundle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTriviaBundle(null, "SomeFile.xml");
    }

    /**
     * Saves {@code trivia bundle} at the specified {@code filePath}.
     */
    private void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle, String filePath) {
        try {
            new XmlTriviaBundleStorage(Paths.get(filePath))
                    .saveTriviaBundle(triviaBundle, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTriviaBundle_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTriviaBundle(new TriviaBundle(), null);
    }

}
