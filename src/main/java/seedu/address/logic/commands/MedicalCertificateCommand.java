package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.MedicalCertificate;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.ServedPatient;

//integrate select command
/**
 * Generates an MC for {@code Patient} specified by {@code index} that appears in the GUI and in a pdf.
 */
public class MedicalCertificateCommand extends QueueCommand {
    public static final String COMMAND_WORD = "mc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a medical certificate for the patient in the specified"
            + " index. Includes information like name and NRIC of patient, and duration of medical leave. \n"
            + "Example: " + COMMAND_WORD + "<person's index>";

    public static final String MESSAGE_SUCCESS = "MC generated for patient!";

    private final Index index;
    private String generatedResult;

    /**
     * Creates a ReceiptCommand for the {@code servedPatient} specified by {@code index}
     */
    public MedicalCertificateCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, ServedPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= servedPatientList.getServedPatientListLength()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        final MedicalCertificate mc;

        ServedPatient servedPatient = servedPatientList.selectServedPatient(index);
        mc = new MedicalCertificate(servedPatient);
        generatedResult = mc.generate();

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(String.join("\n", MESSAGE_SUCCESS, generatedResult)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalCertificateCommand // instanceof handles nulls
                && index.equals(((MedicalCertificateCommand) other).index)); // state check
    }
}