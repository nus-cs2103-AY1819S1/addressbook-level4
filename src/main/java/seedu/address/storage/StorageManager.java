package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.FoodZoomChangedEvent;
import seedu.address.commons.events.model.UsersListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.storage.user.UsersListStorage;

/**
 * Manages storage of OrderBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FoodZoomStorage foodZoomStorage;
    private UserPrefsStorage userPrefsStorage;
    private UsersListStorage usersListStorage;

    public StorageManager(UsersListStorage usersListStorage, FoodZoomStorage foodZoomStorage,
            UserPrefsStorage userPrefsStorage) {
        super();
        this.foodZoomStorage = foodZoomStorage;
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

    @Override
    public Path getFoodZoomFilePath() {
        return foodZoomStorage.getFoodZoomFilePath();
    }

    //================================ data read methods ======================================
    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(foodZoomStorage.getFoodZoomFilePath());
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return foodZoomStorage.readOrderBook(filePath);
    }

    @Override
    public Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException {
        return readDeliverymenList(foodZoomStorage.getFoodZoomFilePath());
    }

    @Override
    public Optional<DeliverymenList> readDeliverymenList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return foodZoomStorage.readDeliverymenList(filePath);
    }

    //================================ data save methods ======================================

    @Override
    public void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList) throws IOException {
        saveFoodZoom(orderBook, deliverymenList, foodZoomStorage.getFoodZoomFilePath());
    }

    @Override
    public void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList, Path filePath) throws
            IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        foodZoomStorage.saveFoodZoom(orderBook, deliverymenList, filePath);
    }

    @Override
    @Subscribe
    public void handleFoodZoomChangedEvent(FoodZoomChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveFoodZoom(event.orderBook, event.deliverymenList);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
