package seedu.address.storage;

import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.medicalrecord.BloodType;
import seedu.address.model.person.medicalrecord.Disease;
import seedu.address.model.person.medicalrecord.DrugAllergy;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.model.person.medicalrecord.Note;

public class XmlAdaptedMedicalRecord {

    @XmlElement(required = true)
    private String bloodType;
    @XmlElement
    private List<XmlAdaptedDrugAllergy> drugAllergy = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedDisease> disease = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedNote> note = new ArrayList<>();

    public XmlAdaptedMedicalRecord() {}

    public XmlAdaptedMedicalRecord(String bloodType, List<XmlAdaptedDrugAllergy> drugAllergy,
                                   List<XmlAdaptedDisease> disease, List<XmlAdaptedNote> note) {
        this.bloodType = bloodType;
        if (drugAllergy != null) {
            this.drugAllergy = new ArrayList<>(drugAllergy);
        }
        if (disease != null) {
            this.disease = new ArrayList<>(disease);
        }
        if (note != null) {
            this.note = new ArrayList<>(note);
        }
    }

    public XmlAdaptedMedicalRecord(MedicalRecord source) {
        drugAllergy = source.getDrugAllergies().stream()
                .map(XmlAdaptedDrugAllergy::new)
                .collect(Collectors.toList());
        disease = source.getDiseaseHistory().stream()
                .map(XmlAdaptedDisease::new)
                .collect(Collectors.toList());
        note = source.getNotes().stream()
                .map(XmlAdaptedNote::new)
                .collect(Collectors.toList());
        bloodType = source.getBloodType().value;
    }

    public MedicalRecord toModelType() throws IllegalValueException {
        if (bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, BloodType.class.getSimpleName()));
        }
        if (!BloodType.isValidBloodType(bloodType)) {
            throw new IllegalValueException(BloodType.MESSAGE_BLOODTYPE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodType);


        final List<DrugAllergy> modelDrugAllergies = new ArrayList<>();
        for (XmlAdaptedDrugAllergy drugAllergy: this.drugAllergy) {
            modelDrugAllergies.add(drugAllergy.toModelType());
        }

        final List<Disease> modelDiseaseHistory = new ArrayList<>();
        for (XmlAdaptedDisease disease: this.disease) {
            modelDiseaseHistory.add(disease.toModelType());
        }

        final List<Note> modelNotes = new ArrayList<>();
        for (XmlAdaptedNote note: this.note) {
            modelNotes.add(note.toModelType());
        }

        return new MedicalRecord(modelBloodType, modelDrugAllergies, modelDiseaseHistory, modelNotes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedicalRecord)) {
            return false;
        }

        XmlAdaptedMedicalRecord otherMedicalRecord = (XmlAdaptedMedicalRecord) other;
        return Objects.equals(bloodType, otherMedicalRecord.bloodType)
                && drugAllergy.equals(otherMedicalRecord.drugAllergy)
                && disease.equals(otherMedicalRecord.disease)
                && note.equals(otherMedicalRecord.note);
    }


}
