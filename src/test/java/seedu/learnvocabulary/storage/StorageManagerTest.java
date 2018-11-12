package seedu.learnvocabulary.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.learnvocabulary.commons.events.model.LearnVocabularyChangedEvent;
import seedu.learnvocabulary.commons.events.storage.DataSavingExceptionEvent;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlLearnVocabularyStorage learnVocabularyStorage = new XmlLearnVocabularyStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(learnVocabularyStorage, userPrefsStorage);
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
    public void learnVocabularyReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlLearnVocabularyStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlLearnVocabularyStorageTest} class.
         */
        LearnVocabulary original = getTypicalLearnVocabulary();
        storageManager.saveLearnVocabulary(original);
        ReadOnlyLearnVocabulary retrieved = storageManager.readLearnVocabulary().get();
        assertEquals(original, new LearnVocabulary(retrieved));
    }

    @Test
    public void getLearnVocabularyFilePath() {
        assertNotNull(storageManager.getLearnVocabularyFilePath());
    }

    @Test
    public void handleLearnVocabularyChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlLearnVocabularyStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleLearnVocabularyChangedEvent(new LearnVocabularyChangedEvent(new LearnVocabulary()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlLearnVocabularyStorageExceptionThrowingStub extends XmlLearnVocabularyStorage {

        public XmlLearnVocabularyStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
