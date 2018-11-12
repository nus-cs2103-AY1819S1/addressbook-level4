package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.FoodZoomChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.DeliverymenList;
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
        XmlUsersListStorage usersListStorage = new XmlUsersListStorage(getTempFilePath("users"));
        XmlFoodZoomStorage foodZoomStorage = new XmlFoodZoomStorage(getTempFilePath("foodzoom"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(usersListStorage, foodZoomStorage, userPrefsStorage);
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
    public void foodZoomReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlFoodZoomStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlFoodZoomStorageTest} class.
         */
        OrderBook original = getTypicalOrderBook();
        DeliverymenList deliverymenList = getTypicalDeliverymenList();
        storageManager.saveFoodZoom(original, deliverymenList);
        ReadOnlyOrderBook ordersRetrieved = storageManager.readOrderBook().get();
        assertEquals(original, new OrderBook(ordersRetrieved));
        DeliverymenList dmenRetrieved = storageManager.readDeliverymenList().get();
        assertEquals(deliverymenList, new DeliverymenList(dmenRetrieved));
    }

    @Test
    public void getFoodZoomFilePath() {
        assertNotNull(storageManager.getFoodZoomFilePath());
    }

    @Test
    public void handleOrderBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlUsersListStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlFoodZoomStorageExceptionThrowingStub(Paths.get("dummy2")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleFoodZoomChangedEvent(new FoodZoomChangedEvent(new OrderBook(), new DeliverymenList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlFoodZoomStorageExceptionThrowingStub extends XmlFoodZoomStorage {

        public XmlFoodZoomStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList, Path filePath) throws
                IOException {
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

}
