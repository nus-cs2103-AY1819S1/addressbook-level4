package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;


/**
 * Finish serving the Current Patient.
 * Move Patient from Current Patient to ServedPatientList.
 * Also saves the note to the patient's MedicalRecord.
 */
public class FinishCommand extends QueueCommand {
    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finish serving Current Patient.";

    public static final String MESSAGE_SUCCESS = "Finish Serving patient: ";
    public static final String MESSAGE_EMPTY_CURRENT_PATIENT = "There is no current patient!";
    public static final String MESSAGE_EMPTY_NOTE = "Add a note to patient before using the finish command.\n"
            + "Use the adddocument command.\n"
            + DocumentContentAddCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (!currentPatient.hasCurrentPatient()) {
            throw new CommandException(MESSAGE_EMPTY_CURRENT_PATIENT);
        }

        if (currentPatient.getNoteContent() == "") {
            throw new CommandException(MESSAGE_EMPTY_NOTE);
        }

        String currentPatientNameIc = currentPatient.toNameAndIc();
        ServedPatient finishedPatient = currentPatient.finishServing();

        // Add finished patient to the servedPatientList
        servedPatientList.addServedPatient(finishedPatient);

        // Create a new patient object with the updated medical record
        Patient editedPatient = finishedPatient.createNewPatientWithUpdatedMedicalRecord();

        // Update this patient
        model.updatePerson(finishedPatient.getPatient(), editedPatient);

        EventsCenter.getInstance().post(new ShowPatientListEvent());
        EventsCenter.getInstance().post(new ShowQueueInformationEvent(patientQueue, servedPatientList, currentPatient));

        return new CommandResult(MESSAGE_SUCCESS + currentPatientNameIc);
    }
}
