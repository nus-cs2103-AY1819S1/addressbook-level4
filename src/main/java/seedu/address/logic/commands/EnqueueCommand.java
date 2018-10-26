package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;


/**
 * Enqueues a given patient (for consultation).
 */
public class EnqueueCommand extends Command {
    public static final String COMMAND_WORD = "enqueue";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Enqueues the selected patient "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENQUEUE_PATIENT_SUCCESS = "Patient %1$s successfully assigned to the queue. ";

    public static final String MESSAGE_PERSON_NOT_PATIENT = "Person %1$s is not a patient. ";

    public static final String MESSAGE_PATIENT_IS_CURRENTLY_QUEUING = "Patient %1$s is currently in the queue. ";

    private final Index index;

    /**
     * @param index The index of the patient as in the displayed patient list.
     */
    public EnqueueCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person patientToEnqueue = lastShownList.get(index.getZeroBased());

        if (!(patientToEnqueue instanceof Patient)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_PATIENT, patientToEnqueue.getName()));
        }

        model.enqueue((Patient) patientToEnqueue);

        model.updateFilteredPersonList(model.PREDICATE_SHOW_ALL_PATIENTS_IN_QUEUE);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ENQUEUE_PATIENT_SUCCESS, patientToEnqueue.getName()));
    }
}
