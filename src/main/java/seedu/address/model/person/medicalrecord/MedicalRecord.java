package seedu.address.model.person.medicalrecord;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Medical Record of a Patient.
 */
public class MedicalRecord {

    // Items belonging to a single medical record
    private BloodType bloodType;
    private List<DrugAllergy> drugAllergies;
    private List<Disease> diseaseHistory;
    private List<Note> notes;

    public MedicalRecord(BloodType bloodType, List<DrugAllergy> drugAllergies, List<Disease> diseaseHistory, List<Note> notes) {
        this.bloodType = bloodType;
        this.drugAllergies = drugAllergies;
        this.diseaseHistory = diseaseHistory;
        this.notes = notes;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public List<DrugAllergy> getDrugAllergies() {
        return drugAllergies;
    }

    public List<Disease> getDiseaseHistory() {
        return diseaseHistory;
    }

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bloodType, drugAllergies, diseaseHistory, notes);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Medical Record: ")
                .append(" Blood Type: ")
                .append(getBloodType())
                .append(" Drug Allergies: ")
                .append(getDrugAllergies())
                .append(" History of diseases: ")
                .append(getDiseaseHistory())
                .append(" Notes: ")
                .append(getNotes());
        return builder.toString();
    }

}
