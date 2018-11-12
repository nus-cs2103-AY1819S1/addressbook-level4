package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySsenisub;
import seedu.address.model.Ssenisub;

public class XmlSsenisubStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSsenisubStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readSsenisub_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readSsenisub(null);
    }

    private java.util.Optional<ReadOnlySsenisub> readSsenisub(String filePath) throws Exception {
        return new XmlSsenisubStorage(Paths.get(filePath)).readSsenisub(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSsenisub("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readSsenisub("NotXmlFormatSsenisub.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readSsenisub_invalidPersonSsenisub_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readSsenisub("invalidPersonSsenisub.xml");
    }

    @Test
    public void readSsenisub_invalidAndValidPersonSsenisub_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readSsenisub("invalidAndValidPersonSsenisub.xml");
    }

    @Test
    public void readAndSaveSsenisub_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempSsenisub.xml");
        Ssenisub original = getTypicalSsenisub();
        XmlSsenisubStorage xmlSsenisubStorage = new XmlSsenisubStorage(filePath);

        //Save in new file and read back
        xmlSsenisubStorage.saveSsenisub(original, filePath);
        ReadOnlySsenisub readBack = xmlSsenisubStorage.readSsenisub(filePath).get();
        assertEquals(original, new Ssenisub(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlSsenisubStorage.saveSsenisub(original, filePath);
        readBack = xmlSsenisubStorage.readSsenisub(filePath).get();
        assertEquals(original, new Ssenisub(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlSsenisubStorage.saveSsenisub(original); //file path not specified
        readBack = xmlSsenisubStorage.readSsenisub().get(); //file path not specified
        assertEquals(original, new Ssenisub(readBack));

    }

    @Test
    public void saveSsenisub_nullSsenisub_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveSsenisub(null, "SomeFile.xml");
    }

    /**
     * Saves {@code ssenisub} at the specified {@code filePath}.
     */
    private void saveSsenisub(ReadOnlySsenisub ssenisub, String filePath) {
        try {
            new XmlSsenisubStorage(Paths.get(filePath))
                    .saveSsenisub(ssenisub, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSsenisub_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveSsenisub(new Ssenisub(), null);
    }


}
