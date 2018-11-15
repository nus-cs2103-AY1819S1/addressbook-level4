package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalMedicalRecords.BENSON_MEDICAL_RECORD;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.medicalrecord.BloodType;
import seedu.address.testutil.Assert;

public class XmlAdaptedMedicalRecordTest {
    private static final String INVALID_BLOOD_TYPE = "F";
    private static final String INVALID_DISEASE = "@ids";
    private static final String INVALID_DRUG_ALLERGY = "p@n@d0l";

    private static final String VALID_BLOOD_TYPE = BENSON_MEDICAL_RECORD.getBloodType().toString();
    private static final List<XmlAdaptedDisease> VALID_DISEASE = BENSON_MEDICAL_RECORD.getDiseaseHistory()
            .stream()
            .map(XmlAdaptedDisease::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedDrugAllergy> VALID_DRUG_ALLERGY = BENSON_MEDICAL_RECORD.getDrugAllergies()
            .stream()
            .map(XmlAdaptedDrugAllergy::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedNote> VALID_NOTE = BENSON_MEDICAL_RECORD.getNotes()
            .stream()
            .map(XmlAdaptedNote::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMedicalRecordDetails_returnsMedicalRecord() throws Exception {
        XmlAdaptedMedicalRecord medicalRecord = new XmlAdaptedMedicalRecord(BENSON_MEDICAL_RECORD);
        assertEquals(BENSON_MEDICAL_RECORD, medicalRecord.toModelType());
    }

    @Test
    public void toModelType_invalidBloodType_throwsIllegalValueException() {
        XmlAdaptedMedicalRecord medicalRecord =
                new XmlAdaptedMedicalRecord(INVALID_BLOOD_TYPE, VALID_DRUG_ALLERGY, VALID_DISEASE, VALID_NOTE);
        String expectedMessage = BloodType.MESSAGE_BLOODTYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicalRecord::toModelType);
    }

    @Test
    public void toModelType_nullBloodType_throwsIllegalValueException() {
        XmlAdaptedMedicalRecord medicalRecord =
                new XmlAdaptedMedicalRecord(null, VALID_DRUG_ALLERGY, VALID_DISEASE, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BloodType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicalRecord::toModelType);
    }

    @Test
    public void toModelType_invalidDiseaseHistory_throwsIllegalValueException() {
        List<XmlAdaptedDisease> invalidDiseaseHistory = new ArrayList<>(VALID_DISEASE);
        invalidDiseaseHistory.add(new XmlAdaptedDisease(INVALID_DISEASE));
        XmlAdaptedMedicalRecord medicalRecord =
                new XmlAdaptedMedicalRecord(VALID_BLOOD_TYPE, VALID_DRUG_ALLERGY, invalidDiseaseHistory, VALID_NOTE);
        Assert.assertThrows(IllegalValueException.class, medicalRecord::toModelType);
    }

    @Test
    public void toModelType_invalidDrugAllergies_throwsIllegalValueException() {
        List<XmlAdaptedDrugAllergy> invalidDrugAllergies = new ArrayList<>(VALID_DRUG_ALLERGY);
        invalidDrugAllergies.add(new XmlAdaptedDrugAllergy(INVALID_DRUG_ALLERGY));
        XmlAdaptedMedicalRecord medicalRecord =
                new XmlAdaptedMedicalRecord(VALID_BLOOD_TYPE, invalidDrugAllergies, VALID_DISEASE, VALID_NOTE);
        Assert.assertThrows(IllegalValueException.class, medicalRecord::toModelType);
    }

}
