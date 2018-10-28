package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;

/**
 * Reverts the {@code recipeModel}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    private final Model model;

    public RedoCommand(Model model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAppContent()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAppContent();
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
