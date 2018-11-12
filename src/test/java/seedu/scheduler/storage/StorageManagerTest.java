package seedu.scheduler.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.scheduler.commons.events.model.SchedulerChangedEvent;
import seedu.scheduler.commons.events.storage.DataSavingExceptionEvent;
import seedu.scheduler.model.ReadOnlyScheduler;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlSchedulerStorage schedulerStorage = new XmlSchedulerStorage(getTempFilePath("scheduler"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(schedulerStorage, userPrefsStorage);
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
    public void schedulerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlSchedulerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlSchedulerStorageTest} class.
         */
        Scheduler original = getTypicalScheduler();
        storageManager.saveScheduler(original);
        ReadOnlyScheduler retrieved = storageManager.readScheduler().get();
        assertEquals(original, new Scheduler(retrieved));
    }

    @Test
    public void getSchedulerFilePath() {
        assertNotNull(storageManager.getSchedulerFilePath());
    }

    @Test
    public void handleSchedulerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlSchedulerStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleSchedulerChangedEvent(new SchedulerChangedEvent(new Scheduler()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlSchedulerStorageExceptionThrowingStub extends XmlSchedulerStorage {

        public XmlSchedulerStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveScheduler(ReadOnlyScheduler scheduler, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
