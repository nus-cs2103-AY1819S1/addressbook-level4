package seedu.superta.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.superta.commons.events.model.SuperTaClientChangedEvent;
import seedu.superta.commons.events.storage.DataSavingExceptionEvent;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.UserPrefs;
import seedu.superta.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlSuperTaClientStorage addressBookStorage = new XmlSuperTaClientStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlSuperTaClientStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlSuperTaClientStorageTest} class.
         */
        SuperTaClient original = getTypicalSuperTaClient();
        storageManager.saveSuperTaClient(original);
        ReadOnlySuperTaClient retrieved = storageManager.readSuperTaClient().get();
        assertEquals(original, new SuperTaClient(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getSuperTaClientFilePath());
    }

    @Test
    public void handleSuperTaClientChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlSuperTaClientStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleSuperTaClientChangedEvent(new SuperTaClientChangedEvent(new SuperTaClient()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlSuperTaClientStorageExceptionThrowingStub extends XmlSuperTaClientStorage {

        public XmlSuperTaClientStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveSuperTaClient(ReadOnlySuperTaClient superTaClient, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
