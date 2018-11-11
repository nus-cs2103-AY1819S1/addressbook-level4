package seedu.restaurant.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_RECORDS;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;

/**
 * Reverts the {@code model}'s restaurant book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_ALIAS = "r";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoRestaurantBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoRestaurantBook();
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
