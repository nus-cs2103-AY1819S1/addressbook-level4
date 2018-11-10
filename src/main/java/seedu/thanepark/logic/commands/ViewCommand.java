package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.thanepark.commons.core.EventsCenter;
import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.commons.events.ui.JumpToListRequestEvent;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Ride;

/**
 * Views a ride identified using it's displayed index number from thane park.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the ride identified by the index number used in the displayed ride list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Viewed %1$s at index %2$s";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Ride> filteredRideList = model.getFilteredRideList();

        if (targetIndex.getZeroBased() >= filteredRideList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        String targetRideName = filteredRideList.get(targetIndex.getZeroBased()).getName().fullName;
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                targetRideName, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && targetIndex.equals(((ViewCommand) other).targetIndex)); // state check
    }
}
