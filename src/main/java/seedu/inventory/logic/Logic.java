package seedu.inventory.logic;

import javafx.collections.ObservableList;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

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

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Item> getFilteredItemList();

    /** Returns an unmodifiable view of the filtered list of purchase order */
    ObservableList<PurchaseOrder> getFilteredPurchaseOrderList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
