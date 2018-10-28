package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;

/**
 * Represents a patient that is currently consulting the doctor in the address book.
 * It has all methods needed to add information to generate various document later on.
 */
public class CurrentPatient {

    private ServedPatient patient;

    public CurrentPatient() {}

    /**
     * Creates a new CurrentPatient with {@code patientToCopy}
     */
    public CurrentPatient(ServedPatient patientToCopy) {
        this.patient = patientToCopy;
    }

    /**
     * Gets Served Patient from Current Patient
     */
    public ServedPatient getServedPatient() {
        return patient;
    }

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
     * Add specified quantity of medicine to patient.
     * @param medicine to be added.
     * @param quantity of medicinet to be added.
     * @return string representation of medicine added.
     */
    public String addMedicine(Medicine medicine, QuantityToDispense quantity) {
        return patient.addMedicine(medicine, quantity);
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
     * Returns the allocated medicine for the patient.
     */
    public Map<Medicine, QuantityToDispense> getMedicineAllocated() {
        return patient.getMedicineAllocated();
    }

    /**
     * Returns true if there is Current Patient.
     */
    public boolean hasCurrentPatient() {
        return patient != null;
    }

    /**
     * Checks whether current is a specified patient.
     * @return true if queue contains patient.
     */
    public boolean isPatient(Patient patient) {
        if (this.patient == null) {
            return false;
        }
        return (this.patient).isPatient(patient);
    }

    /**
     * Returns a console-friendly representation of the patient's documents.
     */
    public String toDocumentInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNotes: ")
                .append(getNoteContent())
                .append("\nDay(s) of MC: ")
                .append(getMcContent())
                .append("\nReferral Content: ")
                .append(getReferralContent());
        sb.append("\nMedicine Allocated: ");
        getMedicineAllocated().forEach((medicine, quantity) -> sb.append(
                medicine.getMedicineName() + ": " + quantity + "\n"));
        return sb.toString();
    }

    /**
     * @return a console-friendly representation of the patient.
     */
    public String toNameAndIc() {
        return patient != null ? patient.toNameAndIc() : "No current patient!";
    }

    public String toUrlFormat() {
        return patient != null ? patient.getName().fullName : "empty";
    }

    public Patient getPatient() {
        return this.patient.getPatient();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CurrentPatient)) {
            return false;
        }

        // state check
        CurrentPatient other = (CurrentPatient) obj;

        if (this.patient == null && other.patient == null) {
            return true;
        } else if (other.patient == null) {
            return false;
        } else if (this.patient == null) {
            return false;
        }

        return this.patient.equals(other.patient);
    }

}
