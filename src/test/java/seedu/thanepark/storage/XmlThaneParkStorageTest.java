package seedu.thanepark.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.HAUNTED;
import static seedu.thanepark.testutil.TypicalRides.IDA;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.thanepark.commons.exceptions.DataConversionException;
import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;

public class XmlThaneParkStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlThaneParkStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readThanePark_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readThanePark(null);
    }

    private java.util.Optional<ReadOnlyThanePark> readThanePark(String filePath) throws Exception {
        return new XmlThaneParkStorage(Paths.get(filePath)).readThanePark(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readThanePark("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readThanePark("NotXmlFormatThanePark.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readThanePark_invalidRideThanePark_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readThanePark("invalidRideThanePark.xml");
    }

    @Test
    public void readThanePark_invalidAndValidRideThanePark_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readThanePark("invalidAndValidRideThanePark.xml");
    }

    @Test
    public void readAndSaveThanePark_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempThanePark.xml");
        ThanePark original = getTypicalThanePark();
        XmlThaneParkStorage xmlThaneParkStorage = new XmlThaneParkStorage(filePath);

        //Save in new file and read back
        xmlThaneParkStorage.saveThanePark(original, filePath);
        ReadOnlyThanePark readBack = xmlThaneParkStorage.readThanePark(filePath).get();
        assertEquals(original, new ThanePark(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRide(HAUNTED);
        original.removeRide(ACCELERATOR);
        xmlThaneParkStorage.saveThanePark(original, filePath);
        readBack = xmlThaneParkStorage.readThanePark(filePath).get();
        assertEquals(original, new ThanePark(readBack));

        //Save and read without specifying file path
        original.addRide(IDA);
        xmlThaneParkStorage.saveThanePark(original); //file path not specified
        readBack = xmlThaneParkStorage.readThanePark().get(); //file path not specified
        assertEquals(original, new ThanePark(readBack));

    }

    @Test
    public void saveThanePark_nullThanePark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveThanePark(null, "SomeFile.xml");
    }

    /**
     * Saves {@code thanePark} at the specified {@code filePath}.
     */
    private void saveThanePark(ReadOnlyThanePark thanePark, String filePath) {
        try {
            new XmlThaneParkStorage(Paths.get(filePath))
                    .saveThanePark(thanePark, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveThanePark_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveThanePark(new ThanePark(), null);
    }


}
