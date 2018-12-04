package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Ride;

/**
 * Deletes a ride identified using it's displayed index from the thanepark book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the ride identified by the index number used in the displayed ride list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RIDE_SUCCESS = "Deleted Ride: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ride> lastShownList = model.getFilteredRideList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
        }

        Ride rideToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRide(rideToDelete);
        model.commitThanePark();
        return new CommandResult(String.format(MESSAGE_DELETE_RIDE_SUCCESS, rideToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
