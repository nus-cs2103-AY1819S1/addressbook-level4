package seedu.restaurant.logic.commands.sales;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.sales.DisplayRecordListRequestEvent;
import seedu.restaurant.commons.events.ui.sales.JumpToRecordListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.sales.SalesRecord;

//@@author HyperionNKJ
/**
 * Selects a record identified using its displayed index from the menu.
 */
public class SelectSalesCommand extends Command {

    public static final String COMMAND_WORD = "select-sales";

    public static final String COMMAND_ALIAS = "ss";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the record identified by the index number used in the displayed sales list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RECORD_SUCCESS = "Selected Record: %s";

    private final Index targetIndex;

    public SelectSalesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<SalesRecord> filteredRecordList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= filteredRecordList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }
        SalesRecord selectedRecord = filteredRecordList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new DisplayRecordListRequestEvent());
        EventsCenter.getInstance().post(new JumpToRecordListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RECORD_SUCCESS,
                selectedRecord.getDate().toString() + " " + selectedRecord.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectSalesCommand // instanceof handles nulls
                    && targetIndex.equals(((SelectSalesCommand) other).targetIndex)); // state check
    }
}
