package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.medicalrecord.BloodType;
import seedu.address.model.person.medicalrecord.Disease;
import seedu.address.model.person.medicalrecord.DrugAllergy;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.model.person.medicalrecord.Note;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building MedicalRecord objects.
 */
public class MedicalRecordBuilder {
    public static final String DEFAULT_BLOOD_TYPE = "A+";

    private BloodType bloodType;
    private List<DrugAllergy> drugAllergies;
    private List<Disease> diseaseHistory;
    private List<Note> notes;

    public MedicalRecordBuilder() {
        bloodType = new BloodType(DEFAULT_BLOOD_TYPE);
        drugAllergies = new ArrayList<>();
        diseaseHistory = new ArrayList<>();
        notes = new ArrayList<>();
    }

    /**
     * Initializes the MedicalRecordBuilder with the data of {@code medicalRecordToCopy}.
     */
    public MedicalRecordBuilder(MedicalRecord medicalRecordToCopy) {
        bloodType = medicalRecordToCopy.getBloodType();
        drugAllergies = medicalRecordToCopy.getDrugAllergies();
        diseaseHistory = medicalRecordToCopy.getDiseaseHistory();
        notes = medicalRecordToCopy.getNotes();
    }

    /**
     * Sets the {@code BloodType} of the {@code MedicalRecord} that we are building.
     */
    public MedicalRecordBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code DrugAllergy} of the {@code MedicalRecord} that we are building.
     */
    public MedicalRecordBuilder withDrugAllergies(String ... drugAllergies) {
        this.drugAllergies = SampleDataUtil.getDrugAllergyList(drugAllergies);
        return this;
    }

    /**
     * Sets the {@code Disease} of the {@code MedicalRecord} that we are building.
     */
    public MedicalRecordBuilder withDiseaseHistory(String ... diseaseHistory) {
        this.diseaseHistory = SampleDataUtil.getDiseaseList(diseaseHistory);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code MedicalRecord} that we are building.
     */
    public MedicalRecordBuilder withNotes(String ... notes) {
        this.notes = SampleDataUtil.getNoteList(notes);
        return this;
    }

    public MedicalRecord build() {
        return new MedicalRecord(bloodType, drugAllergies, diseaseHistory, notes);
    }
}
