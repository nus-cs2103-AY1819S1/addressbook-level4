package seedu.address.logic.commands;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserData;
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
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void addRestaurant(Restaurant restaurant) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public UserData getUserData() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRestaurant(Restaurant restaurant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateRestaurant(Restaurant target, Restaurant editedRestaurant) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDebtId(DebtId debtId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean matchDebtToAmount(DebtId debtId, Amount amount) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean matchDebtToUser(DebtId debtId, Username user) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean matchDebtToStatus(DebtId debtId, DebtStatus status) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean debtExist(Username debtorUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean allowToClear(Username debtorUsername, Amount amount) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addDebt(Username debtorUsername, Amount amount) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addGroupDebt(Name groupName, Amount amount) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearDebt(Username debtorUsername, Amount amount) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public void acceptedDebtRequest(Username creditorUsername, Amount amount, DebtId debtId) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public void deleteDebtRequest(Username creditorUsername, Amount amount, DebtId debtId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Debt> getDebtList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Debt> getCreditorList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Debt> getDebtorList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Debt> getDebtRequestReceived() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Debt> getDebtRequestSent() {
        throw new AssertionError("This method should not be called.");
    }
    // To be Done Later, fake tests for now

    @Override
    public void debtListing(ObservableList<Debt> list) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void friendListing(ObservableList<Friendship> list) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void groupListing(ObservableList<Group> list) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isUniqueUsernames(List<Username> listUsernames) {
        throw new AssertionError("This method should not be called");
    }


    @Override
    public ObservableList<Jio> getJioList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredJioList(Predicate<Jio> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasJio(Jio jio) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasJioName(Name jioName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeJioOfName(Name jioName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void createJio(Jio jio) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isCurrentUserInJioOfName(Name jioName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCurrentUserToJioOfName(Name jioName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isCurrentUserCreatorOfJio(Name jioName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void listJio(ObservableList<Jio> list) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearJio() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addUser(User user) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasUser(Username username) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean verifyLogin(Username username, Password password) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isCurrentlyLoggedIn() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void loginUser(User user) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void loginUser(Username username) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void logoutUser() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void displayProfile() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addUserReview(Restaurant restaurant, Rating rating, WrittenReview writtenReview) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasUsernameSentRequest(Username friendUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasUsernameFriendRequest(Username friendusername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasUsernameFriend(Username friendUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFriend(Username friendUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void acceptFriend(Username friendUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isSameAsCurrentUser(Username username) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFriend(Username friendUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFriendRequest(Username friendUsername) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Friendship> getFriendRequestsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Friendship> getFriendsList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroup(Name group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addGroup(Name groupName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroupRequest(Name groupName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void acceptGroupRequest(Name groupName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isInGroup(Name groupName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPendingUsersGroup(Pair<Name, List<Username>> pair) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isAllValidUsers(List<Username> listUsernames) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasUsersInGroup(Pair<Name, List<Username>> pair) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRequestForUsers(Pair<Name, List<Username>> pair) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGroup(Name groupName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGroupRequest(Name groupName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getGroupRequestList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    // ========= timetable commands =========

    @Override
    public void blockDateForCurrentUser(Date date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void freeDateForCurrentUser(Date date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDateForCurrentUser(Date date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void displayUserWeekSchedule(String header, Week weekNumber) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void displayFreeDatesForGroupAndWeek(String header, Name groupName, Week week) {

        throw new AssertionError("This method should not be called.");
    }

}
