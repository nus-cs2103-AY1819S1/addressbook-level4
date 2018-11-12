package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JORDAN;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ArchiveList;
import seedu.address.model.ReadOnlyArchiveList;

public class XmlArchiveListStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlArchiveListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readArchiveList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readArchiveList(null);
    }

    private java.util.Optional<ReadOnlyArchiveList> readArchiveList(String filePath) throws Exception {
        return new XmlArchiveListStorage(Paths.get(filePath)).readArchiveList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
              ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
              : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readArchiveList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readArchiveList("NotXmlFormatArchiveList.xml");
    }

    @Test
    public void readArchiveList_invalidPersonArchiveList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readArchiveList("invalidPersonArchiveList.xml");
    }

    @Test
    public void readArchiveList_invalidAndValidPersonArchiveList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readArchiveList("invalidAndValidPersonArchiveList.xml");
    }

    @Test
    public void readAndSaveArchiveList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempArchiveList.xml");
        ArchiveList original = getTypicalArchiveList();
        XmlArchiveListStorage xmlArchiveListStorage = new XmlArchiveListStorage(filePath);

        //Save in new file and read back
        xmlArchiveListStorage.saveArchiveList(original, filePath);
        ReadOnlyArchiveList readBack = xmlArchiveListStorage.readArchiveList(filePath).get();
        assertEquals(original, new ArchiveList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(JORDAN);
        xmlArchiveListStorage.saveArchiveList(original, filePath);
        readBack = xmlArchiveListStorage.readArchiveList(filePath).get();
        assertEquals(original, new ArchiveList(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlArchiveListStorage.saveArchiveList(original); //file path not specified
        readBack = xmlArchiveListStorage.readArchiveList().get(); //file path not specified
        assertEquals(original, new ArchiveList(readBack));
    }

    @Test
    public void saveArchiveList_nullArchiveList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveArchiveList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code archiveList} at the specified {@code filePath}.
     */
    private void saveArchiveList(ReadOnlyArchiveList archiveList, String filePath) {
        try {
            new XmlArchiveListStorage(Paths.get(filePath))
                  .saveArchiveList(archiveList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveArchiveList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveArchiveList(new ArchiveList(), null);
    }
}
