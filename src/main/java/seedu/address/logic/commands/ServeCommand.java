package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;


/**
 * Serve the patient that is first in queue.
 * Updates the queue to remove the first patient.
 */
public class ServeCommand extends QueueCommand {
    public static final String COMMAND_WORD = "serve";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Dequeues the first person in queue. ";

    public static final String MESSAGE_SUCCESS = "Serving patient: ";
    public static final String MESSAGE_EMPTY_QUEUE = "Patient Queue is empty!";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, ServedPatient currentPatient,
                                 CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (patientQueue.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_QUEUE);
        }

        Patient patient = patientQueue.dequeue();
        currentPatient = new ServedPatient(patient);
        return new CommandResult(MESSAGE_SUCCESS + patient.toNameAndIc());
    }
}
