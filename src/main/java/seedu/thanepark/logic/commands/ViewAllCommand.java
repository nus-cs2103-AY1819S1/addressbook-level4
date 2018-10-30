package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.model.Model.PREDICATE_SHOW_ALL_RIDES;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;

/**
 * Lists all persons in the thanepark book to the user.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "viewall";

    public static final String MESSAGE_SUCCESS = "Viewed all rides";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
