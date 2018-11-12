package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXIST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.CARL_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.HealthBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Condition;
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
    private ArrayList<Allergy> emptyAllergy = new ArrayList<>();
    private ArrayList<Condition> emptyCondition = new ArrayList<>();

    @Test
    public void execute_addMedicalHistory_success() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);

        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Patient editedPatient = new PatientBuilder(firstPatient).build();
        editedPatient.getMedicalHistory().addAllergy(new Allergy(VALID_ALLERGY_1));
        editedPatient.getMedicalHistory().addAllergy(new Allergy(VALID_ALLERGY_2));
        editedPatient.getMedicalHistory().addCondition(new Condition(VALID_CONDITION_1));
        editedPatient.getMedicalHistory().addCondition(new Condition(VALID_CONDITION_2));

        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));

        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, conditions);

        String expectedMessage = String.format(AddMedicalHistoryCommand.MESSAGE_ADD_MEDICAL_HISTORY_SUCCESS,
                editedPatient);

        Model expectedModel = new ModelManager(new HealthBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPatient, editedPatient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addMedicalHistoryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonName_failure() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));

        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(NON_EXIST_NAME), null, allergies, conditions);

        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_MATCH_NAME);
    }

    @Test
    public void execute_duplicatePatient_failure() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));

        Patient similarPatientDifferentNumber = new PatientBuilder().withName(CARL_PATIENT.getName().toString())
                .withPhone("12341234").build();
        model.addPatient(similarPatientDifferentNumber);

        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(CARL_PATIENT.getName().toString()),
                        null, allergies, conditions);

        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, AddMedicalHistoryCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicateAllergy_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Allergy duplicateAllergy = firstPatient.getMedicalHistory().getAllergies().get(0);
        ArrayList<Allergy> allergies = new ArrayList<>();
        allergies.add(duplicateAllergy);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, emptyCondition);

        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                duplicateAllergy
                        + AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);

    }

    @Test
    public void execute_duplicateCondition_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Condition duplicateCondition = firstPatient.getMedicalHistory().getConditions().get(0);
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(duplicateCondition);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, emptyAllergy, conditions);

        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                duplicateCondition
                        + AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);

    }

    @Test
    public void execute_duplicateAllergyInput() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_1));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, conditions);
        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE_INPUT);
    }

    @Test
    public void execute_duplicateConditionInput() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_1));
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, conditions);
        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE_INPUT);
    }

    @Test
    public void execute_invalidType_failure() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));
        final TagContainsDoctorPredicate predicate = new TagContainsDoctorPredicate();
        model.updateFilteredPersonList(predicate);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(
                        model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                        null, allergies, conditions);
        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY);

    }

    @Test
    public void execute_blankInput_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, emptyAllergy, emptyCondition);
        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_INFO);
    }


    @Test
    public void equals() {
        ArrayList<Allergy> allergies = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        allergies.add(new Allergy(VALID_ALLERGY_1));
        allergies.add(new Allergy(VALID_ALLERGY_2));
        conditions.add(new Condition(VALID_CONDITION_1));
        conditions.add(new Condition(VALID_CONDITION_2));
        final AddMedicalHistoryCommand standardCommand =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, conditions);

        // same values -> returns true
        AddMedicalHistoryCommand commandWithSameValues =
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, allergies, conditions);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(
                new AddMedicalHistoryCommand(new Name(VALID_NAME_BENSON), null, allergies, conditions)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new AddMedicalHistoryCommand(new Name(VALID_NAME_ALICE), null, emptyAllergy, emptyCondition)));
    }

}
