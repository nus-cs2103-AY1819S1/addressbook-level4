package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Status;

/**
 * View all opened rides in thane park.
 */
public class QuickViewCommand extends Command {

    public static final String COMMAND_WORD = "quickView";

    public static final String MESSAGE_SUCCESS = "Opened rides listed!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredRideList(ride -> ride.getStatus().equals(Status.OPEN));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
