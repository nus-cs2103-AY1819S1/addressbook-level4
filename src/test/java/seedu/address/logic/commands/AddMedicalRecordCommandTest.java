package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISEASE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISEASE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRUG_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalMedicalRecords.AMY_MEDICAL_RECORD;
import static seedu.address.testutil.TypicalMedicalRecords.BOB_MEDICAL_RECORD;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Patient;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.testutil.MedicalRecordBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddMedicalRecordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullMedicalRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMedicalRecordCommand(null, null);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient patient = new PersonBuilder(ALICE).build();
        MedicalRecord medicalRecord = new MedicalRecordBuilder().withBloodType(VALID_BLOOD_TYPE_BOB)
                .withDrugAllergies(VALID_DRUG_ALLERGY_BOB).withDiseaseHistory(VALID_DISEASE_BOB)
                .withNotes(VALID_NOTE_BOB).build();
        patient.addMedicalRecord(medicalRecord);
        AddMedicalRecordCommand addMedicalRecordCommand =
                new AddMedicalRecordCommand(INDEX_FIRST_PERSON, medicalRecord);

        String expectedMessage = String.format(AddMedicalRecordCommand.MESSAGE_SUCCESS, medicalRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), patient);
        expectedModel.commitAddressBook();


        System.out.println(model.getFilteredPersonList());
        System.out.println(expectedModel.getFilteredPersonList());

        assertCommandSuccess(addMedicalRecordCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    // Some fields specified success
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Patient patient = new PersonBuilder(ALICE).build();
        MedicalRecord medicalRecord = new MedicalRecordBuilder().withBloodType(VALID_BLOOD_TYPE_AMY)
                .withDiseaseHistory(VALID_DISEASE_AMY).build();
        patient.addMedicalRecord(medicalRecord);
        AddMedicalRecordCommand addMedicalRecordCommand =
                new AddMedicalRecordCommand(INDEX_FIRST_PERSON, medicalRecord);

        String expectedMessage = String.format(AddMedicalRecordCommand.MESSAGE_SUCCESS, medicalRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), patient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addMedicalRecordCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    // No field specified success
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Patient patient = new PersonBuilder(ALICE).build();
        MedicalRecord medicalRecord = new MedicalRecordBuilder().build();
        patient.addMedicalRecord(medicalRecord);
        AddMedicalRecordCommand addMedicalRecordCommand =
                new AddMedicalRecordCommand(INDEX_FIRST_PERSON, medicalRecord);

        String expectedMessage = String.format(AddMedicalRecordCommand.MESSAGE_SUCCESS, medicalRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), patient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addMedicalRecordCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    // invalid person index failure
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MedicalRecord medicalRecord = new MedicalRecordBuilder().build();
        AddMedicalRecordCommand addMedicalRecordCommand =
                new AddMedicalRecordCommand(outOfBoundIndex, medicalRecord);

        assertCommandFailure(addMedicalRecordCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // equals
    @Test
    public void equals() {
        final AddMedicalRecordCommand standardCommand =
                new AddMedicalRecordCommand(INDEX_FIRST_PERSON, AMY_MEDICAL_RECORD);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddMedicalRecordCommand(INDEX_SECOND_PERSON, AMY_MEDICAL_RECORD)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddMedicalRecordCommand(INDEX_FIRST_PERSON, BOB_MEDICAL_RECORD)));
    }


}
