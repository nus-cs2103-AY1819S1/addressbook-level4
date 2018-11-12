package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DisplayProfileEvent;
import seedu.address.commons.events.model.DisplayWeekScheduleEvent;
import seedu.address.commons.events.model.ListJioCommandEvent;
import seedu.address.commons.events.model.ListingDebtCommandEvent;
import seedu.address.commons.events.model.ListingFriendCommandEvent;
import seedu.address.commons.events.model.ListingGroupCommandEvent;
import seedu.address.commons.events.model.UserDataChangedEvent;
import seedu.address.commons.events.model.UserLoggedOutEvent;
import seedu.address.model.accounting.Amount;
import seedu.address.model.accounting.Debt;
import seedu.address.model.accounting.DebtId;
import seedu.address.model.accounting.DebtStatus;
import seedu.address.model.friend.Friendship;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.UserReview;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.UniqueSchedule;
import seedu.address.model.timetable.Week;
import seedu.address.model.user.Password;
import seedu.address.model.user.RestaurantReview;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final FilteredList<Jio> filteredJios;
    private final FilteredList<Group> filteredGroups;
    private List<Date> displayedDates;
    private UserData userData;
    private boolean isLoggedIn = false;
    private User currentUser = null;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs and userData.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs,
                        UserData userData) {
        super();
        requireAllNonNull(addressBook, userPrefs, userData);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredRestaurants = new FilteredList<>(versionedAddressBook.getRestaurantList());
        this.userData = userData;
        filteredJios = new FilteredList<>(userData.getJios());
        if (currentUser != null) {
            filteredGroups = new FilteredList<>(FXCollections.observableArrayList(currentUser.getGroups()));
        } else {
            filteredGroups = new FilteredList<>(FXCollections.observableArrayList(userData.getGroups()));
        }
        displayedDates = UniqueSchedule.generateDefaultWeekSchedule();
    }

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs, userData, and a current user.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs,
                        UserData userData, User user) {
        super();
        requireAllNonNull(addressBook, userPrefs, userData);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredRestaurants = new FilteredList<>(versionedAddressBook.getRestaurantList());
        this.userData = userData;
        filteredJios = new FilteredList<>(userData.getJios());
        if (currentUser != null) {
            filteredGroups = new FilteredList<>(FXCollections.observableArrayList(currentUser.getGroups()));
        } else {
            filteredGroups = new FilteredList<>(FXCollections.observableArrayList(userData.getGroups()));
        }
        displayedDates = UniqueSchedule.generateDefaultWeekSchedule();

        this.currentUser = user;
        this.isLoggedIn = true;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new UserData());
    }

    //=========== Model Manager Miscellaneous Methods =+==========================================================

    @Override
    public UserData getUserData() {
        return this.userData;
    }

    @Override
    public boolean isCurrentlyLoggedIn() {
        return this.isLoggedIn;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserDataChanged() {
        raise(new UserDataChangedEvent(userData, currentUser));
    }

    //=========== Model Manager Restaurants Methods =+============================================================

    @Override
    public boolean hasRestaurant(Restaurant restaurant) {
        requireNonNull(restaurant);
        return versionedAddressBook.hasRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        versionedAddressBook.removeRestaurant(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        versionedAddressBook.addRestaurant(restaurant);
        updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);

        versionedAddressBook.updateRestaurant(target, editedRestaurant);
        indicateAddressBookChanged();
    }

    @Override
    public void displayProfile() {
        raise(new DisplayProfileEvent(currentUser));
    }

    //=========== Filtered Restaurant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Restaurant} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return FXCollections.unmodifiableObservableList(filteredRestaurants);
    }
    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        filteredRestaurants.setPredicate(predicate);
    }



    //=========== Model Manager User Methods ====================================================================

    @Override
    public boolean hasUser(Username username) {
        requireNonNull(username);
        return userData.hasUser(username);
    }

    @Override
    public boolean verifyLogin(Username username, Password password) {
        requireAllNonNull(username);
        requireAllNonNull(password);
        return userData.verifyLogin(username, password);
    }

    @Override
    public void addUser(User user) {
        userData.addUser(user);
        indicateUserDataChanged();
    }

    @Override
    public void loginUser(User user) {
        requireAllNonNull(user);
        this.currentUser = user;
        this.isLoggedIn = true;
        // reset the schedule.
        resetDisplayedDates();
    }

    @Override
    public void loginUser(Username username) {
        requireAllNonNull(username);
        User userToLogin = userData.getUsernameUserHashMap().get(username);
        requireAllNonNull(userToLogin);
        this.currentUser = userToLogin;
        this.isLoggedIn = true;
    }

    @Override
    public void logoutUser() {
        this.currentUser = null;
        this.isLoggedIn = false;
        resetDisplayedDates();
        raise(new UserLoggedOutEvent());
    }

    //================ Debt methods =================================
    @Override
    public void addUserReview(Restaurant restaurant, Rating rating, WrittenReview writtenReview) {
        UserReview newUserReview = new UserReview(currentUser.getUsername(), rating, writtenReview);
        // Add to AddressBook
        versionedAddressBook.addUserReviewToRestaurant(restaurant, newUserReview);
        indicateAddressBookChanged();
        // Add to UserData
        RestaurantReview newRestauratnReview = new RestaurantReview(restaurant.getName(), rating, writtenReview);
        currentUser.addRestaurantReviewToUser(newRestauratnReview);
        indicateUserDataChanged();
    }

    //=========== Friendship methods =============================================================================
    @Override
    public boolean hasUsernameSentRequest(Username friendUsername) {
        User friend = userData.getUser(friendUsername);
        Username myUsername = currentUser.getUsername();
        List<Friendship> friendRequestLists = friend.getFriendRequests();
        for (Friendship f: friendRequestLists) {
            if (f.getFriendUsername().equals(myUsername)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasDebtId(DebtId debtId) {
        boolean result = false;
        for (int i = 0; i < currentUser.getDebts().size(); i++) {
            if (currentUser.getDebts().get(i).getDebtId().equals(debtId)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean matchDebtToAmount(DebtId debtId, Amount amount) {
        int count = 0;
        boolean check = false;
        for (int i = 0; i < currentUser.getDebts().size(); i++) {
            if (currentUser.getDebts().get(i).getDebtId().equals(debtId)) {
                count = i;
                check = true;
                break;
            }
        }
        if (check) {
            if (currentUser.getDebts().get(count).getAmount().equals(amount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matchDebtToUser(DebtId debtId, Username user) {
        int count = 0;
        boolean check = false;
        for (int i = 0; i < currentUser.getDebts().size(); i++) {
            if (currentUser.getDebts().get(i).getDebtId().equals(debtId)) {
                count = i;
                check = true;
                break;
            }
        }
        if (check) {
            if (currentUser.getDebts().get(count).getDebtor().getUsername().equals(user)
                    || currentUser.getDebts().get(count).getCreditor().getUsername().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasUsernameFriendRequest(Username friendUsername) {
        List<Friendship> friendRequestsLists = currentUser.getFriendRequests();
        for (Friendship f: friendRequestsLists) {
            if (f.getFriendUsername().equals(friendUsername)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matchDebtToStatus(DebtId debtId, DebtStatus status) {
        int count = 0;
        boolean check = false;
        for (int i = 0; i < currentUser.getDebts().size(); i++) {
            if (currentUser.getDebts().get(i).getDebtId().equals(debtId)) {
                count = i;
                check = true;
                break;
            }
        }
        if (check) {
            if (currentUser.getDebts().get(count).getDebtStatus() == status) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean debtExist(Username debtorUsername) {
        for (Debt d: currentUser.getDebts()) {
            if (d.getCreditor().getUsername().equals(currentUser.getUsername())
                    && d.getDebtor().getUsername().equals(debtorUsername)
                    && d.getDebtStatus().equals(DebtStatus.ACCEPTED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allowToClear(Username debtorUsername, Amount amount) {
        for (Debt d: currentUser.getDebts()) {
            if (d.getCreditor().getUsername().equals(currentUser.getUsername())
                    && d.getDebtor().getUsername().equals(debtorUsername)
                    && d.getDebtStatus().equals(DebtStatus.ACCEPTED)
                    && d.getAmount().toDouble() >= amount.toDouble()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addDebt(Username debtorUsername, Amount amount) {
        User debtor = userData.getUser(debtorUsername);
        this.currentUser.addDebt(debtor, amount);
        indicateUserDataChanged();
    }

    @Override
    public void addGroupDebt(Name groupName, Amount amount) {
        Group toAdd = userData.getGroupHashmap().get(groupName);
        currentUser.addGroupDebt(currentUser, toAdd, amount);
        indicateUserDataChanged();
    }

    @Override
    public void clearDebt(Username debtorUsername, Amount amount) {
        User debtor = userData.getUser(debtorUsername);
        currentUser.clearDebt(debtor, amount);
        indicateUserDataChanged();
    }

    @Override
    public void acceptedDebtRequest(Username creditorUsername, Amount amount, DebtId debtId) {
        User creditor = userData.getUser(creditorUsername);
        currentUser.acceptedDebtRequest(creditor, amount, debtId);
        indicateUserDataChanged();
    }

    @Override
    public void deleteDebtRequest(Username creditorUsername, Amount amount, DebtId debtId) {
        User creditor = userData.getUser(creditorUsername);
        currentUser.deleteDebtRequest(creditor, amount, debtId);
        indicateUserDataChanged();
    }

    @Override
    public ObservableList<Friendship> getFriendsList() {
        return FXCollections.observableArrayList(currentUser.getFriends());
    }

    @Override
    public ObservableList<Friendship> getFriendRequestsList() {
        return FXCollections.observableArrayList(currentUser.getFriendRequests());
    }

    @Override
    public ObservableList<Debt> getDebtList() {
        return FXCollections.observableArrayList(currentUser.getDebts());
    }

    @Override
    public ObservableList<Debt> getCreditorList() {
        return FXCollections.observableArrayList(currentUser.getCreditor());
    }
    @Override
    public ObservableList<Debt> getDebtorList() {
        return FXCollections.observableArrayList(currentUser.getDebtor());
    }

    @Override
    public ObservableList<Debt> getDebtRequestReceived() {
        return FXCollections.observableArrayList(currentUser.getDebtRequestReceived());
    }

    @Override
    public ObservableList<Debt> getDebtRequestSent() {
        return FXCollections.observableArrayList(currentUser.getDebtRequestSent());
    }

    //=============Friend & Group methods ====================

    @Override
    public boolean hasUsernameFriend(Username friendUsername) {
        List<Friendship> friendsLists = currentUser.getFriends();
        for (Friendship f: friendsLists) {
            if (f.getFriendUsername().equals(friendUsername)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addFriend(Username friendUsername) {
        User friendUser = userData.getUser(friendUsername);
        currentUser.addFriend(friendUser);
        indicateUserDataChanged();
    }

    @Override
    public void acceptFriend(Username friendUsername) {
        User friendUser = userData.getUser(friendUsername);
        currentUser.acceptFriendRequest(friendUser);
        indicateUserDataChanged();
    }

    @Override
    public boolean isSameAsCurrentUser(Username username) {
        User toCheck = userData.getUser(username);
        return toCheck.equals(currentUser);
    }

    @Override
    public void deleteFriend(Username friendUsername) {
        User friendUser = userData.getUser(friendUsername);
        currentUser.deleteFriend(friendUser);
        indicateUserDataChanged();
    }

    @Override
    public boolean hasGroup(Name groupName) {
        requireNonNull(groupName);
        return userData.hasGroup(groupName);
    }

    @Override
    public void addGroup(Name groupName) {
        Group group = new Group(groupName, currentUser);
        currentUser.addGroup(group);
        userData.addGroup(group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        indicateUserDataChanged();
    }

    @Override
    public boolean hasGroupRequest(Name groupName) {
        List<Group> listGroups = currentUser.getGroupRequests();
        for (Group g: listGroups) {
            if (groupName.equals(g.getGroupName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUniqueUsernames(List<Username> listUsernames) {
        int size = listUsernames.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (listUsernames.get(i).equals(listUsernames.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isInGroup(Name groupName) {
        List<Group> listGroups = currentUser.getGroups();
        for (Group g: listGroups) {
            if (groupName.equals(g.getGroupName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void acceptGroupRequest(Name groupName) {
        Group group = userData.getGroupHashmap().get(groupName);
        currentUser.acceptGroupRequest(group);
        indicateUserDataChanged();
    }

    @Override
    public void addPendingUsersGroup(Pair<Name, List<Username>> pair) {
        List<Username> listUsernames = pair.getValue();
        Name groupName = pair.getKey();
        Group toAddto = userData.getGroupHashmap().get(groupName);
        List<User> listUsers = new ArrayList<>();
        listUsernames.forEach(username -> listUsers.add(userData.getUser(username)));
        toAddto.addMembers(listUsers);
        indicateUserDataChanged();
    }

    @Override
    public boolean isAllValidUsers(List<Username> listUsernames) {
        for (Username u: listUsernames) {
            if (!userData.hasUser(u)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasUsersInGroup(Pair<Name, List<Username>> pair) {
        List<Username> listUsernames = pair.getValue();
        Name groupName = pair.getKey();
        Group originalGroup = userData.getGroupHashmap().get(groupName);
        List<User> acceptedUsers = originalGroup.getAcceptedUsers();

        // checking to see if any Users are already in the list of acceptedUsers
        return acceptedUsers.stream()
                .anyMatch(accUser -> listUsernames.stream()
                        .anyMatch(user -> accUser.getUsername().equals(user)));
    }

    @Override
    public boolean hasRequestForUsers(Pair<Name, List<Username>> pair) {
        List<Username> listUsernames = pair.getValue();
        Name groupName = pair.getKey();
        Group originalGroup = userData.getGroupHashmap().get(groupName);
        List<User> pendingUsers = originalGroup.getPendingUsers();

        // checking to see if any Users are already in the list of pendingUsers
        return pendingUsers.stream()
                .anyMatch(accUser -> listUsernames.stream()
                        .anyMatch(user -> accUser.getUsername().equals(user)));
    }

    @Override
    public void deleteGroup(Name groupName) {
        Group toDelete = userData.getGroupHashmap().get(groupName);
        currentUser.deleteGroup(toDelete);
        indicateUserDataChanged();
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public void deleteGroupRequest(Name groupName) {
        Group toDelete = userData.getGroupHashmap().get(groupName);
        currentUser.deleteGroupRequest(toDelete);
        indicateUserDataChanged();
    }

    @Override
    public void deleteFriendRequest(Username friendUsername) {
        User friendUser = userData.getUser(friendUsername);
        currentUser.deleteFriendRequest(friendUser);
        indicateUserDataChanged();
    }

    @Override
    public ObservableList<Group> getGroupRequestList() {
        return FXCollections.observableArrayList(currentUser.getGroupRequests());
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return FXCollections.observableArrayList(currentUser.getGroups());
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
    }

    // =================== Timetable methods ===============================

    @Override
    public void blockDateForCurrentUser(Date date) {
        requireNonNull(date);
        currentUser.blockDateOnSchedule(date);
        indicateUserDataChanged();
    }

    @Override
    public void freeDateForCurrentUser(Date date) {
        requireNonNull(date);
        currentUser.freeDateOnSchedule(date);
        indicateUserDataChanged();
    }


    @Override
    public boolean hasDateForCurrentUser(Date date) {
        requireNonNull(date);
        return currentUser.hasDateOnSchedule(date);
    }

    @Override
    public void displayUserWeekSchedule(String header, Week weekNumber) {
        updateDisplayedDatesUserWeek(weekNumber);
        displayWeekSchedule(header, displayedDates);
    }

    @Override
    public void displayFreeDatesForGroupAndWeek(String header, Name groupName, Week week) {
        Group group = userData.getGroupHashmap().get(groupName);
        List<User> groupMembers = group.getAcceptedUsers();
        List<UniqueSchedule> allSchedules = new ArrayList<>();
        // getting all the schedules.
        groupMembers.stream().forEach(user -> allSchedules.add(user.getBusySchedule()));
        List<Date> datesForDisplay = UniqueSchedule.findFreeDatesAmongSchedulesForWeek(allSchedules, week);
        displayWeekSchedule(header, datesForDisplay);
    }


    private void updateDisplayedDatesUserWeek(Week weekNumber) {
        requireNonNull(weekNumber);
        this.displayedDates = currentUser.getFreeDatesForWeek(weekNumber);

    }

    private void displayWeekSchedule(String header, List<Date> dates) {
        raise(new DisplayWeekScheduleEvent(header, dates));
    }

    private void resetDisplayedDates() {
        this.displayedDates = UniqueSchedule.generateDefaultWeekSchedule();
    }

    //=========== Jio methods ===============================================================================

    @Override
    public ObservableList<Jio> getJioList() {
        return FXCollections.observableArrayList(userData.getJios());
    }

    @Override
    public void updateFilteredJioList(Predicate<Jio> predicate) {
        requireNonNull(predicate);
        filteredJios.setPredicate(predicate);
    }

    @Override
    public boolean hasJio(Jio jio) {
        requireNonNull(jio);
        return userData.hasJio(jio);
    }

    @Override
    public boolean hasJioName(Name jioName) {
        requireNonNull(jioName);
        return userData.hasJioName(jioName);
    }

    @Override
    public void removeJioOfName(Name jioName) {
        requireNonNull(jioName);
        userData.removeJioOfName(jioName);
        //updateFilteredJioList(PREDICATE_SHOW_ALL_JIOS);
        indicateUserDataChanged();
    }

    @Override
    public void createJio(Jio jio) {
        requireNonNull(jio);
        jio.addUser(currentUser);
        jio.setCreator(currentUser);

        //add group to jio if any
        if (jio.isGroupJio()) {
            Group group = userData.getGroupHashmap().get(jio.getGroupName());
            group.getAcceptedUsers().stream().forEach(user -> jio.addUser(user));
        }

        userData.addJio(jio);
        updateFilteredJioList(PREDICATE_SHOW_ALL_JIOS);
        indicateUserDataChanged();
    }

    @Override
    public boolean isCurrentUserInJioOfName(Name jioName) {
        requireNonNull(jioName);
        return userData.isUserInJioOfName(jioName, currentUser);
    }

    @Override
    public void addCurrentUserToJioOfName(Name jioName) {
        requireNonNull(jioName);
        userData.addUserToJioOfName(jioName, currentUser);
        indicateUserDataChanged();
    }

    @Override
    public boolean isCurrentUserCreatorOfJio(Name jioName) {
        requireNonNull(jioName);
        return userData.isCreatorOfJio(jioName, currentUser);
    }

    @Override
    public void listJio(ObservableList<Jio> list) {
        raise(new ListJioCommandEvent(list));
    }

    @Override
    public void clearJio() {
        //userData.getJios().forEach(jio -> userData.removeJioOfName(jio.getName()));
        userData.clearJio();
        filteredJios.setPredicate(jio -> false);
    }

    //=========== Undo/Redo/Commit ===============================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
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

        return userData.equals(other.userData)
                && ((currentUser == null && other.currentUser == null) || currentUser.equals(other.currentUser))
                && versionedAddressBook.equals(other.versionedAddressBook)
                && filteredRestaurants.equals(other.filteredRestaurants);
    }

    @Override
    public void debtListing(ObservableList<Debt> list) {
        raise(new ListingDebtCommandEvent(list));
    }

    @Override
    public void friendListing(ObservableList<Friendship> list) {
        raise(new ListingFriendCommandEvent(list));
    }

    @Override
    public void groupListing(ObservableList<Group> list) {
        raise(new ListingGroupCommandEvent(list));
    }

}
