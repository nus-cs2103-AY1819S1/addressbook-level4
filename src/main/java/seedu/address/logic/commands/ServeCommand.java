package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowCurrentPatientViewEvent;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;


/**
 * Serve the patient that is first in queue.
 * Updates the queue to remove the first patient.
 */
public class ServeCommand extends QueueCommand {
    public static final String COMMAND_WORD = "serve";
    public static final String COMMAND_ALIAS = "ser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Dequeues the first person in queue. ";

    public static final String MESSAGE_SUCCESS = "Serving patient: ";
    public static final String MESSAGE_EMPTY_QUEUE = "Patient Queue is empty!";
    public static final String MESSAGE_CURRENT_PATIENT_EXISTS = "There is a current patient: ";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (patientQueue.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_QUEUE);
        }

        if (currentPatient.hasCurrentPatient()) {
            throw new CommandException(MESSAGE_CURRENT_PATIENT_EXISTS + currentPatient.toNameAndIc());
        }

        Patient patient = patientQueue.dequeue();

        if (!model.hasPerson(patient)) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_PATIENT_MODIFIED_WHILE_IN_QUEUE, patient.getName()));
        }

        currentPatient.assignPatient(patient);

        EventsCenter.getInstance().post(new ShowMedicineListEvent());
        EventsCenter.getInstance().post(new ShowCurrentPatientViewEvent(currentPatient));

        return new CommandResult(MESSAGE_SUCCESS + patient.toNameAndIc());
    }
}
