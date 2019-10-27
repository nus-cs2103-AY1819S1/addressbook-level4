package seedu.address.storage.entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalEntrys.PROJECT_ORBITALAPP;
import static seedu.address.testutil.TypicalEntrys.WORK_CAROUSELL;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EntryBook;
import seedu.address.model.ReadOnlyEntryBook;

public class XmlEntryBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlEntryBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEntryBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEntryBook(null);
    }

    private Optional<ReadOnlyEntryBook> readEntryBook(String filePath) throws Exception {
        return new XmlEntryBookStorage(Paths.get(filePath)).readEntryBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEntryBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEntryBook("NotXmlFormatEntryBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readEntryBook_invalidEntryEntryBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEntryBook("invalidEntryEntryBook.xml");
    }

    @Test
    public void readEntryBook_invalidAndValidEntryEntryBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEntryBook("invalidAndValidEntryEntryBook.xml");
    }

    @Test
    public void readAndSaveEntryBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempEntryBook.xml");
        EntryBook original = getTypicalEntryBook();
        XmlEntryBookStorage xmlEntryBookStorage = new XmlEntryBookStorage(filePath);

        //Save in new file and read back
        xmlEntryBookStorage.saveEntryBook(original, filePath);
        ReadOnlyEntryBook readBack = xmlEntryBookStorage.readEntryBook(filePath).get();
        assertEquals(original, new EntryBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addEntry(WORK_CAROUSELL);
        original.removeEntry(WORK_FACEBOOK);
        xmlEntryBookStorage.saveEntryBook(original, filePath);
        readBack = xmlEntryBookStorage.readEntryBook(filePath).get();
        assertEquals(original, new EntryBook(readBack));

        //Save and read without specifying file path
        original.addEntry(PROJECT_ORBITALAPP);
        xmlEntryBookStorage.saveEntryBook(original); //file path not specified
        readBack = xmlEntryBookStorage.readEntryBook().get(); //file path not specified
        assertEquals(original, new EntryBook(readBack));
    }

    @Test
    public void saveEntryBook_nullEntryBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEntryBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code entryBook} at the specified {@code filePath}.
     */
    private void saveEntryBook(ReadOnlyEntryBook entryBook, String filePath) {
        try {
            new XmlEntryBookStorage(Paths.get(filePath))
                    .saveEntryBook(entryBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEntryBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEntryBook(new EntryBook(), null);
    }


}
