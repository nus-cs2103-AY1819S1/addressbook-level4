package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.RideStatusPredicate;

/**
 * View rides in thane park that are filtered by their status.
 */
public class ViewStatusCommand extends Command {

    public static final String COMMAND_WORD = "viewstatus";

    public static final String MESSAGE_USAGE = "Find all the rides with the status that the user "
            + "inputs (case-insensitive) and displays them as a list with index numbers.\n"
            + "PARAMETERS: [STATUS]\n"
            + "Example: " + COMMAND_WORD + " open";

    private final RideStatusPredicate predicate;

    public ViewStatusCommand(RideStatusPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredRideList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_RIDES_LISTED_OVERVIEW, model.getFilteredRideList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewStatusCommand // instanceof handles nulls
                && predicate.equals(((ViewStatusCommand) other).predicate)); // state check
    }
}
