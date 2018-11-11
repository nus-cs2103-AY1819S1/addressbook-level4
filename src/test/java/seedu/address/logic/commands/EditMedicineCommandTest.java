package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STOCK_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditMedicineCommand.MedicineDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.MedicineBuilder;
import seedu.address.testutil.MedicineDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditMedicineCommand.
 */
public class EditMedicineCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Medicine editedMedicine = new MedicineBuilder().build();
        MedicineDescriptor descriptor = new MedicineDescriptorBuilder(editedMedicine).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE, descriptor);

        String expectedMessage = String.format(EditMedicineCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMedicine(model.getFilteredMedicineList().get(0), editedMedicine);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMedicine = Index.fromOneBased(model.getFilteredMedicineList().size());
        Medicine lastPatient = model.getFilteredMedicineList().get(indexLastMedicine.getZeroBased());

        MedicineBuilder medicineInList = new MedicineBuilder(lastPatient);
        Medicine editedMedicine = medicineInList.withMedicineName(VALID_MEDICINE_NAME_ZYRTEC)
                .withStock(VALID_STOCK_ZYRTEC).build();

        MedicineDescriptor descriptor = new MedicineDescriptorBuilder().withMedicineName(VALID_MEDICINE_NAME_ZYRTEC)
                .withStock(VALID_STOCK_ZYRTEC).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(indexLastMedicine, descriptor);

        String expectedMessage = String.format(EditMedicineCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMedicine(lastPatient, editedMedicine);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE, new MedicineDescriptor());
        Medicine editedMedicine = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());

        String expectedMessage = String.format(EditMedicineCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        Medicine medicineInFilteredList = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        Medicine editedMedicine = new MedicineBuilder(medicineInFilteredList)
                .withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE,
                new MedicineDescriptorBuilder().withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build());

        String expectedMessage = String.format(EditMedicineCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMedicine(model.getFilteredMedicineList().get(0), editedMedicine);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMedicineUnfilteredList_failure() {
        Medicine firstPatient = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        MedicineDescriptor descriptor = new MedicineDescriptorBuilder(firstPatient).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_SECOND_MEDICINE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditMedicineCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

    @Test
    public void execute_duplicateMedicineFilteredList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        // edit medicine in filtered list into a duplicate in address book
        Medicine patientInList = model.getAddressBook().getMedicineList().get(INDEX_SECOND_MEDICINE.getZeroBased());
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE,
                new MedicineDescriptorBuilder(patientInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditMedicineCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

    @Test
    public void execute_invalidMedicineIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        MedicineDescriptor descriptor = new MedicineDescriptorBuilder()
                .withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        Index outOfBoundIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMedicineList().size());

        EditMedicineCommand editCommand = new EditMedicineCommand(outOfBoundIndex,
                new MedicineDescriptorBuilder().withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Medicine editedMedicine = new MedicineBuilder().build();
        Medicine medicineToEdit = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        MedicineDescriptor descriptor = new MedicineDescriptorBuilder(editedMedicine).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMedicine(medicineToEdit, editedMedicine);
        expectedModel.commitAddressBook();

        // edit -> first medicine edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered medicine list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first medicine edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        MedicineDescriptor descriptor = new MedicineDescriptorBuilder()
                .withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Medicine } from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited medicine in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the medicine object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMedicineEdited() throws Exception {
        Medicine editedMedicine = new MedicineBuilder().build();
        MedicineDescriptor descriptor = new MedicineDescriptorBuilder(editedMedicine).build();
        EditMedicineCommand editCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showMedicineAtIndex(model, INDEX_SECOND_MEDICINE);
        Medicine medicineToEdit = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        expectedModel.updateMedicine(medicineToEdit, editedMedicine);
        expectedModel.commitAddressBook();

        // edit -> edits second medicine in unfiltered medicine list / first medicine in filtered medicine list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered medicine list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased()), medicineToEdit);
        // redo -> edits same second medicine in unfiltered medicine list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditMedicineCommand standardCommand = new EditMedicineCommand(INDEX_FIRST_MEDICINE, DESC_PANADOL);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMedicineCommand(INDEX_SECOND_MEDICINE, DESC_PANADOL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMedicineCommand(INDEX_FIRST_MEDICINE, DESC_ZYRTEC)));
    }

}
