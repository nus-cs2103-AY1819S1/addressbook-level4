package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.HOON;
import static seedu.address.testutil.TypicalTasks.IDA;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySchedulePlanner;
import seedu.address.model.SchedulePlanner;

public class XmlSchedulePlannerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSchedulePlannerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlySchedulePlanner> readAddressBook(String filePath) throws Exception {
        return new XmlSchedulePlannerStorage(Paths.get(filePath)).
                readSchedulePlanner(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatSchedulePlanner.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidTaskSchedulePlanner.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidTaskSchedulePlanner.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        SchedulePlanner original = getTypicalAddressBook();
        XmlSchedulePlannerStorage xmlAddressBookStorage = new XmlSchedulePlannerStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveSchedulePlanner(original, filePath);
        ReadOnlySchedulePlanner readBack = xmlAddressBookStorage.readSchedulePlanner(filePath).get();
        assertEquals(original, new SchedulePlanner(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(HOON);
        original.removeTask(ALICE);
        xmlAddressBookStorage.saveSchedulePlanner(original, filePath);
        readBack = xmlAddressBookStorage.readSchedulePlanner(filePath).get();
        assertEquals(original, new SchedulePlanner(readBack));

        //Save and read without specifying file path
        original.addTask(IDA);
        xmlAddressBookStorage.saveSchedulePlanner(original); //file path not specified
        readBack = xmlAddressBookStorage.readSchedulePlanner().get(); //file path not specified
        assertEquals(original, new SchedulePlanner(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlySchedulePlanner addressBook, String filePath) {
        try {
            new XmlSchedulePlannerStorage(Paths.get(filePath))
                    .saveSchedulePlanner(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new SchedulePlanner(), null);
    }


}
