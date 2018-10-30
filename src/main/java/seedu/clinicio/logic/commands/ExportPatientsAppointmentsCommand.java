package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

//@@author arsalanc-v2

/**
 * Exports all patients' appointment records to a csv file.
 */
public class ExportPatientsAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "exportpatientsappointments";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String resultMessage = model.exportPatientsAppointments();
        return new CommandResult(resultMessage);
    }
}

