package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.DeliverymenListChangedEvent;
import seedu.address.commons.events.model.OrderBookChangedEvent;
import seedu.address.commons.events.model.RouteListChangedEvent;
import seedu.address.commons.events.model.UsersListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.storage.deliveryman.DeliverymenListStorage;
import seedu.address.storage.route.RouteListStorage;
import seedu.address.storage.user.UsersListStorage;

/**
 * Manages storage of OrderBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RouteListStorage routeListStorage;
    private OrderBookStorage orderBookStorage;
    private DeliverymenListStorage deliverymenListStorage;
    private UserPrefsStorage userPrefsStorage;
    private UsersListStorage usersListStorage;

    public StorageManager(OrderBookStorage orderBookStorage, RouteListStorage routeListStorage,
            UsersListStorage usersListStorage, DeliverymenListStorage deliverymenListStorage,
            UserPrefsStorage userPrefsStorage) {
        super();
        this.orderBookStorage = orderBookStorage;
        this.routeListStorage = routeListStorage;
        this.deliverymenListStorage = deliverymenListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.usersListStorage = usersListStorage;
    }

    // ================ UsersList methods ==============================

    @Override
    public Path getUsersListFilePath() {
        return usersListStorage.getUsersListFilePath();
    }

    @Override
    public Optional<ReadOnlyUsersList> readUsersList() throws DataConversionException, IOException {
        return readUsersList(usersListStorage.getUsersListFilePath());
    }

    @Override
    public Optional<ReadOnlyUsersList> readUsersList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return usersListStorage.readUsersList(filePath);
    }

    @Override
    public void saveUsersList(ReadOnlyUsersList usersList) throws IOException {
        saveUsersList(usersList, usersListStorage.getUsersListFilePath());
    }

    @Override
    public void saveUsersList(ReadOnlyUsersList usersList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        usersListStorage.saveUsersList(usersList, filePath);
    }

    @Override
    @Subscribe
    public void handleUsersListChangedEvent(UsersListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveUsersList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ OrderBook methods ==============================

    @Override
    public Path getOrderBookFilePath() {
        return orderBookStorage.getOrderBookFilePath();
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(orderBookStorage.getOrderBookFilePath());
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return orderBookStorage.readOrderBook(filePath);
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException {
        saveOrderBook(orderBook, orderBookStorage.getOrderBookFilePath());
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        orderBookStorage.saveOrderBook(orderBook, filePath);
    }


    @Override
    @Subscribe
    public void handleOrderBookChangedEvent(OrderBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveOrderBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ RouteList methods ==============================

    @Override
    public Path getRouteListFilePath() {
        return routeListStorage.getRouteListFilePath();
    }

    @Override
    public Optional<ReadOnlyRouteList> readRouteList() throws DataConversionException, IOException {
        return readRouteList(routeListStorage.getRouteListFilePath());
    }

    @Override
    public Optional<ReadOnlyRouteList> readRouteList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return routeListStorage.readRouteList(filePath);
    }

    @Override
    public void saveRouteList(ReadOnlyRouteList routeList) throws IOException {
        saveRouteList(routeList, routeListStorage.getRouteListFilePath());
    }

    @Override
    public void saveRouteList(ReadOnlyRouteList routeList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        routeListStorage.saveRouteList(routeList, filePath);
    }

    // ================ DeliverymenList methods ==============================

    @Override
    public Path getDeliverymenListFilePath() {
        return deliverymenListStorage.getDeliverymenListFilePath();
    }

    @Override
    public Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException {
        return readDeliverymenList(deliverymenListStorage.getDeliverymenListFilePath());
    }

    @Override
    public Optional<DeliverymenList> readDeliverymenList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return deliverymenListStorage.readDeliverymenList(filePath);
    }

    @Override
    public void saveDeliverymenList(DeliverymenList deliverymenList) throws IOException {
        saveDeliverymenList(deliverymenList, deliverymenListStorage.getDeliverymenListFilePath());
    }

    @Override
    public void saveDeliverymenList(DeliverymenList deliverymenList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        deliverymenListStorage.saveDeliverymenList(deliverymenList, filePath);
    }


    @Override
    @Subscribe
    public void handleRouteListChangedEvent(RouteListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRouteList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleDeliverymenListChangedEvent(DeliverymenListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveDeliverymenList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
