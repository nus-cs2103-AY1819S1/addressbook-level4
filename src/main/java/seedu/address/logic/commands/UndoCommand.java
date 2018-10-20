package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;

/**
 * Reverts the {@code model}'s ClinicIO to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoClinicIo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoClinicIo();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
