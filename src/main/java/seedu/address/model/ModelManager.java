package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.FoodZoomChangedEvent;
import seedu.address.commons.events.model.UserLoggedInEvent;
import seedu.address.commons.events.model.UserLoggedOutEvent;
import seedu.address.commons.events.model.UsersListChangedEvent;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.deliveryman.VersionedDeliverymenList;
import seedu.address.model.order.Order;
import seedu.address.model.user.User;
import seedu.address.model.user.UserSession;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedOrderBook versionedOrderBook;
    private final VersionedUsersList versionedUsersList;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<User> filteredUsers;

    private final VersionedDeliverymenList versionedDeliverymenList;
    private final FilteredList<Deliveryman> filteredDeliverymen;

    private final UserSession userSession;

    /**
     * Initializes a ModelManager with the given addressBook, usersList and userPrefs.
     */
    public ModelManager(ReadOnlyOrderBook orderBook, ReadOnlyUsersList usersList,
                        DeliverymenList deliverymenList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(orderBook, userPrefs, deliverymenList);

        logger.fine("Initializing with order book: " + orderBook
                + " and users list " + usersList
                + " and deliverymen list " + deliverymenList
                + " and user prefs " + userPrefs);

        versionedOrderBook = new VersionedOrderBook(orderBook);
        versionedUsersList = new VersionedUsersList(usersList);
        versionedDeliverymenList = new VersionedDeliverymenList(deliverymenList);
        filteredOrders = new FilteredList<>(versionedOrderBook.getOrderList());
        filteredUsers = new FilteredList<>(versionedUsersList.getUserList());
        filteredDeliverymen = new FilteredList<>(versionedDeliverymenList.getDeliverymenList());

        userSession = new UserSession();

        logger.fine("Initializing with order book: " + orderBook
                + " and users list "
                + usersList
                + " and deliverymen list"
                + deliverymenList
                + " and user prefs "
                + userPrefs);
    }

    public ModelManager() {
        this(new OrderBook(), new UsersList(), new DeliverymenList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyOrderBook newData) {
        versionedOrderBook.resetData(newData);
        indicateAppChanged();
    }

    @Override
    public void resetDeliverymenData(DeliverymenList newData) {
        versionedDeliverymenList.resetData(newData);
        indicateAppChanged();
    }

    @Override
    public ReadOnlyOrderBook getOrderBook() {
        return versionedOrderBook;
    }

    @Override
    public DeliverymenList getDeliverymenList() {
        return versionedDeliverymenList;
    }

    /** Raises an event to indicate that there is an app change. */
    private void indicateAppChanged() {
        raise(new FoodZoomChangedEvent(versionedOrderBook, versionedDeliverymenList));
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateUsersListChanged() {
        raise(new UsersListChangedEvent(versionedUsersList));
    }

    /**
     * Raises an event to indicate user have logged in.
     */
    private void indicateUserLoggedIn(User user) {
        raise(new UserLoggedInEvent(user));
    }

    /**
     * Raises an event to indicate user have logged out.
     */
    private void indicateUserLoggedOut() {
        raise(new UserLoggedOutEvent());
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return versionedOrderBook.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order target) {
        versionedOrderBook.removeOrder(target);
        indicateAppChanged();
    }

    @Override
    public void addOrder(Order order) {
        versionedOrderBook.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        indicateAppChanged();
    }

    @Override
    public void updateOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        versionedOrderBook.updateOrder(target, editedOrder);
        indicateAppChanged();
    }

    // =========== Deliveryman methods ====================================

    @Override
    public boolean hasDeliveryman(Deliveryman deliveryman) {
        requireNonNull(deliveryman);
        return versionedDeliverymenList.hasDeliveryman(deliveryman);
    }

    @Override
    public void deleteDeliveryman(Deliveryman target) {
        versionedDeliverymenList.removeDeliveryman(target);
        indicateAppChanged();
    }

    @Override
    public void addDeliveryman(Deliveryman deliveryman) {
        versionedDeliverymenList.addDeliveryman(deliveryman);
        indicateAppChanged();
    }

    @Override
    public void updateDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
        requireAllNonNull(target, editedDeliveryman);

        versionedDeliverymenList.updateDeliveryman(target, editedDeliveryman);
        indicateAppChanged();
    }

    //=========== Filtered Orders List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedOrderBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return FXCollections.unmodifiableObservableList(filteredOrders);
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
        EventsCenter.getInstance().post(new BackToHomeEvent());
    }

    //=========== Filtered Deliveryman List Accessors =======================================================

    /**
     * Returns an unmodifiable view of the list of {@code Deliveryman} backed by the internal list
     * of {@code versionedDeliverymenList}
     */
    @Override
    public ObservableList<Deliveryman> getFilteredDeliverymenList() {
        return FXCollections.unmodifiableObservableList(filteredDeliverymen);
    }

    @Override
    public void updateFilteredDeliverymenList(Predicate<Deliveryman> predicate) {
        requireNonNull(predicate);
        filteredDeliverymen.setPredicate(predicate);
        EventsCenter.getInstance().post(new BackToHomeEvent());
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoOrderBook() {
        return versionedOrderBook.canUndo();
    }

    @Override
    public boolean canRedoOrderBook() {
        return versionedOrderBook.canRedo();
    }

    @Override
    public void undoOrderBook() {
        versionedOrderBook.undo();
        indicateAppChanged();
    }

    @Override
    public void redoOrderBook() {
        versionedOrderBook.redo();
        indicateAppChanged();
    }

    @Override
    public void commitOrderBook() {
        versionedOrderBook.commit();
    }


    //=========== Filtered User List Accessors =============================================================
    @Override
    public boolean hasUser(User user) {
        requireNonNull(user);
        return versionedUsersList.hasUser(user);
    }

    @Override
    public void addUser(User user) {
        versionedUsersList.addUser(user);
        updateFilteredUsersList(PREDICATE_SHOW_ALL_USERS);
        indicateUsersListChanged();
    }

    @Override
    public void commitUsersList() {
        versionedUsersList.commit();
    }

    @Override
    public void updateFilteredUsersList(Predicate<User> predicate) {
        requireNonNull(predicate);
        filteredUsers.setPredicate(predicate);
    }

    @Override
    public ObservableList<User> getFilteredUsersList() {
        return FXCollections.unmodifiableObservableList(filteredUsers);
    }

    @Override
    public boolean isRegisteredUser(User user) {
        requireNonNull(user);
        return versionedUsersList.isRegisteredUser(user);
    }

    @Override
    public ReadOnlyUsersList getUsersList() {
        return versionedUsersList;
    }

    @Override
    public boolean isUserLoggedIn() {
        return userSession.isUserAlreadyLoggedIn();
    }

    @Override
    public void storeUserInSession(User user) {
        userSession.setUserSession(user);
        indicateUserLoggedIn(user);
    }

    @Override
    public User getLoggedInUserDetails() {
        return userSession.getLoggedInUserDetails();
    }

    @Override
    public void clearUserInSession() {
        userSession.clearUserSession();
        indicateUserLoggedOut();
    }

    @Override
    public boolean canUndoDeliverymenList() {
        return versionedDeliverymenList.canUndo();
    }

    @Override
    public boolean canRedoDeliverymenList() {
        return versionedDeliverymenList.canRedo();
    }

    @Override
    public void undoDeliverymenList() {
        versionedDeliverymenList.undo();
        indicateAppChanged();
    }

    @Override
    public void redoDeliverymenList() {
        versionedDeliverymenList.redo();
        indicateAppChanged();
    }

    @Override
    public void commitDeliverymenList() {
        versionedDeliverymenList.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedOrderBook.equals(other.versionedOrderBook)
                && filteredOrders.equals(other.filteredOrders)
                && versionedUsersList.equals(other.versionedUsersList)
                && filteredUsers.equals(other.filteredUsers)
                && versionedDeliverymenList.equals(other.versionedDeliverymenList)
                && filteredDeliverymen.equals(other.filteredDeliverymen);
    }


}
