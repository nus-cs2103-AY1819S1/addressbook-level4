package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.medicalrecord.Note;

/**
 * Represents a patient that has already consulted the doctor in the address book.
 * It has all the necessary data needed to generate any object that inherits from document.
 */
public class ServedPatient {

    private Patient patient;
    private String noteContent;
    private String referralContent;
    private String mcContent;
    private Map<Medicine, QuantityToDispense> medicineAllocated;
    // add more fields as required

    /**
     * Constructs a servedPatient object from a patient object to extract all the information
     * necessary for printing a document.
     *
     * @param patient A valid patient.
     */
    public ServedPatient(Patient patient) {
        requireNonNull(patient);
        medicineAllocated = new HashMap<>();
        this.patient = patient;
        this.noteContent = "";
        this.referralContent = "";
        this.mcContent = "";
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
     * Returns the medicine and quantity allocated for the {@code served patient}.
     */
    public Map<Medicine, QuantityToDispense> getMedicineAllocated() {
        return medicineAllocated;
    }

    /**
     * Skeleton to add note content.
     */
    public String addNoteContent(String content) {
        return noteContent = content;
    }

    /**
     * Skeleton to add referral content.
     */
    public String addReferralContent(String content) {
        return referralContent = content;
    }

    /**
     * Skeleton to add Mc Content.
     */
    public String addMcContent(String content) {
        return mcContent = content;
    }

    /**
     * Add specified quantity of medicine to patient.
     * @param medicine to be added.
     * @param quantity of medicine to be added.
     * @return string representation of medicine added.
     */
    public String addMedicine(Medicine medicine, QuantityToDispense quantity) {
        medicineAllocated.put(medicine, quantity);
        return medicine.toString();
    }

    /**
     * Creates a note from the noteContent and dispensedMedicines and saves to the patient's medicalRecord.
     */
    public Patient createNewPatientWithUpdatedMedicalRecord() {
        Note noteToSave = new Note(this.noteContent, this.medicineAllocated);
        Patient editedPatient = new Patient(this.patient, this.patient.getMedicalRecord());
        editedPatient.addNoteMedicalRecord(noteToSave);
        return editedPatient;
    }

    /**
     * Checks whether current is a specified patient.
     * @return true if queue contains patient.
     */
    public boolean isPatient(Patient patient) {
        return (this.patient).isSamePerson(patient);
    }

    /**
     * Update the patient if any information changes.
     */
    public void updatePatient(Patient patient) {
        this.patient = patient;
    }
    /**
     * @return a console-friendly representation of the patient.
     */
    public String toNameAndIc() {
        return patient.toNameAndIc();
    }

    public Patient getPatient() {
        return this.patient;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ServedPatient)) {
            return false;
        }

        // state check
        ServedPatient other = (ServedPatient) obj;

        return this.patient.equals(other.patient)
                && this.mcContent.equals(other.mcContent)
                && this.referralContent.equals(other.referralContent)
                && this.noteContent.equals(other.noteContent)
                && this.medicineAllocated.equals(other.medicineAllocated);
    }
}
