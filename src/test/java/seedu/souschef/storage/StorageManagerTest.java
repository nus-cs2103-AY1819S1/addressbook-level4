package seedu.souschef.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.storage.recipe.XmlRecipeStorage;
import seedu.souschef.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlGeneralStorage addressBookStorage = new XmlRecipeStorage(getTempFilePath("ab"));
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
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AppContent original = getTypicalAddressBook();
        storageManager.saveAppContent(original);
        ReadOnlyAppContent retrieved = storageManager.readAppContent().get();
        assertEquals(original, new AppContent(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAppContentFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the
        // saveAppContent method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAppContentChangedEvent(new AppContentChangedEvent(new AppContent()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the saveAppContent method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlGeneralStorage {

        public XmlAddressBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAppContent(ReadOnlyAppContent addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
