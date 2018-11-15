package seedu.address.logic.commands;

import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemoveCommand.MESSAGE_INDEX_OUT_OF_BOUND;
import static seedu.address.logic.commands.RemoveCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getSamplePersonsLinkedList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
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

public class RemoveCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void constructor_nullIndex_throwException() {
        thrown.expect(NullPointerException.class);
        new RemoveCommand(null);
    }

    @Test
    public void execute_removeValidPositionOne_success() {
        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        Patient patient = expectedPatientQueue.removeAtIndex(0);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc() + " from Queue.";

        assertCommandSuccess(new RemoveCommand(Index.fromOneBased(1)), commandHistory, patientQueue, currentPatient,
                servedPatientList, model, expectedPatientQueue, currentPatient, servedPatientList, expectedMessage);
    }

    @Test
    public void execute_removeValidPositionSize_success() {
        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        Patient patient = expectedPatientQueue.removeAtIndex(expectedPatientQueue.getQueueLength() - 1);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc() + " from Queue.";

        assertCommandSuccess(new RemoveCommand(Index.fromOneBased(patientQueue.getQueueLength())), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedPatientQueue, currentPatient,
                servedPatientList, expectedMessage);
    }

    @Test
    public void execute_removeInvalidPositionSizePlusOne_success() {
        String expectedMessage = MESSAGE_INDEX_OUT_OF_BOUND;

        assertCommandFailure(new RemoveCommand(Index.fromOneBased(patientQueue.getQueueLength() + 1)), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedMessage);
    }
}
