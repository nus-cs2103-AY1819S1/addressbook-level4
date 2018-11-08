package seedu.parking.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.parking.commons.events.model.CarparkFinderChangedEvent;
import seedu.parking.commons.events.storage.DataSavingExceptionEvent;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.ReadOnlyCarparkFinder;
import seedu.parking.model.UserPrefs;
import seedu.parking.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlCarparkFinderStorage carparkFinderStorage = new XmlCarparkFinderStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(carparkFinderStorage, userPrefsStorage);
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
    public void carparkFinderReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlCarparkFinderStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlCarparkFinderStorageTest} class.
         */
        CarparkFinder original = getTypicalCarparkFinder();
        storageManager.saveCarparkFinder(original);
        ReadOnlyCarparkFinder retrieved = storageManager.readCarparkFinder().get();
        assertEquals(original, new CarparkFinder(retrieved));
    }

    @Test
    public void getCarparkFinderFilePath() {
        assertNotNull(storageManager.getCarparkFinderFilePath());
    }

    @Test
    public void handleCarparkFinderChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlCarparkFinderStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleCarparkFinderChangedEvent(new CarparkFinderChangedEvent(new CarparkFinder()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlCarparkFinderStorageExceptionThrowingStub extends XmlCarparkFinderStorage {

        public XmlCarparkFinderStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
