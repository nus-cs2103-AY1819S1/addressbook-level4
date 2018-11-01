package seedu.address.model.person.medicalrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.exceptions.DifferentBloodTypeException;

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

    public MedicalRecord(BloodType bloodType, List<DrugAllergy> drugAllergies, List<Disease> diseaseHistory,
                         List<Note> notes) {
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

    /**
     * Returns true if both medicalrecords have same data.
     */
    public boolean isSameMedicalRecord(MedicalRecord otherMedicalRecord) {
        if (otherMedicalRecord == this) {
            return true;
        }
        return otherMedicalRecord != null
                && otherMedicalRecord.getBloodType().equals(getBloodType())
                && otherMedicalRecord.getDiseaseHistory().equals(getDiseaseHistory())
                && otherMedicalRecord.getDrugAllergies().equals(getDrugAllergies())
                && otherMedicalRecord.getNotes().equals(getNotes());
    }

    /**
     * Returns true if both medicalrecords have same data.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MedicalRecord)) {
            return false;
        }

        MedicalRecord otherMedicalRecord = (MedicalRecord) other;
        return otherMedicalRecord != null
                && otherMedicalRecord.getBloodType().equals(getBloodType())
                && otherMedicalRecord.getDiseaseHistory().equals(getDiseaseHistory())
                && otherMedicalRecord.getDrugAllergies().equals(getDrugAllergies())
                && otherMedicalRecord.getNotes().equals(getNotes());
    }


    /**
     * Combines 2 medical records into one.
     * @param record1
     * @param record2
     * @return a single MedicalRecord object which is a combination of the two provided.
     */
    public static MedicalRecord combineMedicalRecords(MedicalRecord record1, MedicalRecord record2) {

        BloodType newBloodType = record1.getBloodType();

        if (record1.getBloodType().value.equals("")) {
            newBloodType = record2.getBloodType();
        } else {
            if (!record2.getBloodType().value.equals("")) {
                if (!record1.getBloodType().value.equals(record2.getBloodType().value)) {
                    throw new DifferentBloodTypeException();
                }
            }
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
