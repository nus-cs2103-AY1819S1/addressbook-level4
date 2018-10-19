package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.ServedPatient;


/**
 * Settle payment for a patient in the ServedPatientList.
 * Removes the patient from the ServedPatientList, ending the Patient's visit to the clinic.
 */
public class PaymentCommand extends QueueCommand {
    public static final String COMMAND_WORD = "payment";
    public static final String COMMAND_ALIAS = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the receipt and settle payment for patient. "
            + "Parameters: "
            + "Index ";

    public static final String MESSAGE_SUCCESS = "Payment successful! ";
    public static final String MESSAGE_INVALID_POSITION = "Invalid position! ";

    private final Index targetPosition;

    public PaymentCommand(Index position) {
        requireNonNull(position);
        targetPosition = position;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        if (targetPosition.getZeroBased() >= servedPatientList.size()) {
            throw new CommandException(MESSAGE_INVALID_POSITION);
        }

        ServedPatient servedPatient = servedPatientList.removeAtIndex(targetPosition.getZeroBased());

        //Do document processing, saving all documents to the patient before evicting him from the clinic.

        return new CommandResult(MESSAGE_SUCCESS + servedPatient.toNameAndIc());
    }
}
