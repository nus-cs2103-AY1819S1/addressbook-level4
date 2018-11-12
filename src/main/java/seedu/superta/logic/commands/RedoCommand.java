package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;

/**
 * Reverts the {@code model}'s SuperTA client to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoSuperTaClient()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoSuperTaClient();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
