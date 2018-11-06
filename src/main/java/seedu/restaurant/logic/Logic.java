package seedu.restaurant.logic;

import javafx.collections.ObservableList;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.sales.SalesRecord;

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

    /** Returns an unmodifiable view of the filtered list of account */
    ObservableList<Account> getFilteredAccountList();

    /** Returns an unmodifiable view of the filtered list of ingredients */
    ObservableList<Ingredient> getFilteredIngredientList();

    /** Returns an unmodifiable view of the filtered list of items */
    ObservableList<Item> getFilteredItemList();

    /** Returns an unmodifiable view of the filtered list of reservations */
    ObservableList<Reservation> getFilteredReservationList();

    /** Returns an unmodifiable view of the filtered list of sales records */
    ObservableList<SalesRecord> getFilteredRecordList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
