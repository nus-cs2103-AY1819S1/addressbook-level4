package seedu.parking.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.parking.testutil.TypicalCarparks.ALFA;
import static seedu.parking.testutil.TypicalCarparks.HOTEL;
import static seedu.parking.testutil.TypicalCarparks.INDIA;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.ReadOnlyCarparkFinder;

public class XmlCarparkFinderStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlCarparkFinderStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readCarparkFinder_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readCarparkFinder(null);
    }

    private java.util.Optional<ReadOnlyCarparkFinder> readCarparkFinder(String filePath) throws Exception {
        return new XmlCarparkFinderStorage(Paths.get(filePath)).readCarparkFinder(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCarparkFinder("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readCarparkFinder("NotXmlFormatCarparkFinder.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readCarparkFinder_invalidCarparkCarparkFinder_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCarparkFinder("invalidCarparkCarparkFinder.xml");
    }

    @Test
    public void readCarparkFinder_invalidAndValidCarparkFinder_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCarparkFinder("invalidAndValidCarparkCarparkFinder.xml");
    }

    @Test
    public void readAndSaveCarparkFinder_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempCarparkFinder.xml");
        CarparkFinder original = getTypicalCarparkFinder();
        XmlCarparkFinderStorage xmlCarparkFinderStorage = new XmlCarparkFinderStorage(filePath);

        //Save in new file and read back
        xmlCarparkFinderStorage.saveCarparkFinder(original, filePath);
        ReadOnlyCarparkFinder readBack = xmlCarparkFinderStorage.readCarparkFinder(filePath).get();
        assertEquals(original, new CarparkFinder(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addCarpark(HOTEL);
        original.removeCarpark(ALFA);
        xmlCarparkFinderStorage.saveCarparkFinder(original, filePath);
        readBack = xmlCarparkFinderStorage.readCarparkFinder(filePath).get();
        assertEquals(original, new CarparkFinder(readBack));

        //Save and read without specifying file path
        original.addCarpark(INDIA);
        xmlCarparkFinderStorage.saveCarparkFinder(original); //file path not specified
        readBack = xmlCarparkFinderStorage.readCarparkFinder().get(); //file path not specified
        assertEquals(original, new CarparkFinder(readBack));

    }

    @Test
    public void saveCarparkFinder_nullCarparkFinder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCarparkFinder(null, "SomeFile.xml");
    }

    /**
     * Saves {@code carparkFinder} at the specified {@code filePath}.
     */
    private void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder, String filePath) {
        try {
            new XmlCarparkFinderStorage(Paths.get(filePath))
                    .saveCarparkFinder(carparkFinder, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCarparkFinder_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCarparkFinder(new CarparkFinder(), null);
    }

}
