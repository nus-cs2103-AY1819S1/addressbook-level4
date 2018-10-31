package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.parking.commons.core.Messages;
import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;

/**
 * Deletes a car park identified using it's displayed index from the car park finder.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ABBREVIATION = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the car park identified by the index number used in the displayed car park list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CARPARK_SUCCESS = "Deleted Car park: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Carpark> lastShownList = model.getFilteredCarparkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
        }

        Carpark carparkToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCarpark(carparkToDelete);
        model.commitCarparkFinder();
        return new CommandResult(String.format(MESSAGE_DELETE_CARPARK_SUCCESS, carparkToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
