package seedu.restaurant.logic.commands.sales;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.sales.DisplayRecordListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * Deletes a sales record identified using its displayed index from the restaurant book.
 */
public class DeleteSalesCommand extends Command {

    public static final String COMMAND_WORD = "delete-sales";

    public static final String COMMAND_ALIAS = "des";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the sales record identified by the index number used in the displayed sales list.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SALES_SUCCESS = "Deleted Sales record: %1$s";

    private final Index targetIndex;

    public DeleteSalesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<SalesRecord> lastShownList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        SalesRecord recordToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRecord(recordToDelete);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayRecordListRequestEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_SALES_SUCCESS, recordToDelete));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSalesCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteSalesCommand) other).targetIndex)); // state check
    }
}
