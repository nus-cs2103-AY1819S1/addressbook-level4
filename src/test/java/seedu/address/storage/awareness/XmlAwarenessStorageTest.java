package seedu.address.storage.awareness;

import static org.junit.Assert.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.awareness.Awareness;
import seedu.address.storage.XmlAwarenessStorage;

public class XmlAwarenessStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlAwarenessStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
               ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
               : null;
    }

    private Optional<Awareness> readAwareness(String filePath) throws Exception {
        return new XmlAwarenessStorage(addToTestDataPathIfNotNull(filePath)).readAwarenessData();
    }

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAwareness(null);
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAwareness("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readAwareness("NotXmlFormatAwareness.xml");
    }


}
