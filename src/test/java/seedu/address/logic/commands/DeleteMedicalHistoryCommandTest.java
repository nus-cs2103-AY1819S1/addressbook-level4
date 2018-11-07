package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXIST_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXIST_CONDITION;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXIST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_TO_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_TO_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.CARL_PATIENT;
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
 * Contains integration tests and unit tests for DeleteMedicalHistoryCommand.
 */
public class DeleteMedicalHistoryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteMedicalHistory_success() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);

        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Patient editedPatient = new PatientBuilder(firstPatient).build();
        editedPatient.getMedicalHistory().getAllergies().remove(VALID_ALLERGY_TO_DELETE);
        editedPatient.getMedicalHistory().getConditions().remove(VALID_CONDITION_TO_DELETE);

        DeleteMedicalHistoryCommand deleteMedicalHistoryCommand =
                new DeleteMedicalHistoryCommand(
                        new Name(VALID_NAME_ALICE), null, VALID_ALLERGY_TO_DELETE, VALID_CONDITION_TO_DELETE);

        String expectedMessage = String.format(DeleteMedicalHistoryCommand.MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS,
                editedPatient);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPatient, editedPatient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteMedicalHistoryCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidPersonName_failure() {
        DeleteMedicalHistoryCommand deleteMedicalHistoryCommand =
                new DeleteMedicalHistoryCommand(new Name(NON_EXIST_NAME), null, VALID_ALLERGY, VALID_CONDITION);

        assertCommandFailure(deleteMedicalHistoryCommand,
                model, commandHistory, DeleteMedicalHistoryCommand
                        .MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_MATCH_NAME);
    }

    @Test
    public void execute_duplicatePatient_failure() {
        Patient similarPatientDifferentNumber = new PatientBuilder().withName(CARL_PATIENT.getName().toString())
                .withPhone("12341234").build();
        model.addPatient(similarPatientDifferentNumber);

        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(CARL_PATIENT.getName().toString()),
                        null, VALID_ALLERGY, VALID_CONDITION);

        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, AddMedicalHistoryCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidAllergy_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);

        DeleteMedicalHistoryCommand deleteMedicalHistoryCommand =
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, NON_EXIST_ALLERGY, "");

        assertCommandFailure(deleteMedicalHistoryCommand, model, commandHistory,
                DeleteMedicalHistoryCommand.MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY
                        + NON_EXIST_ALLERGY);

    }

    @Test
    public void execute_invalidCondition_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);

        DeleteMedicalHistoryCommand deleteMedicalHistoryCommand =
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, "", NON_EXIST_CONDITION);

        assertCommandFailure(deleteMedicalHistoryCommand, model, commandHistory,
                DeleteMedicalHistoryCommand.MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION
                        + NON_EXIST_CONDITION);

    }

    @Test
    public void execute_invalidType_failure() {
        final TagContainsDoctorPredicate predicate = new TagContainsDoctorPredicate();
        model.updateFilteredPersonList(predicate);
        DeleteMedicalHistoryCommand deleteMedicalHistoryCommand =
                new DeleteMedicalHistoryCommand(
                        model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                        null, VALID_ALLERGY, VALID_CONDITION);
        assertCommandFailure(deleteMedicalHistoryCommand, model, commandHistory,
                DeleteMedicalHistoryCommand.MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE);

    }

    @Test
    public void execute_blankInput_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        DeleteMedicalHistoryCommand deleteMedicalHistoryCommand =
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, "", "");
        assertCommandFailure(deleteMedicalHistoryCommand, model, commandHistory,
                DeleteMedicalHistoryCommand.MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO);
    }

    @Test
    public void equals() {
        final DeleteMedicalHistoryCommand standardCommand =
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, VALID_ALLERGY, VALID_CONDITION);

        // same values -> returns true
        DeleteMedicalHistoryCommand commandWithSameValues =
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, VALID_ALLERGY, VALID_CONDITION);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_BENSON), null, VALID_ALLERGY, VALID_CONDITION)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new DeleteMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, "", "")));
    }
}
