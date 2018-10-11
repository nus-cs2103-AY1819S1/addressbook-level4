package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
 * API of the Storage component
 */
public interface Storage extends OrderBookStorage, RouteListStorage, UserPrefsStorage,
        UsersListStorage, DeliverymenListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getOrderBookFilePath();

    @Override
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    @Override
    void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleOrderBookChangedEvent(OrderBookChangedEvent abce);

    @Override
    Path getRouteListFilePath();

    @Override
    Optional<ReadOnlyRouteList> readRouteList() throws DataConversionException, IOException;

    /**
     * Saves the current version of the Route List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRouteListChangedEvent(RouteListChangedEvent abce);

    @Override
    Path getUsersListFilePath();

    @Override
    Optional<ReadOnlyUsersList> readUsersList() throws DataConversionException, IOException;

    @Override
    void saveUsersList(ReadOnlyUsersList usersList) throws IOException;

    /**
     * Saves the current version of the UsersList to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUsersListChangedEvent(UsersListChangedEvent ulce);

    @Override
    Path getDeliverymenListFilePath();

    @Override
    Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException;

    @Override
    void saveDeliverymenList(DeliverymenList deliverymenList) throws IOException;

    /**
    * Saves the current version of the Address Book to the hard disk.
    *   Creates the data file if it is missing.
    * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
    */
    void handleDeliverymenListChangedEvent(DeliverymenListChangedEvent abce);
}
