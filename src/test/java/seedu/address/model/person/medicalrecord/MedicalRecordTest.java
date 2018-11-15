package seedu.address.model.person.medicalrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISEASE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISEASE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.testutil.TypicalMedicalRecords.AMY_MEDICAL_RECORD;
import static seedu.address.testutil.TypicalMedicalRecords.BOB_MEDICAL_RECORD;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.MedicalRecordBuilder;

public class MedicalRecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameMedicalRecord() {
        // same object -> returns true
        assertTrue(AMY_MEDICAL_RECORD.isSameMedicalRecord(AMY_MEDICAL_RECORD));

        // null -> returns false
        assertFalse(AMY_MEDICAL_RECORD.isSameMedicalRecord(null));

        // different minimumStockQuantity and pricePerUnit and stock -> returns false
        MedicalRecord editedAmyMedicalRecord = new MedicalRecordBuilder(AMY_MEDICAL_RECORD)
                .withDiseaseHistory(VALID_DISEASE_AMY)
                .withDrugAllergies(VALID_DRUG_ALLERGY_AMY)
                .withNotes(VALID_NOTE_AMY)
                .build();
        assertFalse(AMY_MEDICAL_RECORD.isSameMedicalRecord(editedAmyMedicalRecord));

        // different disease -> returns false
        editedAmyMedicalRecord = new MedicalRecordBuilder(AMY_MEDICAL_RECORD)
                .withDiseaseHistory(VALID_DISEASE_BOB).build();
        assertFalse(AMY_MEDICAL_RECORD.isSameMedicalRecord(editedAmyMedicalRecord));
    }

    @Test
    public void equals() {
        // same values -> returns true
        MedicalRecord amyMedicalRecordCopy = new MedicalRecordBuilder(AMY_MEDICAL_RECORD).build();
        assertTrue(AMY_MEDICAL_RECORD.equals(amyMedicalRecordCopy));

        // same object -> returns true
        assertTrue(AMY_MEDICAL_RECORD.equals(AMY_MEDICAL_RECORD));

        // null -> returns false
        assertFalse(AMY_MEDICAL_RECORD.equals(null));

        // different type -> returns false
        assertFalse(AMY_MEDICAL_RECORD.equals(5));

        // different medical record -> returns false
        assertFalse(AMY_MEDICAL_RECORD.equals(BOB_MEDICAL_RECORD));

        // different drugAllergy -> returns false
        MedicalRecord editedAmyMedicalRecord = new MedicalRecordBuilder(AMY_MEDICAL_RECORD)
                .withDrugAllergies(VALID_DRUG_ALLERGY_BOB).build();
        assertFalse(AMY_MEDICAL_RECORD.equals(editedAmyMedicalRecord));

        // different disease -> returns false
        editedAmyMedicalRecord = new MedicalRecordBuilder(AMY_MEDICAL_RECORD)
                .withDiseaseHistory(VALID_DISEASE_BOB).build();
        assertFalse(AMY_MEDICAL_RECORD.equals(editedAmyMedicalRecord));

        // different notes -> returns false
        editedAmyMedicalRecord = new MedicalRecordBuilder(AMY_MEDICAL_RECORD)
                .withNotes(VALID_NOTE_BOB).build();
        assertFalse(AMY_MEDICAL_RECORD.equals(editedAmyMedicalRecord));
    }
}
