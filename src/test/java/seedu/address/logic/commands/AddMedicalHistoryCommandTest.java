package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXIST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.tag.TagContainsDoctorPredicate;
import seedu.address.model.tag.TagContainsPatientPredicate;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests and unit tests for AddMedicalHistoryCommand.
 */
public class AddMedicalHistoryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addMedicalHistory_success() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);

        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Patient editedPatient = new PatientBuilder(firstPatient).build();
        editedPatient.getMedicalHistory().addAllergy(VALID_ALLERGY);
        editedPatient.getMedicalHistory().addAllergy(VALID_CONDITION);

        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, VALID_ALLERGY, VALID_CONDITION);

        String expectedMessage = String.format(AddMedicalHistoryCommand.MESSAGE_ADD_MEDICAL_HISTORY_SUCCESS,
                editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPatient, editedPatient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addMedicalHistoryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonName_failure() {
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(NON_EXIST_NAME), null, VALID_ALLERGY, VALID_CONDITION);

        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_MATCH_NAME);
    }

    @Test
    public void execute_duplicateAllergy_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String duplicateAllergy = new String(firstPatient.getMedicalHistory().getAllergies().get(0));
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, duplicateAllergy, VALID_CONDITION);

        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                duplicateAllergy
                        + AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);

    }

    @Test
    public void execute_duplicateCondition_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String duplicateCondition = new String(firstPatient.getMedicalHistory().getConditions().get(0));
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, VALID_ALLERGY, duplicateCondition);

        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                duplicateCondition
                        + AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);

    }

    @Test
    public void execute_invalidType_failure() {
        final TagContainsDoctorPredicate predicate = new TagContainsDoctorPredicate();
        model.updateFilteredPersonList(predicate);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(
                        model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                        null, VALID_ALLERGY, VALID_CONDITION);
        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY);

    }

    @Test
    public void execute_blankInput_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, "", "");
        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_INFO);
    }

    @Test
    public void equals() {
        final AddMedicalHistoryCommand standardCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, VALID_ALLERGY, VALID_CONDITION);

        // same values -> returns true
        AddMedicalHistoryCommand commandWithSameValues =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, VALID_ALLERGY, VALID_CONDITION);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(
                new AddMedicalHistoryCommand(new Name(VALID_NAME_BENSON), null, VALID_ALLERGY, VALID_CONDITION)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, "", "")));
    }

}
