package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.ServedPatient;

/**
 *
 */
public class DisplayServedPatientsCommand extends QueueCommand {
    public static final String COMMAND_WORD = "servedPatients";
    public static final String COMMAND_ALIAS = "showsp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the list of served patients";

    public static final String MESSAGE_SUCCESS = "Served patient list: ";
    public static final String MESSAGE_EMPTY_LIST = "Served patient list is empty!";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, ServedPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (servedPatientList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        return new CommandResult(servedPatientList.displayServedPatientList());
    }
}
