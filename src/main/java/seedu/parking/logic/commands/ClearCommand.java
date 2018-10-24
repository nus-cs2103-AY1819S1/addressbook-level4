package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.parking.logic.CommandHistory;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.Model;

/**
 * Clears the car park finder.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "car park finder has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new CarparkFinder());
        model.commitCarparkFinder();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
