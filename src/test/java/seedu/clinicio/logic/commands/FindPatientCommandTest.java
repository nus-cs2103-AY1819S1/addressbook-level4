package seedu.clinicio.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.commons.core.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.ALAN;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.patient.PatientNameContainsKeywordsPredicate;
import seedu.clinicio.testutil.TypicalPersons;

public class FindPatientCommandTest {

    private Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        UserSession.create(ALAN);

        addSamplePatient();
    }

    @Test
    public void equals() {
        PatientNameContainsKeywordsPredicate firstPredicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("first"));
        PatientNameContainsKeywordsPredicate secondPredicate =
                new PatientNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPatientCommand findFirstMedicineCommand = new FindPatientCommand(firstPredicate);
        FindPatientCommand findSecondMedicineCommand = new FindPatientCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstMedicineCommand.equals(findFirstMedicineCommand));

        // same values -> returns true
        FindPatientCommand findFirstMedicineCommandCopy = new FindPatientCommand(firstPredicate);
        assertTrue(findFirstMedicineCommand.equals(findFirstMedicineCommandCopy));

        // different types -> returns false
        assertFalse(findFirstMedicineCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstMedicineCommand == null);

        // different medicine -> returns false
        assertFalse(findFirstMedicineCommand.equals(findSecondMedicineCommand));
    }

    @Test
    public void execute_zeroKeywords_noPatientFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 0);
        PatientNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPatientCommand command = new FindPatientCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);
        assertEquals(Collections.emptyList(), model.getFilteredPatientList());
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 2);
        PatientNameContainsKeywordsPredicate predicate = preparePredicate("Alex Bryan");
        FindPatientCommand command = new FindPatientCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);
        assertEquals(Arrays.asList(ALEX, BRYAN), model.getFilteredPatientList());
    }

    /**
     * Parses {@code userInput} into a {@code PatientNameContainsKeywordsPredicate}.
     */
    private PatientNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PatientNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Add typical patient into model and expected model for testing.
     */
    private void addSamplePatient() {
        TypicalPersons.getTypicalPatients().forEach(patient -> {
            model.addPatient(patient);
            expectedModel.addPatient(patient);
        });
    }
}
