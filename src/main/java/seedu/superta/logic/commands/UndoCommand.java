package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoSuperTaClient()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoSuperTaClient();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
