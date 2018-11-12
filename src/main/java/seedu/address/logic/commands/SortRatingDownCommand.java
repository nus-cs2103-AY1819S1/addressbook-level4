package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sort the list of deck in department's lexicographical order.
 */
public class SortRatingDownCommand extends Command {

    public static final String COMMAND_WORD = "sortRatingDown";
    public static final String COMMAND_ALIAS = "sortRating";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by rating from highest to lowest";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortByRatingDown();
        model.commitSsenisub();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
