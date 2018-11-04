package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;

/**
 * Display the details of an existing patient in the address book.
 */
public class DocumentContentDisplayCommand extends QueueCommand {

    public static final String COMMAND_WORD = "displaydocuments";
    public static final String COMMAND_ALIAS = "dd";

    public static final String MESSAGE_NO_CURRENT_PATIENT = "There is no Current Patient. Use the serve command first.";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        if (!currentPatient.hasCurrentPatient()) {
            throw new CommandException(MESSAGE_NO_CURRENT_PATIENT);
        }

        return new CommandResult(currentPatient.toNameAndIc()
                + currentPatient.toDocumentInformation());
    }

}
