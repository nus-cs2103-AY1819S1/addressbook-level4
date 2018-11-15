package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;

/**
 * Inserts a patient to the specified position in queue.
 */
public class InsertCommand extends QueueCommand {
    public static final String COMMAND_WORD = "insert";
    public static final String COMMAND_ALIAS = "ins";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Inserts a patient into the queue. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_POSITION + "POSITION ";

    public static final String MESSAGE_SUCCESS = "Inserted ";
    public static final String MESSAGE_INVALID_POSITION = "Invalid position!";
    public static final String MESSAGE_DUPLICATE_PERSON = "Person is already in queue!";

    private final Index targetIndex;
    private final Index targetPosition;

    public InsertCommand(Index index, Index position) {
        requireNonNull(index);
        requireNonNull(position);
        targetIndex = index;
        targetPosition = position;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patient patientToRegister = lastShownList.get(targetIndex.getZeroBased());

        if (patientQueue.contains(patientToRegister) || currentPatient.isPatient(patientToRegister)
                || servedPatientList.containsPatient(patientToRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        int actualPosition;

        //If the position is greater than the queue length, then we just append the patient to the back of the queue.
        if (targetPosition.getZeroBased() > patientQueue.getQueueLength()) {
            actualPosition = patientQueue.enqueue(patientToRegister);
        } else {
            patientQueue.addAtIndex(patientToRegister, targetPosition.getZeroBased());
            actualPosition = targetPosition.getOneBased();
        }

        EventsCenter.getInstance().post(new ShowPatientListEvent());

        return new CommandResult(MESSAGE_SUCCESS + patientToRegister.toNameAndIc()
                + " with Queue Number: " + actualPosition + "\n" + patientQueue.displayQueue());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsertCommand // instanceof handles nulls
                && targetIndex.equals(((InsertCommand) other).targetIndex)
                && targetPosition.equals(((InsertCommand) other).targetPosition));
    }
}


