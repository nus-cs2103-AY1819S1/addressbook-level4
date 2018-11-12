package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounting.Debt;
import seedu.address.model.friend.Friendship;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Restaurant;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of restaurants */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /** Returns an unmodifiable view of the filtered list of friends */
    ObservableList<Friendship> getFriendRequestsList();
    ObservableList<Friendship> getFriendsList();

    /** Returns an unmodifiable view of the filtered list of groups */
    ObservableList<Group> getGroupList();

    /** Returns an unmodifiable view of the filtered list of groups */
    ObservableList<Group> getGroupRequestList();

    /** Returns an unmodifiable view of the filtered list of jios */
    ObservableList<Jio> getJioList();

    /** Returns an unmodifiable view of the filtered list of debts */
    ObservableList<Debt> getDebtList();
    ObservableList<Debt> getCreditorList();
    ObservableList<Debt> getDebtorList();
    ObservableList<Debt> getDebtRequestReceived();
    ObservableList<Debt> getDebtRequestSent();
    void debtListing(ObservableList<Debt> debt);
    void friendListing(ObservableList<Friendship> list);
    void groupListing(ObservableList<Group> list);

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    boolean isCurrentlyLoggedIn();
}
