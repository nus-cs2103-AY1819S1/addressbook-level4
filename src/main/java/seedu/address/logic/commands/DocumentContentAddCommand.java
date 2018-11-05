package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MC_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REFERRAL_CONTENT;

import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowCurrentPatientViewEvent;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;

/**
 * Edits the details of an existing patient in the address book.
 */
public class DocumentContentAddCommand extends QueueCommand {

    public static final String COMMAND_WORD = "adddocument";
    public static final String COMMAND_ALIAS = "ad";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds document content to the Current Patient "
            + "Parameters: "
            + "[" + PREFIX_NOTE_CONTENT + "Notes] "
            + "[" + PREFIX_REFERRAL_CONTENT + "Referral] "
            + "[" + PREFIX_MC_CONTENT + "No. of days of Medical Leave]";
    public static final String MESSAGE_MC_DAYS_CONSTRAINTS = "Number of days of MC should be a positive integer.";
    public static final String MESSAGE_SUCCESS = "Patient document Updated: ";
    public static final String MESSAGE_NO_CURRENT_PATIENT = "There is no Current Patient. Use the serve command first.";


    private final DocumentContentDescriptor documentContentDescriptor;

    /**
     * @param documentContentDescriptor details to edit the patient with
     */
    public DocumentContentAddCommand(DocumentContentDescriptor documentContentDescriptor) {
        requireNonNull(documentContentDescriptor);

        this.documentContentDescriptor = new DocumentContentDescriptor(documentContentDescriptor);
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        if (!currentPatient.hasCurrentPatient()) {
            throw new CommandException(MESSAGE_NO_CURRENT_PATIENT);
        }

        Patient patient = currentPatient.getPatient();
        if (!model.hasPerson(patient)) {
            currentPatient.finishServing();
            EventsCenter.getInstance().post(new ShowPatientListEvent());
            throw new CommandException(Messages.MESSAGE_PATIENT_MODIFIED_WHILE_IN_QUEUE);
        }
        updateCurrentPatientDocument(currentPatient, documentContentDescriptor);

        EventsCenter.getInstance().post(new ShowCurrentPatientViewEvent(currentPatient));

        return new CommandResult(MESSAGE_SUCCESS + currentPatient.toNameAndIc()
                + currentPatient.toDocumentInformation());
    }


    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static void updateCurrentPatientDocument(CurrentPatient currentPatientToEdit,
                                                     DocumentContentDescriptor documentContentDescriptor) {
        assert currentPatientToEdit != null;

        if (documentContentDescriptor.getMcContent().isPresent()) {
            currentPatientToEdit.addMcContent(documentContentDescriptor.getMcContent().get());
        }

        if (documentContentDescriptor.getReferralContent().isPresent()) {
            currentPatientToEdit.addReferralContent(documentContentDescriptor.getReferralContent().get());
        }

        if (documentContentDescriptor.getNoteContent().isPresent()) {
            currentPatientToEdit.addNoteContent(documentContentDescriptor.getNoteContent().get());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DocumentContentAddCommand // instanceof handles nulls
                && documentContentDescriptor.equals(((DocumentContentAddCommand) other).documentContentDescriptor));
    }

    /**
     * Stores the details to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class DocumentContentDescriptor {
        private String mcContent;
        private String referralContent;
        private String noteContent;

        public DocumentContentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public DocumentContentDescriptor(DocumentContentDescriptor toCopy) {
            setMcContent(toCopy.mcContent);
            setReferralContent(toCopy.referralContent);
            setNoteContent(toCopy.noteContent);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(mcContent, referralContent, noteContent);
        }

        public void setMcContent(String mcContent) {
            this.mcContent = mcContent;
        }

        public Optional<String> getMcContent() {
            return Optional.ofNullable(mcContent);
        }

        public void setReferralContent(String referralContent) {
            this.referralContent = referralContent;
        }

        public Optional<String> getReferralContent() {
            return Optional.ofNullable(referralContent);
        }

        public void setNoteContent(String noteContent) {
            this.noteContent = noteContent;
        }

        public Optional<String> getNoteContent() {
            return Optional.ofNullable(noteContent);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DocumentContentDescriptor)) {
                return false;
            }

            // state check
            DocumentContentDescriptor e = (DocumentContentDescriptor) other;

            return this.getMcContent().equals(e.getMcContent())
                    && getReferralContent().equals(e.getReferralContent())
                    && getNoteContent().equals(e.getNoteContent());
        }
    }
}
