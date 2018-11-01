package seedu.meeting.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.meeting.commons.events.model.MeetingBookChangedEvent;
import seedu.meeting.commons.events.model.MeetingBookExportEvent;
import seedu.meeting.commons.events.model.UserPrefsChangeEvent;
import seedu.meeting.commons.events.storage.DataSavingExceptionEvent;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlMeetingBookStorage meetingBookStorage = new XmlMeetingBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(meetingBookStorage, userPrefsStorage);
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
    public void meetingBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlMeetingBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlMeetingBookStorageTest} class.
         */
        MeetingBook original = getTypicalMeetingBook();
        storageManager.saveMeetingBook(original);
        ReadOnlyMeetingBook retrieved = storageManager.readMeetingBook().get();
        assertEquals(original, new MeetingBook(retrieved));
    }

    @Test
    public void getMeetingBookFilePath() {
        assertNotNull(storageManager.getMeetingBookFilePath());
    }

    @Test
    public void handleMeetingBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlMeetingBookStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleMeetingBookChangedEvent(new MeetingBookChangedEvent(new MeetingBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleExportMeetingBookEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the export method is called
        Storage storage = new StorageManager(new XmlMeetingBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleMeetingBookExportEvent(new MeetingBookExportEvent(new MeetingBook(), Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleUserPrefsChangeEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the change method is called
        Storage storage = new StorageManager(new XmlMeetingBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleUserPrefsChangeEvent(new UserPrefsChangeEvent(new UserPrefs(), new MeetingBook(),
                Paths.get("dummy"), Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save/delete method is called
     */
    class XmlMeetingBookStorageExceptionThrowingStub extends XmlMeetingBookStorage {

        public XmlMeetingBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMeetingBook(ReadOnlyMeetingBook meetingBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void deleteMeetingBook(Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
