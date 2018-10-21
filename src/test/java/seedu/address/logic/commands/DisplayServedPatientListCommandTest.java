package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static seedu.address.logic.commands.QueueCommandTestUtil.generateServedPatientList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CurrentPatient;
import seedu.address.testutil.TypicalPersons;

public class DisplayServedPatientListCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void execute_listIsSame_success() throws Exception {
        servedPatientList = generateServedPatientList(TypicalPersons.ALICE, TypicalPersons.BOB);
        String expectedMessage = servedPatientList.displayServedPatientList();
        CommandResult commandResult = new DisplayServedPatientsCommand().execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_listIsDifferentOrder_failure() throws Exception {
        servedPatientList = generateServedPatientList(TypicalPersons.ALICE, TypicalPersons.BOB);
        String wrongMessage = servedPatientList.displayServedPatientList();
        servedPatientList = generateServedPatientList(TypicalPersons.BOB, TypicalPersons.ALICE);
        CommandResult commandResult = new DisplayServedPatientsCommand().execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        assertNotEquals(wrongMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_listIsDifferent_failure() throws Exception {
        servedPatientList = generateServedPatientList(TypicalPersons.IDA, TypicalPersons.ALICE, TypicalPersons.DANIEL);
        String wrongMessage = servedPatientList.displayServedPatientList();
        servedPatientList = generateServedPatientList(TypicalPersons.ALICE, TypicalPersons.BOB);
        CommandResult commandResult = new DisplayServedPatientsCommand().execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        assertNotEquals(wrongMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_listIsEmpty_commandExceptionThrown() throws Exception {
        thrown.expect(CommandException.class);
        servedPatientList = generateServedPatientList();
        new DisplayQueueCommand().execute(model, patientQueue, currentPatient, servedPatientList, commandHistory);
    }
}
