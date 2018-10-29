package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.InsertCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.InsertCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
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
    public void execute_validPositionOneUnfilteredList_success() {
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

    @Test
    public void execute_validPositionSevenUnfilteredList_success() {
        Patient patient = BENSON;

        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        expectedPatientQueue.addAtIndex(BENSON, 6);

        int indexBenson = model.getFilteredPersonList().indexOf(BENSON);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc()
                + " with Queue Number: 7" + "\n" + expectedPatientQueue.displayQueue();

        assertCommandSuccess(new InsertCommand(Index.fromZeroBased(indexBenson), Index.fromOneBased(7)), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedPatientQueue, currentPatient,
                servedPatientList, expectedMessage);
    }

    /**
     * If an index which is more than the current patient queue size is given, the patient will be added to the
     * back of the queue.
     */
    @Test
    public void execute_validPositionSizePlusOneUnfilteredListt_success() {
        Patient patient = BENSON;

        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        expectedPatientQueue.enqueue(BENSON);

        int indexBenson = model.getFilteredPersonList().indexOf(BENSON);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc()
                + " with Queue Number: 7" + "\n" + expectedPatientQueue.displayQueue();

        assertCommandSuccess(new InsertCommand(Index.fromZeroBased(indexBenson),
                        Index.fromOneBased(patientQueue.getQueueLength() + 1)), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedPatientQueue, currentPatient,
                servedPatientList, expectedMessage);
    }

    @Test
    public void execute_validPositionOneFilteredList_success() {
        Patient patient = BENSON;

        PatientQueue expectedPatientQueue = new PatientQueueManager(getSamplePersonsLinkedList());
        expectedPatientQueue.addAtIndex(BENSON, 0);

        int indexBenson = model.getFilteredPersonList().indexOf(BENSON);
        showPersonAtIndex(model, Index.fromZeroBased(indexBenson));

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc()
                + " with Queue Number: 1" + "\n" + expectedPatientQueue.displayQueue();

        assertCommandSuccess(new InsertCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1)), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, expectedPatientQueue, currentPatient, servedPatientList,
                expectedMessage);
    }

    @Test
    public void execute_invalidPatientIndex_failure() {
        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(new InsertCommand(Index.fromOneBased(model.getFilteredPersonList().size() + 1),
                Index.fromOneBased(1)), commandHistory, patientQueue, currentPatient, servedPatientList, model,
                expectedMessage);
    }

    @Test
    public void execute_insertDuplicatePatient_failure() {
        assertTrue(patientQueue.contains(ALICE));

        int indexAlice = model.getFilteredPersonList().indexOf(ALICE);
        String expectedMessage = MESSAGE_DUPLICATE_PERSON;

        assertCommandFailure(new InsertCommand(Index.fromZeroBased(indexAlice), Index.fromOneBased(1)), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedMessage);
    }

    @Test
    public void equals() {
        final InsertCommand standardCommand = new InsertCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        InsertCommand commandWithSameValues = new InsertCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        assertTrue(standardCommand.equals(commandWithSameValues));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));

        assertFalse(standardCommand.equals(new InsertCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON)));

        assertFalse(standardCommand.equals(new InsertCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON)));
    }
}
