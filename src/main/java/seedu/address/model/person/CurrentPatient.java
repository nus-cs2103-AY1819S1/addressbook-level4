package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Uncompleted class, need to add more methods and fields.
 * Represents a patient that is currently consulting the doctor in the address book.
 * It has all methods needed to add information to generate various Document later on.
 */
public class CurrentPatient {

    private ServedPatient patient;

    /**
     * Assigns a patient to the Current Patient.
     * @param patient which is going to be served.
     */
    public void assignPatient(Patient patient) {
        this.patient = new ServedPatient(patient);
    }

    /**
     * Removes the ServedPatient.
     * @return the removed ServedPatient
     */
    public ServedPatient finishServing() {
        ServedPatient temp = patient;
        patient = null;
        return temp;
    }

    /**
     * Add note content to the ServedPatient.
     */
    public String addNoteContent(String content) {
        requireNonNull(patient);
        return patient.addNoteContent(content);
    }

    /**
     * Add referral letter content to the ServedPatient.
     */
    public String addReferralContent(String content) {
        requireNonNull(patient);
        return patient.addReferralContent(content);
    }

    /**
     * Add Mc content to the ServedPatient.
     */
    public String addMcContent(String content) {
        requireNonNull(patient);
        return patient.addMcContent(content);
    }

    /**
     * Returns the note content for the {@code served patient}.
     */
    public String getNoteContent() {
        requireNonNull(patient);
        return patient.getNoteContent();
    }
    /**
     * Returns the MC content for the {@code served patient}.
     */
    public String getMcContent() {
        requireNonNull(patient);
        return patient.getMcContent();
    }

    /**
     * Returns the referral content for the {@code served patient}.
     */
    public String getReferralContent() {
        requireNonNull(patient);
        return patient.getReferralContent();
    }

    /**
     * Returns true if there is Current Patient.
     */
    public boolean hasCurrentPatient() {
        return patient != null;
    }

    /**
     * Returns a console-friendly representation of the patient.
     */
    public String toNameAndIc() {
        return patient.toNameAndIc();
    }

}
