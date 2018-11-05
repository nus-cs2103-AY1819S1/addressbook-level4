package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowDocumentWindowRequestEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.document.MedicalCertificate;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

//integrate select command
/**
 * Generates an MC for {@code Patient} specified by {@code index} that appears in the GUI and in a pdf.
 */
public class MedicalCertificateCommand extends QueueCommand {
    public static final String COMMAND_WORD = "mc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a medical certificate for the patient in"
            + " the specified index. Includes information like name and"
            + " NRIC of patient, and duration of medical leave. \n"
            + "Example: " + COMMAND_WORD + "<Served patient's index>";

    public static final String MESSAGE_GENERATE_MC_SUCCESS = "MC generated for patient!";
    public static final String MESSAGE_EMPTY_MC = "MC days not specified. MC cannot be generated.";

    private MedicalCertificate mc;
    private final Index index;

    /**
     * Creates a MedicalCertificateCommand for the {@code servedPatient} specified by {@code index}
     */
    public MedicalCertificateCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (index.getZeroBased() >= servedPatientList.getServedPatientListLength()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ServedPatient servedPatient = servedPatientList.selectServedPatient(index);

        Patient patient = servedPatient.getPatient();

        if (!model.hasPerson(patient)) {
            servedPatientList.removeAtIndex(index.getZeroBased());
            EventsCenter.getInstance().post(new ShowQueueInformationEvent(patientQueue,
                    servedPatientList, currentPatient));
            throw new CommandException(String.format(
                    Messages.MESSAGE_PATIENT_MODIFIED_WHILE_IN_QUEUE, patient.getName()));
        }

        if (servedPatient.getMcContent().equals("")) {
            throw new CommandException(MESSAGE_EMPTY_MC);
        }

        mc = new MedicalCertificate(servedPatient);
        mc.generateDocument();

        EventsCenter.getInstance().post(new ShowDocumentWindowRequestEvent(mc));

        return new CommandResult(String.format(MESSAGE_GENERATE_MC_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalCertificateCommand // instanceof handles nulls
                && index.equals(((MedicalCertificateCommand) other).index)); // state check
    }

    public MedicalCertificate getMc() {
        return mc;
    }
}
