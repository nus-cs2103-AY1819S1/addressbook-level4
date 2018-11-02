package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

//@@author arsalanc-v2

/**
 * Exports all patients' consultation records to a csv file.
 */
public class ExportPatientsConsultationsCommand extends Command {

    public static final String COMMAND_WORD = "exportpatientsconsultations";

    @Override

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String resultMessage = model.exportPatientsConsultations();
        return new CommandResult(resultMessage);
    }
}

