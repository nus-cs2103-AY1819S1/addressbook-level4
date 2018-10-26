package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RegisterCommand.MESSAGE_DUPLICATE_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
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

public class RegisterCommandTest {
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
    public void constructor_nullIndex_throwException() {
        thrown.expect(NullPointerException.class);
        new RegisterCommand(null);
    }

    @Test
    public void execute_validRegisterIndexUnFilteredList_success() {
        Patient patient = model.getFilteredPersonList().get(0);
        PatientQueue expectedPatientQueue = new PatientQueueManager();
        int position = expectedPatientQueue.enqueue(patient);

        String expectedMessage = RegisterCommand.MESSAGE_SUCCESS + patient.toNameAndIc()
                + " with Queue Number: " + position + "\n" + expectedPatientQueue.displayQueue();

        assertCommandSuccess(new RegisterCommand(Index.fromOneBased(1)), commandHistory, patientQueue, currentPatient,
                servedPatientList, model, expectedPatientQueue, currentPatient, servedPatientList, expectedMessage);

    }

    @Test
    public void execute_validRegisterIndexFilteredList_success() {
        Patient patient = model.getFilteredPersonList().get(0);
        PatientQueue expectedPatientQueue = new PatientQueueManager();
        int position = expectedPatientQueue.enqueue(patient);

        String expectedMessage = RegisterCommand.MESSAGE_SUCCESS + patient.toNameAndIc()
                + " with Queue Number: " + position + "\n" + expectedPatientQueue.displayQueue();

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertCommandSuccess(new RegisterCommand(Index.fromOneBased(1)), commandHistory, patientQueue, currentPatient,
                servedPatientList, model, expectedPatientQueue, currentPatient, servedPatientList, expectedMessage);
    }

    @Test
    public void execute_invalidRegisterIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertCommandFailure(new RegisterCommand(outOfBoundIndex), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRegisterIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertCommandFailure(new RegisterCommand(outOfBoundIndex), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_registerDuplicatePersonUnfilteredList_failure() {
        Patient patient = model.getFilteredPersonList().get(0);
        patientQueue.enqueue(patient);

        assertCommandFailure(new RegisterCommand(Index.fromOneBased(1)), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_registerDuplicatePersonFilteredList_failure() {
        Patient patient = model.getFilteredPersonList().get(0);
        patientQueue.enqueue(patient);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertCommandFailure(new RegisterCommand(Index.fromOneBased(1)), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, MESSAGE_DUPLICATE_PATIENT);
    }

}
