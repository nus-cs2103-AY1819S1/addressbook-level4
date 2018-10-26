package seedu.address.logic.commands;

import javax.print.attribute.standard.Severity;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.logic.commands.DispenseMedicineCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.ServedPatient;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalMedicines.BACITRACIN;
import static seedu.address.testutil.TypicalMedicines.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.AMY;

public class DispenseMedicineCommandTest {
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
        currentPatient = new CurrentPatient(new ServedPatient(AMY));
        servedPatientList = new ServedPatientListManager();
        commandHistory = new CommandHistory();
    }

    @Test
    public void constructor_nullIndex_throwException() {
        thrown.expect(NullPointerException.class);
        new DispenseMedicineCommand(null, null);
    }

    @Test
    public void execute_existingPatientNoMedicineAllocatedStockSufficientUnfilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedModel.commitAddressBook();
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());

        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    /**
     * Tests if the command with replace medicine X's quantity in patient.
     */
    @Test
    public void execute_existingPatientMedicineAllocatedStockSufficientUnfilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedModel.commitAddressBook();
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());

        //Let current patient have medicine first.
        currentPatient.addMedicine(medicine, new QuantityToDispense(5));
        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    @Test
    public void execute_existingPatientNoMedicineAllocatedStockSufficientFilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedModel.commitAddressBook();
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());

        //For checking later on
        showMedicineAtIndex(expectedModel, Index.fromOneBased(1));

        showMedicineAtIndex(model, Index.fromOneBased(1));
        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    @Test
    public void execute_existingPatientMedicineAllocatedStockSufficientFilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedModel.commitAddressBook();
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());

        //For checking later on
        showMedicineAtIndex(expectedModel, Index.fromOneBased(1));

        //Let current patient have medicine first.
        currentPatient.addMedicine(medicine, new QuantityToDispense(5));
        showMedicineAtIndex(model, Index.fromOneBased(1));
        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }
}
