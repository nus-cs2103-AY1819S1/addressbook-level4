package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
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
public class RegisterCommand extends QueueCommand {
    public static final String COMMAND_WORD = "register";
    public static final String COMMAND_ALIAS = "reg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueue a patient into the queue. "
            + "Parameters: "
            + "NAME ";

    public static final String MESSAGE_SUCCESS = "Added ";
    public static final String MESSAGE_DUPLICATE_PERSON = "Person is already in queue!";

    private final Index targetIndex;

    public RegisterCommand(Index index) {
        requireNonNull(index);
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, ServedPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patient patientToRegister = lastShownList.get(targetIndex.getZeroBased());
        if (patientQueue.contains(patientToRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        int position = patientQueue.enqueue(patientToRegister);
        return new CommandResult(MESSAGE_SUCCESS + patientToRegister.toNameAndIc()
                + " with Queue Number: " + position + "\n" + patientQueue.displayQueue());
    }
}


