package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;

/**
 * Registers a patient to the end of the current queue.
 */
public class RegisterCommand extends QueueCommand {
    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueue a patient into the queue. "
            + "Parameters: "
            + "NAME ";

    public static final String MESSAGE_SUCCESS = "Added ";
    public static final String MESSAGE_DUPLICATE_PERSON = "Person is already in queue!";

    private final String toRegister;

    public RegisterCommand(String patient) {
        requireNonNull(patient);
        toRegister = patient;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CommandHistory history)
            throws CommandException {
        requireNonNull(patientQueue);

        if (patientQueue.contains(toRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        int position = patientQueue.enqueue(toRegister);
        return new CommandResult(MESSAGE_SUCCESS + toRegister + " with Queue Number: " + position
            + "\n" + patientQueue.displayQueue());
    }
}
