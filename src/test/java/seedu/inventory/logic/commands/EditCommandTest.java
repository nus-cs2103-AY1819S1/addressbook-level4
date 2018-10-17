package seedu.inventory.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.EditCommand.EditItemDescriptor;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.item.Item;
import seedu.inventory.testutil.EditItemDescriptorBuilder;
import seedu.inventory.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(), new StaffList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Item editedItem = new ItemBuilder().build();
        EditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList(),
                new StaffList());
        expectedModel.updateItem(model.getFilteredItemList().get(0), editedItem);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredItemList().size());
        Item lastItem = model.getFilteredItemList().get(indexLastPerson.getZeroBased());

        ItemBuilder itemInList = new ItemBuilder(lastItem);
        Item editedItem = itemInList.withName(VALID_NAME_SONY).withQuantity(VALID_QUANTITY_SONY)
                .withTags(VALID_TAG_SMARTPHONE).build();

        EditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_SONY)
                .withQuantity(VALID_QUANTITY_SONY).withTags(VALID_TAG_SMARTPHONE).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList(),
                new StaffList());
        expectedModel.updateItem(lastItem, editedItem);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, new EditItemDescriptor());
        Item editedItem = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList(),
                new StaffList());
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Item itemInFilteredList = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        Item editedItem = new ItemBuilder(itemInFilteredList).withName(VALID_NAME_SONY).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder().withName(VALID_NAME_SONY).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList(),
                new StaffList());
        expectedModel.updateItem(model.getFilteredItemList().get(0), editedItem);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder(firstItem).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        // edit item in filtered list into a duplicate in inventory
        Item itemInList = model.getInventory().getItemList().get(INDEX_SECOND_ITEM.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM,
                new EditItemDescriptorBuilder(itemInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_SONY).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of inventory
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getItemList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditItemDescriptorBuilder().withName(VALID_NAME_SONY).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item editedItem = new ItemBuilder().build();
        Item itemToEdit = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList(),
                new StaffList());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitInventory();

        // edit -> first item edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered item list to show all items
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item edited again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditCommand.EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_SONY).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> inventory state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Item} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited item in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the item object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemEdited() throws Exception {
        Item editedItem = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList(),
                new StaffList());

        showItemAtIndex(model, INDEX_SECOND_ITEM);
        Item itemToEdit = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitInventory();

        // edit -> edits second item in unfiltered item list / first item in filtered item list
        editCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered item list to show all items
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased()), itemToEdit);
        // redo -> edits same second item in unfiltered item list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ITEM, DESC_OPPO);

        // same values -> returns true
        EditItemDescriptor copyDescriptor = new EditCommand.EditItemDescriptor(DESC_OPPO);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ITEM, DESC_OPPO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ITEM, DESC_SONY)));
    }

}
