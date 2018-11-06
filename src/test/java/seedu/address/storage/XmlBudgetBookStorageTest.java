package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCcas.HOCKEY;
import static seedu.address.testutil.TypicalCcas.SOFTBALL;
import static seedu.address.testutil.TypicalCcas.TRACK;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BudgetBook;
import seedu.address.model.ReadOnlyBudgetBook;

//@@author ericyjw
public class XmlBudgetBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlBudgetBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readBudgetBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readBudgetBook(null);
    }

    private Optional<ReadOnlyBudgetBook> readBudgetBook(String filePath) throws Exception {
        return new XmlBudgetBookStorage(Paths.get(filePath)).readBudgetBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBudgetBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readBudgetBook("NotXmlFormatBudgetBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readBudgetBook_invalidCcaBudgetBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readBudgetBook("invalidCcaBudgetBook.xml");
    }

    @Test
    public void readBudgetBook_invalidAndValidCcaBudgetBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readBudgetBook("invalidAndValidCcaBudgetBook.xml");
    }

    @Test
    public void readAndSaveBudgetBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempBudgetBook.xml");
        BudgetBook original = getTypicalBudgetBook();
        XmlBudgetBookStorage xmlBudgetBookStorage = new XmlBudgetBookStorage(filePath);

        //Save in new file and read back
        xmlBudgetBookStorage.saveBudgetBook(original, filePath);
        ReadOnlyBudgetBook readBack = xmlBudgetBookStorage.readBudgetBook(filePath).get();
        assertEquals(original, new BudgetBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addCca(HOCKEY);
        original.removeCca(TRACK);
        xmlBudgetBookStorage.saveBudgetBook(original, filePath);
        readBack = xmlBudgetBookStorage.readBudgetBook(filePath).get();
        assertEquals(original, new BudgetBook(readBack));

        //Save and read without specifying file path
        original.addCca(SOFTBALL);
        xmlBudgetBookStorage.saveBudgetBook(original); //file path not specified
        readBack = xmlBudgetBookStorage.readBudgetBook().get(); //file path not specified
        assertEquals(original, new BudgetBook(readBack));

    }

    @Test
    public void saveBudgetBook_nullBudgetBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveBudgetBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveBudgetBook(ReadOnlyBudgetBook budgetBook, String filePath) {
        try {
            new XmlBudgetBookStorage(Paths.get(filePath))
                .saveBudgetBook(budgetBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBudgetBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveBudgetBook(new BudgetBook(), null);
    }
}
