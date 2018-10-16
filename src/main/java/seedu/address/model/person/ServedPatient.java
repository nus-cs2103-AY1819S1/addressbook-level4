package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Uncompleted class, need to add more methods and fields.
 * Represents a patient that has already consulted the doctor in the address book.
 * It has all the necessary data needed to generate any object that inherits from Document.
 */
public class ServedPatient {

    private final Patient patient;
    private String noteContent;
    private String referralContent;
    private String mcContent;
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
     * Returns the note content for the {@code served patient}.
     */
    public String getNoteContent() {
        return this.noteContent;
    }
    /**
     * Returns the MC content for the {@code served patient}.
     */
    public String getMcContent() {
        return this.mcContent;
    }

    /**
     * Returns the referral content for the {@code served patient}.
     */
    public String getReferralContent() {
        return this.referralContent;
    }

    /**
     * Skeleton to add note content.
     */
    public String addNoteContent(String content) {
        noteContent += " filler" + content;
        return noteContent;
    }

    /**
     * Skeleton to add referral content.
     */
    public String addReferralContent(String notes, String todayDate, String doctorName) {
        referralContent += "Dear Specialist, please assist the patient named above in the following matter:\n";
        referralContent += notes + "\n";
        referralContent += "Kindly do accept him under your care. Thank you very much.\n";
        referralContent += "Date: " + todayDate + "\n";
        referralContent += "Issuing Doctor: " + doctorName + "\n";
        referralContent += "CLInic Pte Ltd\n";
        return referralContent;
    }

    /**
     * Skeleton to add Mc Content.
     */
    public String addMcContent(int dayNumber, String startDate, String endDate, String todayDate, String doctorName) {
        mcContent += "The above named is unfit for duty for a period of " + dayNumber
                + " days from " + startDate + " to " + endDate + ".\n";
        mcContent += "This certificate is not valid for absence from court attendance.\n";
        mcContent += "Date: " + todayDate + "\n";
        mcContent += "Issuing Doctor: " + doctorName + "\n";
        mcContent += "CLInic Pte Ltd\n";
        return mcContent;
    }

    /**
     * Console view of a served patient.
     * @return String representation of patient with Name and IcNumber.
     */
    public String toNameAndIc() {
        return patient.toNameAndIc();
    }

}