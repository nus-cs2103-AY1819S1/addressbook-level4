package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
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
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, VALID_ALLERGY, VALID_CONDITION);

        String expectedMessage = String.format(AddMedicalHistoryCommand.MESSAGE_ADD_MEDICAL_HISTORY_SUCCESS,
                editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPatient, editedPatient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addMedicalHistoryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(outOfBoundIndex, VALID_ALLERGY, VALID_CONDITION);

        assertCommandFailure(addMedicalHistoryCommand,
                model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateAllergy_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String duplicateAllergy = new String(firstPatient.getMedicalHistory().getAllergies().get(0));
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, duplicateAllergy, VALID_CONDITION);

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
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, VALID_ALLERGY, duplicateCondition);

        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                duplicateCondition
                        + AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);

    }

    @Test
    public void execute_invalidType_failure() {
        final TagContainsDoctorPredicate predicate = new TagContainsDoctorPredicate();
        model.updateFilteredPersonList(predicate);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, VALID_ALLERGY, VALID_CONDITION);
        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY);

    }

    @Test
    public void execute_blankInput_failure() {
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        AddMedicalHistoryCommand addMedicalHistoryCommand =
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, "", "");
        assertCommandFailure(addMedicalHistoryCommand, model, commandHistory,
                AddMedicalHistoryCommand.MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_INFO);
    }

    @Test
    public void equals() {
        final AddMedicalHistoryCommand standardCommand =
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, VALID_ALLERGY, VALID_CONDITION);

        // same values -> returns true
        AddMedicalHistoryCommand commandWithSameValues =
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, VALID_ALLERGY, VALID_CONDITION);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new AddMedicalHistoryCommand(INDEX_SECOND_PERSON, VALID_ALLERGY, VALID_CONDITION)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new AddMedicalHistoryCommand(INDEX_FIRST_PERSON, "", "")));
    }

}
