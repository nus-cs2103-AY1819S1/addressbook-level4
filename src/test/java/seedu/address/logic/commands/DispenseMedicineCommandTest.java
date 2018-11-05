package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.logic.commands.DispenseMedicineCommand.MESSAGE_CURRENT_PATIENT_NOT_FOUND;
import static seedu.address.logic.commands.DispenseMedicineCommand.MESSAGE_MEDICINE_STOCK_INSUFFICIENT;
import static seedu.address.logic.commands.DispenseMedicineCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
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
import seedu.address.testutil.MedicineBuilder;

public class DispenseMedicineCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndexPosition_throwException() {
        thrown.expect(NullPointerException.class);
        new DispenseMedicineCommand(null, null);
    }

    @Test
    public void execute_existingCurrentPatientNoMedicineAllocatedStockSufficientUnfilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
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
    public void execute_existingCurrentPatientMedicineAllocatedStockSufficientUnfilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());

        //Let current patient have medicine first.
        currentPatient.addMedicine(medicine, new QuantityToDispense(5));
        model.dispenseMedicine(medicine, quantityToDispense);
        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    /**
     * If stock has X value and medicine quantity to allocate plus patient existing allocated medicine quantity
     * exceeds X but medicine quantity to allocate is less than X, then the command can be successfully executed.
     */
    @Test
    public void execute_quantityToAllocatePlusAllocatedQuantityExceedsStock_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        QuantityToDispense quantityToDispense = new QuantityToDispense(medicine.getStockValue());
        String expectedMessage = String.format(MESSAGE_SUCCESS, medicine.getStockValue(), medicine.getMedicineName());
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);

        medicine.refill(quantityToDispense.getValue());

        //Allocate X - 1 medicine to patient and also removing from model
        currentPatient.addMedicine(medicine, new QuantityToDispense(medicine.getStockValue() - 1));
        model.dispenseMedicine(medicine, new QuantityToDispense(medicine.getStockValue() - 1));

        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    @Test
    public void execute_existingCurrentPatientNoMedicineAllocatedStockSufficientFilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());


        showMedicineAtIndex(model, Index.fromOneBased(1));
        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    @Test
    public void execute_existingCurrentPatientMedicineAllocatedStockSufficientFilteredList_success() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //stock is sufficient
        assertTrue(10 <= medicine.getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(10);
        expectedModel.dispenseMedicine(medicine, quantityToDispense);
        expectedCurrentPatient.addMedicine(medicine, quantityToDispense);
        String expectedMessage = String.format(MESSAGE_SUCCESS, 10, medicine.getMedicineName());


        //Let current patient have medicine first.
        currentPatient.addMedicine(medicine, new QuantityToDispense(5));
        model.dispenseMedicine(medicine, new QuantityToDispense(5));
        showMedicineAtIndex(model, Index.fromOneBased(1));
        assertCommandSuccess(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient,
                servedPatientList, expectedModel, expectedMessage);
    }

    @Test
    public void execute_existingCurrentPatientNoMedicineAllocatedStockInsufficient_failure() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        int stock = medicine.getStockValue();

        QuantityToDispense quantityToDispense = new QuantityToDispense(stock + 1);

        String expectedMessage = String.format(MESSAGE_MEDICINE_STOCK_INSUFFICIENT, medicine.getMedicineName());

        assertCommandFailure(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedMessage);
    }

    @Test
    public void execute_existingCurrentPatientMedicineAllocatedStockInsufficient_failure() {
        Medicine medicine = model.getFilteredMedicineList().get(0);
        int stock = medicine.getStockValue();

        QuantityToDispense quantityToDispense = new QuantityToDispense(stock + 1);

        String expectedMessage = String.format(MESSAGE_MEDICINE_STOCK_INSUFFICIENT, medicine.getMedicineName());

        model.dispenseMedicine(medicine, new QuantityToDispense(stock));
        currentPatient.addMedicine(medicine, new QuantityToDispense(stock));

        assertCommandFailure(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedMessage);
    }

    @Test
    public void execute_emptyCurrentPatient_failure() {
        currentPatient = new CurrentPatient();

        String expectedMessage = MESSAGE_CURRENT_PATIENT_NOT_FOUND;
        assertTrue(1 <= model.getFilteredMedicineList().get(0).getStockValue());
        QuantityToDispense quantityToDispense = new QuantityToDispense(1);
        assertCommandFailure(new DispenseMedicineCommand(INDEX_FIRST_PERSON, quantityToDispense), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedMessage);
    }

    /**
     * Generate an addressbook with non-static medicine objects.
     */
    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Medicine medicine : getTypicalMedicines()) {
            ab.addMedicine(medicine);
        }
        return ab;
    }

    /**
     * Generate a list of non-static Medicine objects.
     */
    public List<Medicine> getTypicalMedicines() {
        List<Medicine> medicines = new LinkedList<>();
        Medicine medicine = new MedicineBuilder().withMedicineName("Acetaminophen")
                .withMinimumStockQuantity(200)
                .withPricePerUnit("1")
                .withSerialNumber("1234522")
                .withStock(4000).build();
        medicines.add(medicine);
        medicine = new MedicineBuilder().withMedicineName("Bacitracin")
                .withMinimumStockQuantity(132)
                .withPricePerUnit("6")
                .withSerialNumber("3444211")
                .withStock(983).build();
        medicines.add(medicine);
        medicine = new MedicineBuilder().withMedicineName("CALEX")
                .withMinimumStockQuantity(30)
                .withStock(93)
                .withSerialNumber("9374830")
                .withPricePerUnit("100").build();
        medicines.add(medicine);
        return medicines;

    }
}
