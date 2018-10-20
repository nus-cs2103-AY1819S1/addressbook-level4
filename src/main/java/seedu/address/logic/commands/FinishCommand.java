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
 * Finish serving the Current Patient.
 * Move Patient from Current Patient to ServedPatientList.
 */
public class FinishCommand extends QueueCommand {
    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finish serving Current Patient.";

    public static final String MESSAGE_SUCCESS = "Finish Serving patient: ";
    public static final String MESSAGE_EMPTY_CURRENT_PATIENT = "There is no current patient!";

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(patientQueue);

        if (!currentPatient.hasCurrentPatient()) {
            throw new CommandException(MESSAGE_EMPTY_CURRENT_PATIENT);
        }

        String currentPatientNameIc = currentPatient.toNameAndIc();
        servedPatientList.addServedPatient(currentPatient.finishServing());

        EventsCenter.getInstance().post(new ShowPatientListEvent());

        return new CommandResult(MESSAGE_SUCCESS + currentPatientNameIc);
    }
}
