package seedu.address.logic.commands;

import static seedu.address.logic.commands.InsertCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
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

public class InsertCommandTest {
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
        new InsertCommand(null, null);
    }

    @Test
    public void execute_validInsertCommandAtIndexOne_success() {
        Patient patient = BENSON;

        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        expectedPatientQueue.addAtIndex(BENSON, 0);

        int indexBenson = model.getFilteredPersonList().indexOf(BENSON);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc()
                + " with Queue Number: 1" + "\n" + expectedPatientQueue.displayQueue();

        assertCommandSuccess(new InsertCommand(Index.fromZeroBased(indexBenson), Index.fromOneBased(1)), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedPatientQueue, currentPatient,
                servedPatientList, expectedMessage);
    }
}
