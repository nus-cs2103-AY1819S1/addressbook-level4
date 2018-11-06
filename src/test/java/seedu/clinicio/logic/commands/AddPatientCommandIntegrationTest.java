package seedu.clinicio.logic.commands;

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;

import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.patient.Patient;

import seedu.clinicio.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddPatientCommand}.
 */
public class AddPatientCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.addPatient(validPatient);
        expectedModel.commitClinicIo();

        assertCommandSuccess(new AddPatientCommand(validPatient), model, commandHistory,
                String.format(AddPatientCommand.MESSAGE_SUCCESS, validPatient), expectedModel, analytics);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient patientInList = model.getClinicIo().getPatientList().get(0);
        assertCommandFailure(new AddPatientCommand(patientInList), model, commandHistory,
                analytics, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

}
