package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.Model;

/**
 * Reverts the {@code model}'s article list to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoArticleList()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoArticleList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
