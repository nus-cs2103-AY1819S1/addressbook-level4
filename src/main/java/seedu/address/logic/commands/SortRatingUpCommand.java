package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sort the list of deck in department's lexicographical order.
 */
public class SortRatingUpCommand extends Command {

    public static final String COMMAND_WORD = "sortRatingUp";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by rating from lowest to highest";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortByRatingUp();
        model.commitSsenisub();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
