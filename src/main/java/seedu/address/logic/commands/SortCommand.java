package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SortPanelViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Changes the UI view.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sorts the current view if applicable (only for tables).\n"
        + "Parameters: SORT_COLUMN_INDEX (starting from 1).\n"
        + "Example: " + COMMAND_WORD + " " + "1";

    public static final String MESSAGE_VIEW_SORTED_SUCCESS = "Sort attempted on current view. "
        + "Note that this will have no effect if the view is not sortable.";

    private final int[] colIdx;

    public SortCommand(int... colIdx) {
        requireNonNull(colIdx);
        this.colIdx = colIdx;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        EventsCenter.getInstance().post(new SortPanelViewEvent(colIdx));

        return new CommandResult(MESSAGE_VIEW_SORTED_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortCommand // instanceof handles nulls
                && Arrays.equals(colIdx, ((SortCommand) other).colIdx)); // state check
    }
}
