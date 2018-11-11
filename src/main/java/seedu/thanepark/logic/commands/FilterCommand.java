package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.RideContainsConditionPredicate;

/**
 * Filters a list of all rides in the thane park which attributes matches the predicate the user inputs.
 * Predicate value must be an integer.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the rides with the predicate that the user "
            + "inputs and displays the result as a list with index numbers\n"
            + "Parameters: [PREFIX] [PREDICATE]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MAINTENANCE + "< 1000";

    private final RideContainsConditionPredicate predicate;

    public FilterCommand(RideContainsConditionPredicate predicate) {
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
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
