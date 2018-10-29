package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ServeCommand.MESSAGE_EMPTY_QUEUE;
import static seedu.address.logic.commands.ServeCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getSamplePersonsLinkedList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

public class ServeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private PatientQueue patientQueue;
    private CurrentPatient currentPatient;
    private ServedPatientList servedPatientList;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        patientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        currentPatient = new CurrentPatient();
        servedPatientList = new ServedPatientListManager();
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_servePopulatedPatientQueue_success() {
        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        Patient patient = expectedPatientQueue.dequeue();
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(patient));

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc();

        assertCommandSuccess(new ServeCommand(), commandHistory, patientQueue, currentPatient, servedPatientList,
                model, expectedPatientQueue, expectedCurrentPatient, servedPatientList, expectedMessage);
    }

    @Test
    public void execute_serveEmptyPatientQueue_failure() {
        patientQueue = new PatientQueueManager();

        assertTrue(patientQueue.isEmpty());

        assertCommandFailure(new ServeCommand(), commandHistory, patientQueue, currentPatient,
                servedPatientList, model, MESSAGE_EMPTY_QUEUE);
    }
}
