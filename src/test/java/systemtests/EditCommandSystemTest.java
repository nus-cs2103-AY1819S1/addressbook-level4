package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.IMAGE_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.IMAGE_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_IMAGE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_SKU_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.PRICE_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.PRICE_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_DIFFERENT;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.TAG_DESC_GADGET;
import static seedu.inventory.logic.commands.CommandTestUtil.TAG_DESC_SMARTPHONE;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_DIFFERENT;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.KEYWORD_MATCHING_SAMSUNG;
import static seedu.inventory.testutil.TypicalItems.OPPO;
import static seedu.inventory.testutil.TypicalItems.SONY;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.EditCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;
import seedu.inventory.testutil.ItemBuilder;
import seedu.inventory.testutil.ItemUtil;

public class EditCommandSystemTest extends InventorySystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_ITEM;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_SONY + "  "
                + PRICE_DESC_SONY + " " + QUANTITY_DESC_SONY + " " + SKU_DESC_SONY + "  " + IMAGE_DESC_SONY + " "
                + TAG_DESC_SMARTPHONE + " ";
        Item editedItem = new ItemBuilder(SONY).withTags(VALID_TAG_SMARTPHONE).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: undo editing the last item in the list -> last item restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last item in the list -> last item edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateItem(
                getModel().getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased()), editedItem);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit an item with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        assertCommandSuccess(command, index, SONY);

        /* Case: edit an item with new values same as another item's values but with different SKU -> edited */
        assertTrue(getModel().getInventory().getItemList().contains(SONY));
        index = INDEX_SECOND_ITEM;
        assertNotEquals(getModel().getFilteredItemList().get(index.getZeroBased()), SONY);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_OPPO + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_DIFFERENT + IMAGE_DESC_SONY + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        editedItem = new ItemBuilder(SONY).withName(VALID_NAME_OPPO).withSku(VALID_SKU_DIFFERENT).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: edit an item with new values same as another item's values but with different quantity and image
         * -> edited
         */
        index = INDEX_SECOND_ITEM;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_OPPO + IMAGE_DESC_SONY + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        editedItem = new ItemBuilder(SONY).withQuantity(VALID_QUANTITY_OPPO).withSku(VALID_SKU_DIFFERENT).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_ITEM;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Item itemToEdit = getModel().getFilteredItemList().get(index.getZeroBased());
        editedItem = new ItemBuilder(itemToEdit).withTags().build();
        assertCommandSuccess(command, index, editedItem);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered item list, edit index within bounds of inventory and item list -> edited */
        showItemsWithName(KEYWORD_MATCHING_SAMSUNG);
        index = INDEX_FIRST_ITEM;
        assertTrue(index.getZeroBased() < getModel().getFilteredItemList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_SONY;
        itemToEdit = getModel().getFilteredItemList().get(index.getZeroBased());
        editedItem = new ItemBuilder(itemToEdit).withName(VALID_NAME_SONY).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: filtered item list, edit index within bounds of inventory but out of bounds of item list
         * -> rejected
         */
        showItemsWithName(KEYWORD_MATCHING_SAMSUNG);
        int invalidIndex = getModel().getInventory().getItemList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_SONY,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a item card is selected -------------------------- */

        /* Case: selects first card in the item list, edit a item -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllItems();
        index = INDEX_FIRST_ITEM;
        selectItem(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_OPPO + PRICE_DESC_OPPO
                + QUANTITY_DESC_OPPO + SKU_DESC_OPPO + IMAGE_DESC_OPPO + TAG_DESC_GADGET;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new item's name
        assertCommandSuccess(command, index, OPPO, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_SONY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_SONY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredItemList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_SONY,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_SONY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased()
                + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased()
                + INVALID_PRICE_DESC, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased()
                + INVALID_QUANTITY_DESC, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid SKU -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased()
                + INVALID_SKU_DESC, Sku.MESSAGE_SKU_CONSTRAINTS);

        /* Case: invalid image -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased()
                + INVALID_IMAGE_DESC, Image.MESSAGE_IMAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a item with new values same as another item's values -> rejected */
        executeCommand(ItemUtil.getAddCommand(SONY));
        assertTrue(getModel().getInventory().getItemList().contains(SONY));
        index = INDEX_FIRST_ITEM;
        assertFalse(getModel().getFilteredItemList().get(index.getZeroBased()).equals(SONY));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different image -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_OPPO + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different quantity -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_OPPO + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different name -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_OPPO + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET + TAG_DESC_SMARTPHONE;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ITEM);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Item, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Item, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Item editedItem) {
        assertCommandSuccess(command, toEdit, editedItem, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the item at index {@code toEdit} being
     * updated to values specified {@code editedItem}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Item editedItem,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateItem(expectedModel.getFilteredItemList().get(toEdit.getZeroBased()), editedItem);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see InventorySystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
