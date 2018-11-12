package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.model.accounting.Amount;
import seedu.address.model.accounting.Debt;
import seedu.address.model.accounting.DebtId;
import seedu.address.model.accounting.DebtStatus;
import seedu.address.model.friend.Friendship;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Week;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * The API of the Model component.
 */
public interface Model {

    //=========== Model Manager Miscellaneous Methods =+==========================================================

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Restaurant> PREDICATE_SHOW_ALL_RESTAURANTS = unused -> true;

    Predicate<Jio> PREDICATE_SHOW_ALL_JIOS = unused -> true;

    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    UserData getUserData();

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a user is currently logged into the Makan Book
     */
    boolean isCurrentlyLoggedIn();

    //=========== Model Manager Restaurants Methods =+============================================================

    /**
     * Returns true if a restaurant with the same identity as {@code restaurant} exists in the address book.
     */
    boolean hasRestaurant(Restaurant restaurant);

    /**
     * Deletes the given restaurant.
     * The restaurant must exist in the address book.
     */
    void deleteRestaurant(Restaurant target);

    /**
     * Adds the given restaurant.
     * {@code restaurant} must not already exist in the address book.
     */
    void addRestaurant(Restaurant restaurant);

    /**
     * Replaces the given restaurant {@code target} with {@code editedRestaurant}.
     * {@code target} must exist in the address book.
     * The restaurant identity of {@code editedRestaurant}
     * must not be the same as another existing restaurant in the address book.
     */
    void updateRestaurant(Restaurant target, Restaurant editedRestaurant);

    /**
     * Displays the Profile of a User.
     */
    void displayProfile();

    //=========== Filtered Restaurant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the filtered restaurant list
     */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /**
     * Updates the filter of the filtered restaurant list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRestaurantList(Predicate<Restaurant> predicate);

    //=========== Model Manager User Methods =+===================================================================

    /**
     * Returns true if a user with the {@code username} exists in User Data.
     */
    boolean hasUser(Username username);

    /**
     * Returns true if the {@code password} matches the password of the user with {@code username}.
     */
    boolean verifyLogin(Username username, Password password);

    /**
     * Adds the given {@code user}. The user must not already exist in the User Data.
     */
    void addUser(User user);

    /**
     * Allows the {@code user} to be logged in immediately after they sign up.
     * There should not be anyone currently logged in before.
     */
    void loginUser(User user);

    /**
     * Allows a User to log into the account with {@code username}.
     * There should not be anyone currently logged in before.
     */
    void loginUser(Username username);

    /**
     * Allows a current User to logout of their account.
     */
    void logoutUser();

    /**
     * Creates a UserReview and a RestaurantReview for storage into both RestaurantAddressBook and UserData.
     */
    void addUserReview(Restaurant restaurant, Rating rating, WrittenReview writtenReview);

    //=========== Model Manager User Methods =+===================================================================

    /**
     * Checks whether currently logged in user has sent a friend request to User with
     * username friendUsername
     * Returns true if such a friend request is found
     * @param friendUsername
     */
    boolean hasUsernameSentRequest(Username friendUsername);

    /**
     * Checks whether the currently logged in user is friends with the User with
     * username friendUsername
     * Returns true if such a friendship exists and they are already friends
     * @param friendUsername
     */
    boolean hasUsernameFriend(Username friendUsername);

    /**
     * Checks whether user with username friendUsername has previously sent a friend
     * request to the currently logged in user.
     * Returns true if such a pending friendship exists
     * @param friendUsername
     */
    boolean hasUsernameFriendRequest(Username friendUsername);

    /**
     * adds the currently logged in user to the list of pending friends under the User
     * with username friendUsername
     * @param friendUsername
     */
    void addFriend(Username friendUsername);

    /**
     * accepts the pending friendship from the user with username friendUsername
     * deletes the pending friendship under the currently logged in user
     * adds an accepted friendship under both the currently logged in user and user with
     * username friendUsername
     * @param friendUsername
     */
    void acceptFriend(Username friendUsername);

    /**
     * returns true if the user with Username username is the same as the currently
     * logged in user.
     * @param username
     */
    boolean isSameAsCurrentUser(Username username);

    /**
     * deletes friendship between user with username friendUsername and currently logged
     * in user.
     * friendship is deleted under both users.
     * @param friendUsername
     */
    void deleteFriend(Username friendUsername);

    /**
     * deletes the pending friendship between user with username friendUsername and
     * currently logged in user.
     * @param friendUsername
     */
    void deleteFriendRequest(Username friendUsername);

    /**
     * Returns an ObservableList of friends of the currently logged in user.
     */
    ObservableList<Friendship> getFriendsList();

    /**
     * Returns an ObservableList of friend requests received by the currently
     * logged in user.
     */
    ObservableList<Friendship> getFriendRequestsList();

    /**
     * Checks whether there is another group in Makan Book has the same name
     * Returns true if there is another group with the same name
     * @param group
     * @return
     */
    boolean hasGroup(Name group);

    /**
     * Checks whether the currently logged in user has a group request from the
     * group with name groupName.
     * @param groupName
     */
    boolean hasGroupRequest(Name groupName);

    /**
     * Adds group with groupName to list of all groups in Makan Book.
     * @param groupName
     */
    void addGroup(Name groupName);

