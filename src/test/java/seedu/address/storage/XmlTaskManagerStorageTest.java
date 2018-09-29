package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTasks.A_TASK;
import static seedu.address.testutil.TypicalTasks.H_TASK;
import static seedu.address.testutil.TypicalTasks.I_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;

public class XmlTaskManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlTaskManagerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskManager_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTaskManager(null);
    }

    private java.util.Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws Exception {
        return new XmlTaskManagerStorage(Paths.get(filePath)).readTaskManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskManager("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readTaskManager_invalidTaskTaskManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskManager("invalidTaskManager.xml");
    }

    @Test
    public void readTaskManager_invalidAndValidTaskTaskManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskManager("invalidAndValidTaskTaskManager.xml");
    }

    @Test
    public void readAndSaveTaskManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        TaskManager original = getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(filePath);

        //Save in new file and read back
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        ReadOnlyTaskManager readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(H_TASK);
        original.removeTask(A_TASK);
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Save and read without specifying file path
        original.addTask(I_TASK);
        xmlTaskManagerStorage.saveTaskManager(original); //file path not specified
        readBack = xmlTaskManagerStorage.readTaskManager().get(); //file path not specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test
    public void saveTaskManager_nullTaskManager_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTaskManager(null, "SomeFile.xml");
    }

    /**
     * Saves {@code taskManager} at the specified {@code filePath}.
     */
    private void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) {
        try {
            new XmlTaskManagerStorage(Paths.get(filePath))
                    .saveTaskManager(taskManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskManager_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTaskManager(new TaskManager(), null);
    }


}
