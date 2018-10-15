package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;


/**
 * Registers a patient to the end of the current queue.
 */
public class RemoveCommand extends QueueCommand {
    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a patient from the queue at the specified"
            + "index.";

    public static final String MESSAGE_SUCCESS = "Removed ";
    public static final String MESSAGE_EMPTY_QUEUE = "Patient Queue is empty!";
    public static final String MESSAGE_INDEX_OUT_OF_BOUND = "No patient at specified position";

    private final Index targetIndex;

    public RemoveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, ServedPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (patientQueue.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_QUEUE);
        } else if (targetIndex.getOneBased() > patientQueue.getQueueLength()) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUND);
        }

        Patient patient = patientQueue.removeAtIndex(targetIndex.getZeroBased());
        return new CommandResult(MESSAGE_SUCCESS + patient.toNameAndIc() + " from Queue.");
    }
}
