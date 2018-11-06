package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;

/**
 * Display the document content of the Current Patient.
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

        Patient patient = currentPatient.getPatient();

        //If the patient's details has been altered (which is unlikely), remove the patient from the queue.
        if (!model.hasPerson(patient)) {
            currentPatient.finishServing();
            EventsCenter.getInstance().post(new ShowPatientListEvent());
            throw new CommandException(Messages.MESSAGE_PATIENT_MODIFIED_WHILE_IN_QUEUE);
        }

        return new CommandResult(currentPatient.toNameAndIc()
                + currentPatient.toDocumentInformation());
    }

}
