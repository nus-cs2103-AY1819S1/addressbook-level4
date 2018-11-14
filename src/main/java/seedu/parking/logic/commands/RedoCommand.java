package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.parking.model.Model.PREDICATE_SHOW_ALL_CARPARK;

import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;

/**
 * Reverts the {@code model}'s car park finder to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoCarparkFinder()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoCarparkFinder();
        model.updateFilteredCarparkList(PREDICATE_SHOW_ALL_CARPARK);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
