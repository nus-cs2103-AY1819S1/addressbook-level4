package seedu.clinicio.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.HOON;
import static seedu.clinicio.testutil.TypicalPersons.IDA;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;

public class XmlClinicIoStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlClinicIoStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readClinicIo_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readClinicIo(null);
    }

    private java.util.Optional<ReadOnlyClinicIo> readClinicIo(String filePath) throws Exception {
        return new XmlClinicIoStorage(Paths.get(filePath)).readClinicIo(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClinicIo("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readClinicIo("NotXmlFormatClinicIo.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readClinicIo_invalidPersonClinicIo_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readClinicIo("invalidPersonClinicIo.xml");
    }

    @Test
    public void readClinicIo_invalidAndValidPersonClinicIo_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readClinicIo("invalidAndValidPersonClinicIo.xml");
    }

    @Test
    public void readAndSaveClinicIo_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempClinicIo.xml");
        ClinicIo original = getTypicalClinicIo();
        XmlClinicIoStorage xmlClinicIoStorage = new XmlClinicIoStorage(filePath);

        //Save in new file and read back
        xmlClinicIoStorage.saveClinicIo(original, filePath);
        ReadOnlyClinicIo readBack = xmlClinicIoStorage.readClinicIo(filePath).get();
        assertEquals(original, new ClinicIo(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlClinicIoStorage.saveClinicIo(original, filePath);
        readBack = xmlClinicIoStorage.readClinicIo(filePath).get();
        assertEquals(original, new ClinicIo(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlClinicIoStorage.saveClinicIo(original); //file path not specified
        readBack = xmlClinicIoStorage.readClinicIo().get(); //file path not specified
        assertEquals(original, new ClinicIo(readBack));

    }

    @Test
    public void saveClinicIo_nullClinicIo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveClinicIo(null, "SomeFile.xml");
    }

    /**
     * Saves {@code clinicIo} at the specified {@code filePath}.
     */
    private void saveClinicIo(ReadOnlyClinicIo clinicIo, String filePath) {
        try {
            new XmlClinicIoStorage(Paths.get(filePath))
                    .saveClinicIo(clinicIo, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClinicIo_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveClinicIo(new ClinicIo(), null);
    }

}
