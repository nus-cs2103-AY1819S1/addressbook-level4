package seedu.souschef.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.souschef.testutil.TypicalRecipes.APPLE;
import static seedu.souschef.testutil.TypicalRecipes.HOON;
import static seedu.souschef.testutil.TypicalRecipes.IDA;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.storage.recipe.XmlRecipeStorage;

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
        return new XmlRecipeStorage(Paths.get(filePath)).readFeature(addToTestDataPathIfNotNull(filePath));
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
        XmlFeatureStorage xmlAddressBookStorage = new XmlRecipeStorage(filePath);

        //Save in new file and readAppContent back
        xmlAddressBookStorage.saveFeature(original, filePath);
        ReadOnlyAppContent readBack = xmlAddressBookStorage.readFeature(filePath).get();
        assertEquals(original, new AppContent(readBack));

        //Modify data, ove
        // rwrite exiting file, and readAppContent back

        original.getRecipes().add(HOON);
        original.getRecipes().remove(APPLE);
        xmlAddressBookStorage.saveFeature(original, filePath);
        readBack = xmlAddressBookStorage.readFeature(filePath).get();
        assertEquals(original, new AppContent(readBack));

        //Save and readAppContent without specifying file path
        original.getRecipes().add(IDA);
        xmlAddressBookStorage.saveFeature(original); //file path not specified
        readBack = xmlAddressBookStorage.readFeature().get(); //file path not specified

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
                    .saveFeature(addressBook, addToTestDataPathIfNotNull(filePath));
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
