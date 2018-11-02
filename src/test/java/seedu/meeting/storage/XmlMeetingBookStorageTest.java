package seedu.meeting.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;
import static seedu.meeting.testutil.TypicalPersons.BENSON;
import static seedu.meeting.testutil.TypicalPersons.HOON;
import static seedu.meeting.testutil.TypicalPersons.IDA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.ReadOnlyMeetingBook;

public class XmlMeetingBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlMeetingBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readMeetingBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readMeetingBook(null);
    }

    private java.util.Optional<ReadOnlyMeetingBook> readMeetingBook(String filePath) throws Exception {
        return new XmlMeetingBookStorage(Paths.get(filePath)).readMeetingBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMeetingBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readMeetingBook("NotXmlFormatMeetingBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readMeetingBook_invalidPersonMeetingBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readMeetingBook("invalidPersonMeetingBook.xml");
    }

    @Test
    public void readMeetingBook_invalidAndValidPersonMeetingBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readMeetingBook("invalidAndValidPersonMeetingBook.xml");
    }

    @Test
    public void readAndSaveMeetingBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempMeetingBook.xml");
        MeetingBook original = getTypicalMeetingBook();
        XmlMeetingBookStorage xmlMeetingBookStorage = new XmlMeetingBookStorage(filePath);

        //Save in new file and read back
        xmlMeetingBookStorage.saveMeetingBook(original, filePath);
        ReadOnlyMeetingBook readBack = xmlMeetingBookStorage.readMeetingBook(filePath).get();
        assertEquals(original, new MeetingBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(BENSON);
        xmlMeetingBookStorage.saveMeetingBook(original, filePath);
        readBack = xmlMeetingBookStorage.readMeetingBook(filePath).get();
        assertEquals(original, new MeetingBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlMeetingBookStorage.saveMeetingBook(original); //file path not specified
        readBack = xmlMeetingBookStorage.readMeetingBook().get(); //file path not specified
        assertEquals(original, new MeetingBook(readBack));

    }

    @Test
    public void saveMeetingBook_nullMeetingBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveMeetingBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code meetingBook} at the specified {@code filePath}.
     */
    private void saveMeetingBook(ReadOnlyMeetingBook meetingBook, String filePath) {
        try {
            new XmlMeetingBookStorage(Paths.get(filePath))
                    .saveMeetingBook(meetingBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Delete {@code meetingBook} at the specified {@code filePath}.
     */
    private void deleteMeetingBook(String filePath) {
        try {
            new XmlMeetingBookStorage(Paths.get(filePath))
                    .deleteMeetingBook(addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error deleting to the file.", ioe);
        }
    }

    @Test
    public void saveMeetingBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveMeetingBook(new MeetingBook(), null);
    }

    @Test
    public void deleteMeetingBook_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempMeetingBook.xml");
        MeetingBook original = getTypicalMeetingBook();
        XmlMeetingBookStorage xmlMeetingBookStorage = new XmlMeetingBookStorage(filePath);
        xmlMeetingBookStorage.saveMeetingBook(original, filePath);

        xmlMeetingBookStorage.deleteMeetingBook(filePath);
        boolean afterDelete = Files.exists(filePath);
        assertFalse("Delete Address Book Fail!", afterDelete);
    }

    @Test
    public void deleteMeetingBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        deleteMeetingBook(null);
    }

    @Test
    public void getMeetingBookFilePath() {
        Path filePath = testFolder.getRoot().toPath().resolve("TempMeetingBook.xml");
        XmlMeetingBookStorage xmlMeetingBookStorage = new XmlMeetingBookStorage(filePath);
        assertEquals(xmlMeetingBookStorage.getMeetingBookFilePath(), filePath);
    }
}
