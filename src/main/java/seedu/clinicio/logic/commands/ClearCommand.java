package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.Model;

/**
 * Clears the ClinicIO.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ClinicIO has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new ClinicIo());
        model.commitClinicIo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
