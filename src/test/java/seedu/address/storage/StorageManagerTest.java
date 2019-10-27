package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.UserPrefs;
import seedu.address.storage.entry.XmlEntryBookStorage;
import seedu.address.testutil.Assert;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlEntryBookStorage entryBookStorage = new XmlEntryBookStorage(getTempFilePath("eb"));
        TxtTemplateStorage templateStorage = new TxtTemplateStorage(getTempFilePath("tmp"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));

        storageManager = new StorageManager(entryBookStorage, templateStorage, userPrefsStorage);
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
    public void loadTemplate() {
        /*
         * More extensive testing of Template reading is done in {@link TxtTemplateStorageTest} class.
         * Since Template cannot be saved via the app, there's no way of actually reading a Template from a temp folder
         * and checking if it is read correctly. But as an integration test, this test still checks that StorageManager
         * forwards the call to TemplateStorage correctly.
         */

        Assert.assertThrows(FileNotFoundException.class, () ->
            storageManager.loadTemplate(storageManager.getTemplateFilePath()));
    }

    @Test
    public void getTemplateFilePath() {
        assertNotNull(storageManager.getTemplateFilePath());
    }
}
