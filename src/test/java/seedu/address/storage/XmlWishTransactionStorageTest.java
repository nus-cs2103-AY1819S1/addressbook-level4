package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalWishes.getTypicalWishTransaction;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.WishTransaction;

public class XmlWishTransactionStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src","test", "XmlWishTransactionTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readWishTransaction_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readWishTransaction(null);
    }

    private Optional<WishTransaction> readWishTransaction(String filePath) throws IOException, DataConversionException {
        return new XmlWishTransactionStorage(Paths.get(filePath))
                .readWishTransaction(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWishTransaction("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readWishTransaction("NotXmlFormatWishBook.xml");
    }

    @Test
    public void readAndSaveWishTransaction() throws Exception{
        Path filePath = testFolder.getRoot().toPath().resolve("TempWishTransaction.xml");
        WishTransaction original = getTypicalWishTransaction();
        XmlWishTransactionStorage xmlWishTransactionStorage = new XmlWishTransactionStorage(filePath);

        // Save in new file and read back
        xmlWishTransactionStorage.saveWishTransaction(original, filePath);
        WishTransaction read = xmlWishTransactionStorage.readWishTransaction().get();
        assertEquals(original, read);
    }
}
