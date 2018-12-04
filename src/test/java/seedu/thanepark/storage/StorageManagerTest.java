package seedu.thanepark.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.thanepark.commons.events.model.ThaneParkChangedEvent;
import seedu.thanepark.commons.events.storage.DataSavingExceptionEvent;
import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlThaneParkStorage thaneParkStorage = new XmlThaneParkStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(thaneParkStorage, userPrefsStorage);
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
    public void thaneParkReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlThaneParkStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlThaneParkStorageTest} class.
         */
        ThanePark original = getTypicalThanePark();
        storageManager.saveThanePark(original);
        ReadOnlyThanePark retrieved = storageManager.readThanePark().get();
        assertEquals(original, new ThanePark(retrieved));
    }

    @Test
    public void getThaneParkFilePath() {
        assertNotNull(storageManager.getThaneParkFilePath());
    }

    @Test
    public void handleThaneParkChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlThaneParkStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleThaneParkChangedEvent(new ThaneParkChangedEvent(new ThanePark()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlThaneParkStorageExceptionThrowingStub extends XmlThaneParkStorage {

        public XmlThaneParkStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveThanePark(ReadOnlyThanePark thanePark, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
