package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.OrderBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.storage.deliveryman.XmlDeliverymenListStorage;
import seedu.address.storage.route.XmlRouteListStorage;
import seedu.address.storage.user.XmlUsersListStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlOrderBookStorage orderBookStorage = new XmlOrderBookStorage(getTempFilePath("ab"));
        XmlUsersListStorage usersListStorage = new XmlUsersListStorage(getTempFilePath("users"));
        XmlRouteListStorage routeListStorage = new XmlRouteListStorage(getTempFilePath("rl"));
        XmlDeliverymenListStorage deliverymenListStorage =
                new XmlDeliverymenListStorage(getTempFilePath("dl"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(orderBookStorage, routeListStorage, usersListStorage,
                deliverymenListStorage, userPrefsStorage);
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
    public void orderBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlOrderBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlOrderBookStorageTest} class.
         */
        OrderBook original = getTypicalOrderBook();
        storageManager.saveOrderBook(original);
        ReadOnlyOrderBook retrieved = storageManager.readOrderBook().get();
        assertEquals(original, new OrderBook(retrieved));
    }

    @Test
    public void getOrderBookFilePath() {
        assertNotNull(storageManager.getOrderBookFilePath());
    }

    @Test
    public void handleOrderBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlOrderBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlRouteListStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlUsersListStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlDeliverymenListStorageExceptionThrowingStub(Paths.get("dummy2")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleOrderBookChangedEvent(new OrderBookChangedEvent(new OrderBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlOrderBookStorageExceptionThrowingStub extends XmlOrderBookStorage {

        public XmlOrderBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlUsersListStorageExceptionThrowingStub extends XmlUsersListStorage {

        public XmlUsersListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveUsersList(ReadOnlyUsersList usersList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRouteListStorageExceptionThrowingStub extends XmlRouteListStorage {

        public XmlRouteListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRouteList(ReadOnlyRouteList routeList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * Another Stub class to throw an exception when the save method is called
     */
    class XmlDeliverymenListStorageExceptionThrowingStub extends XmlDeliverymenListStorage {

        public XmlDeliverymenListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveDeliverymenList(DeliverymenList deliverymenList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
