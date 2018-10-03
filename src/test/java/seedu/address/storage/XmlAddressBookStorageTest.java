package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalRecipes.ALICE;
import static seedu.address.testutil.TypicalRecipes.HOON;
import static seedu.address.testutil.TypicalRecipes.IDA;
import static seedu.address.testutil.TypicalRecipes.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppContent;
import seedu.address.model.ReadOnlyAppContent;
import seedu.address.storage.recipe.XmlRecipeStorage;

public class XmlAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlAddressBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyAppContent> readAddressBook(String filePath) throws Exception {
        return new XmlRecipeStorage(Paths.get(filePath)).read(addToTestDataPathIfNotNull(filePath));
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
    public void readAddressBook_invalidRecipeAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidRecipeAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidRecipeAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidRecipeAddressBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        AppContent original = getTypicalAddressBook();
        XmlGeneralStorage xmlAddressBookStorage = new XmlRecipeStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.save(original, filePath);
        ReadOnlyAppContent readBack = xmlAddressBookStorage.read(filePath).get();
        assertEquals(original, new AppContent(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRecipe(HOON);
        original.removeRecipe(ALICE);
        xmlAddressBookStorage.save(original, filePath);
        readBack = xmlAddressBookStorage.read(filePath).get();
        assertEquals(original, new AppContent(readBack));

        //Save and read without specifying file path
        original.addRecipe(IDA);
        xmlAddressBookStorage.save(original); //file path not specified
        readBack = xmlAddressBookStorage.read().get(); //file path not specified
        assertEquals(original, new AppContent(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAppContent addressBook, String filePath) {
        try {
            new XmlRecipeStorage(Paths.get(filePath))
                    .save(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new AppContent(), null);
    }


}
