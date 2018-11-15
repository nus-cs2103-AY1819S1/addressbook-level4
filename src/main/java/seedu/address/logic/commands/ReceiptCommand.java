package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowDocumentWindowRequestEvent;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.document.Receipt;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

/**
 * Generates a receipt for {@code Patient} specified by {@code index} that appears in the GUI and in a pdf.
 */
public class ReceiptCommand extends QueueCommand {
    public static final String COMMAND_WORD = "receipt";
    public static final String COMMAND_ALIAS = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a receipt for the patient in the specified"
            + " index. Includes information like the date of visit, consultation fee, medicine dispensed, cost, etc. \n"
            + "Example: " + COMMAND_WORD + "<Served patient's index>";

    public static final String MESSAGE_GENERATE_RECEIPT_SUCCESS = "Receipt generated for patient!";

    private Receipt receipt;
    private final Index index;

    /**
     * Creates a ReceiptCommand for the {@code servedPatient} specified by {@code index}
     */
    public ReceiptCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(servedPatientList);

        if (index.getZeroBased() >= servedPatientList.getServedPatientListLength()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ServedPatient servedPatient = servedPatientList.selectServedPatient(index);

        Patient patient = servedPatient.getPatient();

        //If the patient's details has been altered (which is unlikely), remove the patient from the queue.
        if (!model.hasPerson(patient)) {
            servedPatientList.removeAtIndex(index.getZeroBased());
            EventsCenter.getInstance().post(new ShowQueueInformationEvent(patientQueue,
                    servedPatientList, currentPatient));
            throw new CommandException(String.format(
                    Messages.MESSAGE_PATIENT_MODIFIED_WHILE_IN_QUEUE, patient.getName()));
        }

        receipt = new Receipt(servedPatient);
        receipt.generateDocument();

        EventsCenter.getInstance().post(new ShowPatientListEvent());
        EventsCenter.getInstance().post(new ShowDocumentWindowRequestEvent(receipt));
        return new CommandResult(String.format(MESSAGE_GENERATE_RECEIPT_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReceiptCommand // instanceof handles nulls
                && index.equals(((ReceiptCommand) other).index)); // state check
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
