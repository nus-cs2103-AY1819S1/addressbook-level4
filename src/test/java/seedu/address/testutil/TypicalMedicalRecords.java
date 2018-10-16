package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.medicalrecord.MedicalRecord;

/**
 * A utility class containing a list of {@code MedicalRecord} objects to be used in tests.
 */
public class TypicalMedicalRecords {

    public static final MedicalRecord ALICE_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("A+")
            .withDiseaseHistory("Asthma").withDrugAllergies("Panadol").withNotes("Very weak person").build();
    public static final MedicalRecord BENSON_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("A-")
            .withDiseaseHistory("HFMD").withDrugAllergies("Antibiotics").withNotes("Very weak person").build();
    public static final MedicalRecord CARL_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("B+")
            .withDiseaseHistory("Asthma", "HFMD").withNotes("Very weak person").build();
    public static final MedicalRecord DANIEL_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("B-")
            .withDrugAllergies("Panadol").withNotes("Very strong person").build();
    public static final MedicalRecord ELLE_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("O+")
            .withDiseaseHistory("Lung Cancer").build();
    public static final MedicalRecord FIONA_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("O-")
            .withDiseaseHistory("Diabetes", "Colon Cancer").build();
    public static final MedicalRecord GEORGE_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("AB+")
            .withDiseaseHistory("Dengue").build();

    // Manually added
    public static final MedicalRecord HOON_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("AB-")
            .withDiseaseHistory("Dengue").withDrugAllergies("Pandaol").build();
    public static final MedicalRecord IDA_MEDICAL_RECORD = new MedicalRecordBuilder().withBloodType("A+")
            .build();

    // Manually added - MedicalRecord details found in {@code CommandTestUtil}
    public static final MedicalRecord AMY_MEDICAL_RECORD = new MedicalRecordBuilder()
            .withBloodType(VALID_BLOOD_TYPE_AMY).build();
    public static final MedicalRecord BOB_MEDICAL_RECORD = new MedicalRecordBuilder()
            .withBloodType(VALID_BLOOD_TYPE_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMedicalRecords() {} // prevents instantiation

    public static List<MedicalRecord> getTypicalMedicalRecords() {
        return new ArrayList<>(Arrays.asList(ALICE_MEDICAL_RECORD, BENSON_MEDICAL_RECORD, CARL_MEDICAL_RECORD,
                DANIEL_MEDICAL_RECORD, ELLE_MEDICAL_RECORD, FIONA_MEDICAL_RECORD, GEORGE_MEDICAL_RECORD));

    }
}
