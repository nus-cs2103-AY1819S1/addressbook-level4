package seedu.clinicio.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;
import seedu.clinicio.commons.events.storage.DataSavingExceptionEvent;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlClinicIoStorage clinicIoStorage = new XmlClinicIoStorage(getTempFilePath("ci"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(clinicIoStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void clinicIoReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlClinicIoStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlClinicIoStorageTest} class.
         */
        ClinicIo original = getTypicalClinicIo();
        storageManager.saveClinicIo(original);
        ReadOnlyClinicIo retrieved = storageManager.readClinicIo().get();
        assertEquals(original, new ClinicIo(retrieved));
    }

    @Test
    public void getClinicIoFilePath() {
        assertNotNull(storageManager.getClinicIoFilePath());
    }

    @Test
    public void handleClinicIoChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlClinicIoStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleClinicIoChangedEvent(new ClinicIoChangedEvent(new ClinicIo()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlClinicIoStorageExceptionThrowingStub extends XmlClinicIoStorage {

        public XmlClinicIoStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveClinicIo(ReadOnlyClinicIo clinicIo, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
