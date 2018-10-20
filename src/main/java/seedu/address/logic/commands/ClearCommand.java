package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ClinicIo;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;

/**
 * Clears the ClinicIO.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ClinicIO has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireNonNull(model);
        model.resetData(new ClinicIo());
        model.commitClinicIo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
