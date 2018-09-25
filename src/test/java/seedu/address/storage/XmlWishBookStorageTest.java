package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalWishes.ALICE;
import static seedu.address.testutil.TypicalWishes.HOON;
import static seedu.address.testutil.TypicalWishes.IDA;
import static seedu.address.testutil.TypicalWishes.getTypicalWishBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.WishBook;
import seedu.address.model.ReadOnlyWishBook;

public class XmlWishBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlWishBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readWishBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readWishBook(null);
    }

    private java.util.Optional<ReadOnlyWishBook> readWishBook(String filePath) throws Exception {
        return new XmlWishBookStorage(Paths.get(filePath)).readWishBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWishBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readWishBook("NotXmlFormatWishBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readWishBook("invalidWishWishBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readWishBook("invalidAndValidWishWishBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        WishBook original = getTypicalWishBook();
        XmlWishBookStorage xmlAddressBookStorage = new XmlWishBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveWishBook(original, filePath);
        ReadOnlyWishBook readBack = xmlAddressBookStorage.readWishBook(filePath).get();
        assertEquals(original, new WishBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addWish(HOON);
        original.removeWish(ALICE);
        xmlAddressBookStorage.saveWishBook(original, filePath);
        readBack = xmlAddressBookStorage.readWishBook(filePath).get();
        assertEquals(original, new WishBook(readBack));

        //Save and read without specifying file path
        original.addWish(IDA);
        xmlAddressBookStorage.saveWishBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readWishBook().get(); //file path not specified
        assertEquals(original, new WishBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyWishBook addressBook, String filePath) {
        try {
            new XmlWishBookStorage(Paths.get(filePath))
                    .saveWishBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new WishBook(), null);
    }


}
