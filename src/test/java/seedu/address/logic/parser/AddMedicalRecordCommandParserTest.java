package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.BLOOD_TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DISEASE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DRUG_ALLERGY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISEASE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMedicalRecordCommand;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.testutil.MedicalRecordBuilder;

public class AddMedicalRecordCommandParserTest {

    private AddMedicalRecordCommandParser parser = new AddMedicalRecordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index expectedIndex = INDEX_SECOND_PERSON;
        MedicalRecord expectedMedicalRecord = new MedicalRecordBuilder().withBloodType(VALID_BLOOD_TYPE_AMY)
                .withDiseaseHistory(VALID_DISEASE_AMY).withDrugAllergies(VALID_DRUG_ALLERGY_AMY)
                .withNotes(VALID_NOTE_AMY).build();

        assertParseSuccess(parser, expectedIndex.getOneBased() + BLOOD_TYPE_DESC_AMY
                        + DISEASE_DESC_AMY + DRUG_ALLERGY_DESC_AMY + NOTE_DESC_AMY,
                new AddMedicalRecordCommand(expectedIndex, expectedMedicalRecord));
    }

    @Test
    public void parse_someFieldSpecified_success() {
        Index expectedIndex = INDEX_SECOND_PERSON;
        MedicalRecord expectedMedicalRecord = new MedicalRecordBuilder().withBloodType(VALID_BLOOD_TYPE_AMY)
                .withDiseaseHistory(VALID_DISEASE_AMY).build();

        assertParseSuccess(parser, expectedIndex.getOneBased() + BLOOD_TYPE_DESC_AMY
                        + DISEASE_DESC_AMY,
                new AddMedicalRecordCommand(expectedIndex, expectedMedicalRecord));
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index expectedIndex = INDEX_SECOND_PERSON;

        MedicalRecord expectedMedicalRecord = new MedicalRecordBuilder().withBloodType(VALID_BLOOD_TYPE_AMY).build();
        assertParseSuccess(parser, expectedIndex.getOneBased() + BLOOD_TYPE_DESC_AMY,
                new AddMedicalRecordCommand(expectedIndex, expectedMedicalRecord));
    }
}