    /**
     * Allows currently logged in user to accept groupRequest of the group with name
     * groupName.
     * Adds the group to the list of groups for the currently logged in user
     * Deletes the group from list of group requests for the currently logged in user
     * @param groupName
     */
    void acceptGroupRequest(Name groupName);

    /**
     * Checks whether the currently logged in user is part of the group with name
     * groupName
     * @param groupName
     */
    boolean isInGroup(Name groupName);

    /**
     * Adds the users corresponding to the usernames in the list of usernames in pair
     * to the group with name as specified in pair.
     * @param pair
     */
    void addPendingUsersGroup(Pair<Name, List<Username>> pair);

    /**
     * Checks whether all usernames in listUsernames correspond to actual users
     * Returns true if all usernames correspond to some user
     * @param listUsernames
     */
    boolean isAllValidUsers(List<Username> listUsernames);

    /**
     * Checks whether all usernames in listUsernames are unique
     * Returns true if all usernames are unique
     * @param listUsernames
     * @return
     */
    boolean isUniqueUsernames(List<Username> listUsernames);

    boolean hasUsersInGroup(Pair<Name, List<Username>> pair);

    boolean hasRequestForUsers(Pair<Name, List<Username>> pair);

    void deleteGroup(Name groupName);

    void deleteGroupRequest(Name groupName);

    /**
     * Returns an unmodifiable view of the group list
     */
    ObservableList<Group> getGroupList();

    ObservableList<Group> getGroupRequestList();

    void updateFilteredGroupList(Predicate<Group> predicate);

    //============ Timetable commands ==========================

    void blockDateForCurrentUser(Date date);

    void freeDateForCurrentUser(Date date);

    boolean hasDateForCurrentUser(Date date);

    void displayUserWeekSchedule(String header, Week weekNumber);

    void displayFreeDatesForGroupAndWeek(String header, Name groupName, Week week);

    //=========== Jio methods ===============================================================================

    ObservableList<Jio> getJioList();

    void updateFilteredJioList(Predicate<Jio> predicate);

    boolean hasJio(Jio jio);

    boolean hasJioName(Name jioName);

    void removeJioOfName(Name jioName);

    void createJio(Jio jio);

    boolean isCurrentUserInJioOfName(Name jioName);

    void addCurrentUserToJioOfName(Name jioName);

    boolean isCurrentUserCreatorOfJio(Name jioName);

    void listJio(ObservableList<Jio> list);

    void clearJio();

    //=========== Undo/Redo/Commit ===============================================================================

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    //========== Model Manager Debt methods ======================================================================
    /**
     * Returns true if login user of the model has a debt with input debtId
     */
    boolean hasDebtId(DebtId debtId);

    /**
     * Returns true if login user of the model has a debt with input debtId,
     * and that debt's amount is equal to the input amount
     */
    boolean matchDebtToAmount(DebtId debtId, Amount amount);

    /**
     * Returns true if login user of the model has a debt with input debtId,
     * and that debt's creditor/debtor's username is equal to the input username
     */
    boolean matchDebtToUser(DebtId debtId, Username username);

    /**
     * Returns true if login user of the model has a debt with input debtId,
     * and that debt's status is equal to the input status
     */
    boolean matchDebtToStatus(DebtId debtId, DebtStatus status);

    /**
     * Returns true if login user of the model has a debt with the user
     * with username equals to input debtorUsername
     */
    boolean debtExist(Username debtorUsername);

    /**
     * Returns true if the debt can be clear by login user.
     * Check if a debt with login user as creditor,
     * user with input debtorUsername as debtor,
     * status is Accpted and
     * the amount is less than or equal to the input amount
     */
    boolean allowToClear(Username debtorUsername, Amount amount);

    /**
     * Method to create and add a debt(with Pending status) to other user.
     */
    void addDebt(Username debtorUsername, Amount amount);

    /**
     * Method to create and add a debt(with Pending status) to other users in a group.
     */
    void addGroupDebt(Name groupName, Amount amount);

    /**
     * Method for the creditor to clear an amount of the debtor.
     */
    void clearDebt(Username debtorUsername, Amount amount);

    /**
     * Method for debtor to accepted a debt request received.
     */
    void acceptedDebtRequest(Username creditorUsername, Amount amount, DebtId debtId);

    /**
     * Method for debtor to reject and delete a debt request received.
     */
    void deleteDebtRequest(Username creditorUsername, Amount amount, DebtId debtId);

    /**
     * Method for getting all the Debts(regardless the status).
     */
    ObservableList<Debt> getDebtList();

    /**
     * Method for getting all the Creditor.
     */
    ObservableList<Debt> getCreditorList();

    /**
     * Method for getting all the Debtor.
     */
    ObservableList<Debt> getDebtorList();

    /**
     * Method for getting all the Debt Request Received.
     */
    ObservableList<Debt> getDebtRequestReceived();

    /**
     * Method for getting all the Debt Request Sent.
     */
    ObservableList<Debt> getDebtRequestSent();

    /**
     * Method for listing all the debt in the input list on list panel
     */
    void debtListing(ObservableList<Debt> list);

    void friendListing(ObservableList<Friendship> list);

    void groupListing(ObservableList<Group> list);

}

