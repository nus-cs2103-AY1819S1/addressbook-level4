package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Uncompleted class, need to add more methods and fields.
 * Represents a patient that has already consulted the doctor in the address book.
 * It has all the necessary data needed to generate any object that inherits from Document.
 */
public class ServedPatient {

    private final Patient patient;
    private String NoteContent;
    private String ReferralContent;
    private String McContent;
    // add more fields as required

    /**
     * Constructs a servedPatient object from a patient object to extract all the information
     * necessary for printing a document.
     *
     * @param patient A valid patient.
     */
    public ServedPatient(Patient patient) {
        requireNonNull(patient);
        this.patient = patient;
    }

    public Name getName() {
        return patient.getName();
    }

    public IcNumber getIcNumber() {
        return patient.getIcNumber();
    }

    /**
     * Skeleton to add note content.
     */
    public String addNoteContent(String content) {
        NoteContent += " filler" + content;
        return NoteContent;
    }

    /**
     * Skeleton to add referral content.
     */
    public String addReferralContent(String content) {
        ReferralContent += " filler" + content;
        return ReferralContent;
    }

    /**
     * Skeleton to add Mc Content.
     */
    public String addMcContent(String content) {
        McContent += " filler" + content;
        return McContent;
    }

    /**
     * Returns the note content for the {@code served patient}.
     */
    public String getNoteContent() {
        return this.NoteContent;
    }
    /**
     * Returns the MC content for the {@code served patient}.
     */
    public String getMcContent() {
        return this.McContent;
    }

    /**
     * Returns the referral content for the {@code served patient}.
     */
    public String getReferralContent() {
        return this.ReferralContent;
    }


    /**
     * Console view of a served patient.
     * @return String representation of patient with Name and IcNumber.
     */
    public String toNameAndIc() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + " [")
                .append(getIcNumber() + "]");
        return builder.toString();
    }
}
