package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTriviaResults.getTypicalTriviaResults;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TriviaBundleChangedEvent;
import seedu.address.commons.events.model.TriviaResultsChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TriviaResults;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        XmlTriviaResultsStorage triviaResultStorage = new
                XmlTriviaResultsStorage(getTempFilePath("tr"));
        XmlTriviaBundleStorage triviaBundleStorage = new XmlTriviaBundleStorage(getTempFilePath("tb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, triviaBundleStorage,
                triviaResultStorage, userPrefsStorage);
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
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void triviaBundleSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTriviaBundleStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTriviaBundleStorageTest} class.
         */
        TriviaBundle original = getTypicalTriviaBundle();
        storageManager.saveTriviaBundle(original);
        ReadOnlyTriviaBundle retrieved = storageManager.readTriviaBundle().get();
        assertEquals(original, new TriviaBundle(retrieved));
    }

    @Test
    public void triviaResultSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTriviaResultsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTriviaResultsStorageTest} class.
         */
        ReadOnlyTriviaResults original = getTypicalTriviaResults();
        storageManager.saveTriviaResults(original);
        ReadOnlyTriviaResults retrieved = storageManager.readTriviaResults().get();
        assertEquals(original, new TriviaResults(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void getTriviaBundleFilePath() {
        assertNotNull(storageManager.getTriviaBundleFilePath());
    }

    @Test
    public void getTriviaResultsFilePath() {
        assertNotNull(storageManager.getTriviaResultsFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTriviaStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTriviaResultsStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleTriviaBundleChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTriviaStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTriviaResultsStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleTriviaBundleChangedEvent(new TriviaBundleChangedEvent(new TriviaBundle()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleTriviaResultsChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTriviaStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTriviaResultsStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleTriviaResultsChangedEvent(new TriviaResultsChangedEvent(new TriviaResults()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTriviaStorageExceptionThrowingStub extends XmlTriviaBundleStorage {

        public XmlTriviaStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTriviaResultsStorageExceptionThrowingStub extends XmlTriviaResultsStorage {

        public XmlTriviaResultsStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTriviaResults(ReadOnlyTriviaResults triviaResults, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
