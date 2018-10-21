package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

import seedu.address.testutil.TypicalPersons;


public class DisplayQueueCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsSame() throws Exception {
        patientQueue.enqueue(TypicalPersons.ALICE);
        patientQueue.enqueue(TypicalPersons.BOB);
        String expectedMessage = patientQueue.displayQueue();
        CommandResult commandResult = new DisplayQueueCommand().execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_listIsDifferent() throws Exception {
        patientQueue.clear();
        patientQueue.enqueue(TypicalPersons.ALICE);
        patientQueue.enqueue(TypicalPersons.BOB);
        String wrongMessage = patientQueue.displayQueue();
        patientQueue.clear();
        patientQueue.enqueue(TypicalPersons.BOB);
        patientQueue.enqueue(TypicalPersons.ALICE);
        CommandResult commandResult = new DisplayQueueCommand().execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        assertNotEquals(wrongMessage, commandResult.feedbackToUser);

    }
}
