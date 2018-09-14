package seedu.address.model.person.medicalrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Medical Record of a Patient.
 */
public class MedicalRecord {

    // Items belonging to a single medical record
    private final BloodType bloodType;
    private List<DrugAllergy> drugAllergies;
    private List<Disease> diseaseHistory;
    private List<Note> notes;


    /**
     * Initialise MedicalRecord with only BloodType, since BloodType is the only attribute that must be non-null.
     * @param bloodType
     */
    public MedicalRecord(BloodType bloodType) {
        this.bloodType = bloodType;
        this.drugAllergies = new ArrayList<>();
        this.diseaseHistory = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

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

    public void addDrugAllergy(DrugAllergy drugAllergy) {
        this.drugAllergies.add(drugAllergy);
    }

    public void addDisease(Disease disease) {
        this.diseaseHistory.add(disease);
    }

    public void addNote(Note note) {
        this.notes.add(note);
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
