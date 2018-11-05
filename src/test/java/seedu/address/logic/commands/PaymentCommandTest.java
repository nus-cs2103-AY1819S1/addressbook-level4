package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.PaymentCommand.MESSAGE_EMPTY_SERVED_PATIENT_LIST;
import static seedu.address.logic.commands.PaymentCommand.MESSAGE_INVALID_POSITION;
import static seedu.address.logic.commands.PaymentCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.QueueCommandTestUtil.getSampleServedPatientsList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
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
import seedu.address.model.person.ServedPatient;

public class PaymentCommandTest {
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
        servedPatientList = getSampleServedPatientsList();
        commandHistory = new CommandHistory();
    }

    @Test
    public void constructor_nullIndex_throwException() {
        thrown.expect(NullPointerException.class);
        new PaymentCommand(null);
    }

    @Test
    public void execute_validPositionOne_success() {
        ServedPatientList expectedServedPatientList = getSampleServedPatientsList();
        ServedPatient patient = expectedServedPatientList.removeAtIndex(0);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc();

        assertCommandSuccess(new PaymentCommand(INDEX_FIRST_PERSON), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, patientQueue, currentPatient,
                expectedServedPatientList, expectedMessage);
    }

    @Test
    public void execute_validPositionSize_success() {
        ServedPatientList expectedServedPatientList = getSampleServedPatientsList();
        ServedPatient patient = expectedServedPatientList.removeAtIndex(
                expectedServedPatientList.getServedPatientListLength() - 1);

        String expectedMessage = MESSAGE_SUCCESS + patient.toNameAndIc();

        Index index = Index.fromOneBased(servedPatientList.getServedPatientListLength());

        assertCommandSuccess(new PaymentCommand(index), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, patientQueue, currentPatient,
                expectedServedPatientList, expectedMessage);
    }

    @Test
    public void execute_invalidPositiveSizePlusOne_failure() {
        String expectedMessage = MESSAGE_INVALID_POSITION;

        Index invalidIndex = Index.fromZeroBased(servedPatientList.getServedPatientListLength());
        assertCommandFailure(new PaymentCommand(invalidIndex), commandHistory, patientQueue, currentPatient,
                servedPatientList, model, expectedMessage);
    }

    @Test
    public void execute_emptyServedPatientList_failure() {
        ServedPatientList emptyServedPatientList = new ServedPatientListManager();

        String expectedMessage = MESSAGE_EMPTY_SERVED_PATIENT_LIST;

        assertCommandFailure(new PaymentCommand(INDEX_FIRST_PERSON), commandHistory, patientQueue, currentPatient,
                emptyServedPatientList, model, expectedMessage);
    }

    @Test

    public void equals() {
        final PaymentCommand standardCommand = new PaymentCommand(INDEX_FIRST_PERSON);

        PaymentCommand commandWithSameValues = new PaymentCommand(INDEX_FIRST_PERSON);

        assertTrue(standardCommand.equals(commandWithSameValues));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));

        assertFalse(standardCommand.equals(new PaymentCommand(INDEX_SECOND_PERSON)));
    }

}
