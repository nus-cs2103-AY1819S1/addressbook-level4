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

    public MedicalRecord() {
        this.bloodType = new BloodType("");
        this.drugAllergies = new ArrayList<>();
        this.diseaseHistory = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

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

    public MedicalRecord(MedicalRecord medicalRecord) {
        this.bloodType = medicalRecord.getBloodType();
        this.drugAllergies = new ArrayList<>(medicalRecord.getDrugAllergies());
        this.diseaseHistory = new ArrayList<>(medicalRecord.getDiseaseHistory());
        this.notes = new ArrayList<>(medicalRecord.getNotes());
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

    public static MedicalRecord combineMedicalRecords(MedicalRecord record1, MedicalRecord record2) {

        BloodType newBloodType = record1.getBloodType();

        // TODO: Some kind of proper exception throwing when there is attempt to change blood type.
        if (!record1.getBloodType().equals(record2.getBloodType())
                && !record1.getBloodType().value.equals("") && !record2.getBloodType().value.equals("")) {
            // This is not allowed!
            System.out.println("This is not allowed bruh, you can't change your blood type.");
            System.out.println("Defaulting to original bloodtype");
        } else {
            newBloodType = record2.getBloodType();
        }

        List<DrugAllergy> newDrugAllergies = new ArrayList<>(record1.getDrugAllergies());
        for (DrugAllergy drugAllergy: record2.getDrugAllergies()) {
            if (!newDrugAllergies.contains(drugAllergy)) {
                newDrugAllergies.add(drugAllergy);
            }
        }

        List<Disease> newDiseaseHistory = new ArrayList<>(record1.getDiseaseHistory());
        for (Disease disease: record2.getDiseaseHistory()) {
            if (!newDiseaseHistory.contains(disease)) {
                newDiseaseHistory.add(disease);
            }
        }

        List<Note> newNotes = new ArrayList<>(record1.getNotes());
        for (Note note: record2.getNotes()) {
            if (!newNotes.contains(note)) {
                newNotes.add(note);
            }
        }

        MedicalRecord newRecord = new MedicalRecord(newBloodType, newDrugAllergies, newDiseaseHistory, newNotes);
        
        return newRecord;
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
