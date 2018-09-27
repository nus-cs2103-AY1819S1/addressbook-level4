package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;

import static java.util.Objects.requireNonNull;

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
    public CommandResult execute(Model model, PatientQueue patientQueue, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if(patientQueue.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_QUEUE);
        }

        String patient = patientQueue.dequeue();
        return new CommandResult(MESSAGE_SUCCESS + patient);
    }
}
