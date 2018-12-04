package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.model.Model.PREDICATE_SHOW_ALL_RIDES;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.model.Model;

/**
 * Reverts the {@code model}'s thanepark book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoThanePark()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoThanePark();
        model.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
