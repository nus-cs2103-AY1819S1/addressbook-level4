package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;


/**
 * Shows a list of patients currently in the queue.
 */
public class ShowPatientInQueueCommand extends Command {
    public static final String COMMAND_WORD = "showqueue";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Shows the list of patients "
            + "currently in the queue. ";
    public static final String MESSAGE_SHOW_PATIENTS_IN_QUEUE_SUCCESS = "Patients currently "
            + "in the queue successfully displayed. ";
    public static final String MESSAGE_NO_PATIENT_IN_QUEUE = "No patient in the queue. ";
    /**
     * Constructs the command.
     */
    public ShowPatientInQueueCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(model.PREDICATE_SHOW_ALL_PATIENTS_IN_QUEUE);
        model.commitAddressBook();

        if (model.hasPatientInPatientQueue()) {
            return new CommandResult(String.format(MESSAGE_SHOW_PATIENTS_IN_QUEUE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_PATIENT_IN_QUEUE));
        }
    }
}
