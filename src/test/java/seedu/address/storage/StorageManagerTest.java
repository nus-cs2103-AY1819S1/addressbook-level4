package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_OCAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EHOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SHOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMIN;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;
import static seedu.address.testutil.TypicalCalendars.DEFAULT_CALENDAR_NAME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AllDayEventAddedEvent;
import seedu.address.commons.events.model.CalendarCreatedEvent;
import seedu.address.commons.events.model.CalendarEventAddedEvent;
import seedu.address.commons.events.model.CalendarEventDeletedEvent;
import seedu.address.commons.events.model.LoadCalendarEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.CalendarNotFoundEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalCalendars;
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
        XmlBudgetBookStorage budgetBookStorage = new XmlBudgetBookStorage(getTempFilePath("bdg"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        IcsCalendarStorage calendarStorage = new IcsCalendarStorage(getTempFilePath("cal"));
        EmailDirStorage emailStorage = new EmailDirStorage(getTempFilePath("em"));
        ProfilePictureDirStorage profilePictureStorage = new ProfilePictureDirStorage((getTempFilePath("pro")),
                getTempFilePath("outProfile"));
        storageManager = new StorageManager(addressBookStorage, budgetBookStorage, userPrefsStorage, calendarStorage,
            emailStorage, profilePictureStorage);
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
    public void calendarsReadSave() {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link IcsCalendarStorage} class.
         */
        IcsCalendarStorage original = new IcsCalendarStorage(getTempFilePath("dummy"));
        original.setCalendarPath(getTempFilePath("pathCheck"));
        storageManager.setCalendarPath(getTempFilePath("pathCheck"));
        IcsCalendarStorage retrieved = (IcsCalendarStorage) storageManager.getCalendarStorage();
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
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
            new XmlBudgetBookStorage(Paths.get("dummy")),
            new JsonUserPrefsStorage(Paths.get("dummy")),
            new IcsCalendarStorage(Paths.get("dummy")),
            new EmailDirStorage(Paths.get("dummy")),
            new ProfilePictureDirStorage((Paths.get("dummy")), Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
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

    @Test
    public void handleCalendarCreatedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the create method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage(Paths.get("dummy")),
                new XmlBudgetBookStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new IcsCalendarStorageExceptionThrowingStub(Paths.get("dummy")),
                new EmailDirStorage(Paths.get("dummy")),
                new ProfilePictureDirStorage((Paths.get("dummy")), Paths.get("dummy")));
        storage.handleCalendarCreatedEvent(new CalendarCreatedEvent(TypicalCalendars.DEFAULT_CALENDAR,
                DEFAULT_CALENDAR_NAME));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleLoadCalendarEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the create method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage(Paths.get("dummy")),
                new XmlBudgetBookStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new IcsCalendarStorageExceptionThrowingStub(Paths.get("dummy")),
                new EmailDirStorage(Paths.get("dummy")),
                new ProfilePictureDirStorage((Paths.get("dummy")), Paths.get("dummy")));
        storage.handleLoadCalendarEvent(new LoadCalendarEvent(DEFAULT_CALENDAR_NAME));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof CalendarNotFoundEvent);
    }

    @Test
    public void handleAllDayEventAddedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the create method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage(Paths.get("dummy")),
                new XmlBudgetBookStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new IcsCalendarStorageExceptionThrowingStub(Paths.get("dummy")),
                new EmailDirStorage(Paths.get("dummy")),
                new ProfilePictureDirStorage((Paths.get("dummy")), Paths.get("dummy")));
        storage.handleAllDayEventAddedEvent(new AllDayEventAddedEvent(DEFAULT_YEAR, DEFAULT_MONTH,
                VALID_CALENDAR_DATE_1, VALID_CALENDAR_TITLE_OCAMP, TypicalCalendars.DEFAULT_CALENDAR));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleCalendarEventAddedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the create method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage(Paths.get("dummy")),
                new XmlBudgetBookStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new IcsCalendarStorageExceptionThrowingStub(Paths.get("dummy")),
                new EmailDirStorage(Paths.get("dummy")),
                new ProfilePictureDirStorage((Paths.get("dummy")), Paths.get("dummy")));
        storage.handleCalendarEventAddedEvent(new CalendarEventAddedEvent(DEFAULT_YEAR, DEFAULT_MONTH,
                VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN, VALID_CALENDAR_DATE_2, VALID_EHOUR,
                VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP, TypicalCalendars.DEFAULT_CALENDAR));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleCalendarEventDeletedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the create method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage(Paths.get("dummy")),
                new XmlBudgetBookStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new IcsCalendarStorageExceptionThrowingStub(Paths.get("dummy")),
                new EmailDirStorage(Paths.get("dummy")),
                new ProfilePictureDirStorage((Paths.get("dummy")), Paths.get("dummy")));
        storage.handleCalendarEventDeletedEvent(new CalendarEventDeletedEvent(DEFAULT_YEAR, DEFAULT_MONTH,
                VALID_CALENDAR_DATE_1, VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_OCAMP,
                TypicalCalendars.DEFAULT_CALENDAR));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub IcsCalendarStorage class to throw an exception when the create and load method is called
     */
    class IcsCalendarStorageExceptionThrowingStub extends IcsCalendarStorage {

        public IcsCalendarStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void createCalendar(Calendar calendar, String calendarName) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public Calendar loadCalendar(String calendarName) throws IOException, ParserException {
            throw new IOException("dummy exception");
        }
    }
}
