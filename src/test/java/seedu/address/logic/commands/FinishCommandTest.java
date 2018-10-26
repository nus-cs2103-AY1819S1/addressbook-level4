package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.FinishCommand.MESSAGE_EMPTY_CURRENT_PATIENT;
import static seedu.address.logic.commands.FinishCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.ServedPatient;

public class FinishCommandTest {
    private Model model = new ModelManager();
    private PatientQueue patientQueue;
    private CurrentPatient currentPatient;
    private ServedPatientList servedPatientList;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        patientQueue = new PatientQueueManager();
        currentPatient = new CurrentPatient();
        servedPatientList = new ServedPatientListManager();
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_existingCurrentPatient_success() {
        currentPatient = new CurrentPatient(new ServedPatient(AMY));

        //current patient transferred to Served Patient List
        CurrentPatient expectedCurrentPatient = new CurrentPatient();
        ServedPatientList expectedServedPatientList = new ServedPatientListManager();
        expectedServedPatientList.addServedPatient(new ServedPatient(AMY));

        String expectedMessage = MESSAGE_SUCCESS + currentPatient.toNameAndIc();

        assertCommandSuccess(new FinishCommand(), commandHistory, patientQueue, currentPatient, servedPatientList,
                model, patientQueue, expectedCurrentPatient, expectedServedPatientList, expectedMessage);
    }

    @Test
    public void execute_emptyCurrentPatient_failure() {
        assertTrue(!currentPatient.hasCurrentPatient());

        String expectedMessage = MESSAGE_EMPTY_CURRENT_PATIENT;

        assertCommandFailure(new FinishCommand(), commandHistory, patientQueue, currentPatient, servedPatientList,
                model, expectedMessage);
    }

}
