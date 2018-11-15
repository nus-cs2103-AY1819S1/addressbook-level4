package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;

/**
 * Displays the list of served patients to the user.
 */
public class DisplayServedPatientsCommand extends QueueCommand {
    public static final String COMMAND_WORD = "servedpatients";
    public static final String COMMAND_ALIAS = "showsp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the list of served patients";

    public static final String MESSAGE_SUCCESS = "Served patient list: ";
    public static final String MESSAGE_EMPTY_LIST = "Served patient list is empty!";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (servedPatientList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        EventsCenter.getInstance().post(new ShowPatientListEvent());

        return new CommandResult(servedPatientList.displayServedPatientList());
    }
}
