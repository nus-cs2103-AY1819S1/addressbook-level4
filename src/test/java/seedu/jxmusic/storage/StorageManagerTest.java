package seedu.jxmusic.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.jxmusic.commons.events.model.LibraryChangedEvent;
import seedu.jxmusic.commons.events.storage.DataSavingExceptionEvent;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonLibraryStorage libraryStorage = new JsonLibraryStorage(getTempFilePath("lb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(libraryStorage, userPrefsStorage);
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
    public void libraryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonLibraryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonLibraryStorageTest} class.
         */
        Library original = getTypicalLibrary();
        storageManager.saveLibrary(original);
        ReadOnlyLibrary retrieved = storageManager.readLibrary();
        assertEquals(original, new Library(retrieved));
    }

    @Test
    public void getLibraryFilePath() {
        assertNotNull(storageManager.getLibraryFilePath());
    }

    @Test
    public void handleLibraryChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new JsonLibraryStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleLibraryChangedEvent(new LibraryChangedEvent(new Library()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class JsonLibraryStorageExceptionThrowingStub extends JsonLibraryStorage {

        public JsonLibraryStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
