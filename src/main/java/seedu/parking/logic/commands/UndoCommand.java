package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.parking.model.Model.PREDICATE_SHOW_ALL_CARPARK;

import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;

/**
 * Reverts the {@code model}'s car park finder to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoCarparkFinder()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoCarparkFinder();
        model.updateFilteredCarparkList(PREDICATE_SHOW_ALL_CARPARK);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
