package seedu.superta.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.HOON;
import static seedu.superta.testutil.TypicalSuperTaClient.IDA;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;

public class XmlSuperTaClientStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSuperTaClientStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlySuperTaClient> readAddressBook(String filePath) throws Exception {
        return new XmlSuperTaClientStorage(Paths.get(filePath)).readSuperTaClient(addToTestDataPathIfNotNull(filePath));
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
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        SuperTaClient original = getTypicalSuperTaClient();
        XmlSuperTaClientStorage xmlAddressBookStorage = new XmlSuperTaClientStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveSuperTaClient(original, filePath);
        ReadOnlySuperTaClient readBack = xmlAddressBookStorage.readSuperTaClient(filePath).get();
        assertEquals(original, new SuperTaClient(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        xmlAddressBookStorage.saveSuperTaClient(original, filePath);
        readBack = xmlAddressBookStorage.readSuperTaClient(filePath).get();
        assertEquals(original, new SuperTaClient(readBack));

        //Save and read without specifying file path
        original.addStudent(IDA);
        xmlAddressBookStorage.saveSuperTaClient(original); //file path not specified
        readBack = xmlAddressBookStorage.readSuperTaClient().get(); //file path not specified
        assertEquals(original, new SuperTaClient(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlySuperTaClient addressBook, String filePath) {
        try {
            new XmlSuperTaClientStorage(Paths.get(filePath))
                    .saveSuperTaClient(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new SuperTaClient(), null);
    }


}
