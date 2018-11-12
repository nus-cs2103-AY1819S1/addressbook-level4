package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.SsenisubChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlySsenisub;
import seedu.address.model.Ssenisub;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlSsenisubStorage ssenisubStorage = new XmlSsenisubStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(ssenisubStorage, userPrefsStorage);
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
    public void ssenisubReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlSsenisubStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlSsenisubStorageTest} class.
         */
        Ssenisub original = getTypicalSsenisub();
        storageManager.saveSsenisub(original);
        ReadOnlySsenisub retrieved = storageManager.readSsenisub().get();
        assertEquals(original, new Ssenisub(retrieved));
    }

    @Test
    public void getSsenisubFilePath() {
        assertNotNull(storageManager.getSsenisubFilePath());
    }

    @Test
    public void handleSsenisubChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlSsenisubStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleSsenisubChangedEvent(new SsenisubChangedEvent(new Ssenisub()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlSsenisubStorageExceptionThrowingStub extends XmlSsenisubStorage {

        public XmlSsenisubStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveSsenisub(ReadOnlySsenisub ssenisub, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
