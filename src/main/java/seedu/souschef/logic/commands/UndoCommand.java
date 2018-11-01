package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;

/**
 * Reverts the {@code recipeModel}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    private final Model model;

    public UndoCommand(Model model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAppContent()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAppContent();
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
