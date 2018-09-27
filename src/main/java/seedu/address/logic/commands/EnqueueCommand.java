package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;

import static java.util.Objects.requireNonNull;

public class EnqueueCommand extends QueueCommand {
    public static final String COMMAND_WORD = "enqueue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueue a patient into the queue. "
            + "Parameters: "
            + "NAME ";

    public static final String MESSAGE_SUCCESS = "Added ";
    public static final String MESSAGE_DUPLICATE_PERSON = "Patient Queue is empty!";

    private final String toEnqueue;

    public EnqueueCommand(String patient) {
        requireNonNull(patient);
        toEnqueue = patient;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if(patientQueue.contains(toEnqueue)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        int position = patientQueue.enqueue(toEnqueue);
        return new CommandResult(MESSAGE_SUCCESS + toEnqueue + " with Queue Number: " + position
            + "\n" + patientQueue.displayQueue());
    }
}
