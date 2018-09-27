package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;

/**
 * List the current queue to the user.
 */
public class DisplayQueueCommand extends QueueCommand {
    public static final String COMMAND_WORD = "queue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the current queue";

    public static final String MESSAGE_SUCCESS = "Queue: ";
    public static final String MESSAGE_EMPTY_QUEUE = "Patient Queue is empty!";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CommandHistory history)
            throws CommandException {
        requireNonNull(patientQueue);

        if (patientQueue.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_QUEUE);
        }

        return new CommandResult(patientQueue.displayQueue());
    }
}
