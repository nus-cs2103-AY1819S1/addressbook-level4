package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

//@@author arsalanc-v2

/**
 * Exports all patients' personal information to a csv file.
 */
public class ExportPatientsCommand extends Command {

    public static final String COMMAND_WORD = "exportpatients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String resultMessage = model.exportPatients();
        return new CommandResult(resultMessage);
    }
}
