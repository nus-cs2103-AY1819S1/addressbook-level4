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
     * Constructs a servedPatient object from a patient object to extract all the information necessary for printing
     * a document.
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

    public String addNoteContent(String content) {
        NoteContent += " " + content;
        return NoteContent;
    }

    public String addReferralContent(String content) {
        ReferralContent += " " + content;
        return ReferralContent;
    }

    public String addMcContent(String content) {
        McContent += " " + content;
        return McContent;
    }
}
