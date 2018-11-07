package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.core.Messages;
import seedu.parking.commons.core.index.Index;
import seedu.parking.commons.events.ui.JumpToListRequestEvent;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;

/**
 * Selects a carpark identified using it's displayed index from the car park finder.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String FORMAT = "select INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the car park identified by the index number used in the displayed car park list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CARPARK_SUCCESS = "Selected car park: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Carpark> filteredCarparkList = model.getFilteredCarparkList();

        if (targetIndex.getZeroBased() >= filteredCarparkList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_CARPARK_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
