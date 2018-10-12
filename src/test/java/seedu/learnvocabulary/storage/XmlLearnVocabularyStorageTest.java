package seedu.learnvocabulary.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.learnvocabulary.testutil.TypicalWords.ALICE;
import static seedu.learnvocabulary.testutil.TypicalWords.HOON;
import static seedu.learnvocabulary.testutil.TypicalWords.IDA;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;

public class XmlLearnVocabularyStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlLearnVocabularyStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readLearnVocabulary_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readLearnVocabulary(null);
    }

    private java.util.Optional<ReadOnlyLearnVocabulary> readLearnVocabulary(String filePath) throws Exception {
        return new XmlLearnVocabularyStorage(Paths.get(filePath))
                .readLearnVocabulary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLearnVocabulary("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readLearnVocabulary("NotXmlFormatLearnVocabulary.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readLearnVocabulary_invalidWordLearnVocabulary_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLearnVocabulary("invalidWordLearnVocabulary.xml");
    }

    @Test
    public void readLearnVocabulary_invalidAndValidWordLearnVocabulary_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLearnVocabulary("invalidAndValidWordLearnVocabulary.xml");
    }

    @Test
    public void readAndSaveLearnVocabulary_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempLearnVocabulary.xml");
        LearnVocabulary original = getTypicalLearnVocabulary();
        XmlLearnVocabularyStorage xmlLearnVocabularyStorage = new XmlLearnVocabularyStorage(filePath);

        //Save in new file and read back
        xmlLearnVocabularyStorage.saveLearnVocabulary(original, filePath);
        ReadOnlyLearnVocabulary readBack = xmlLearnVocabularyStorage.readLearnVocabulary(filePath).get();
        assertEquals(original, new LearnVocabulary(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addWord(HOON);
        original.removeWord(ALICE);
        xmlLearnVocabularyStorage.saveLearnVocabulary(original, filePath);
        readBack = xmlLearnVocabularyStorage.readLearnVocabulary(filePath).get();
        assertEquals(original, new LearnVocabulary(readBack));

        //Save and read without specifying file path
        original.addWord(IDA);
        xmlLearnVocabularyStorage.saveLearnVocabulary(original); //file path not specified
        readBack = xmlLearnVocabularyStorage.readLearnVocabulary().get(); //file path not specified
        assertEquals(original, new LearnVocabulary(readBack));

    }

    @Test
    public void saveLearnVocabulary_nullLearnVocabulary_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLearnVocabulary(null, "SomeFile.xml");
    }

    /**
     * Saves {@code learnVocabulary} at the specified {@code filePath}.
     */
    private void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary, String filePath) {
        try {
            new XmlLearnVocabularyStorage(Paths.get(filePath))
                    .saveLearnVocabulary(learnVocabulary, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLearnVocabulary_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLearnVocabulary(new LearnVocabulary(), null);
    }


}
