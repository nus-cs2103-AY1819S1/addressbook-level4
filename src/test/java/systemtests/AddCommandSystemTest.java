package systemtests;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.TAG_DESC_GADGET;
import static seedu.inventory.logic.commands.CommandTestUtil.TAG_DESC_SMARTPHONE;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.inventory.testutil.TypicalItems.GOOGLE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.KEYWORD_MATCHING_SAMSUNG;
import static seedu.inventory.testutil.TypicalItems.NOKIA;
import static seedu.inventory.testutil.TypicalItems.OPPO;
import static seedu.inventory.testutil.TypicalItems.SONY;
import static seedu.inventory.testutil.TypicalItems.XIAOMI;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.AddCommand;
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

public class AddCommandSystemTest extends InventorySystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an item without tags to a non-empty inventory list, command with leading spaces and trailing spaces
         * -> added
         */
        Item toAdd = OPPO;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_OPPO + "  " + PRICE_DESC_OPPO + "  "
                + QUANTITY_DESC_OPPO + " " + SKU_DESC_OPPO + "   " + IMAGE_DESC_OPPO + "   " + TAG_DESC_GADGET + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addItem(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a item with all fields same as another item in the inventory book except quantity and SKU
         * -> added
         */
        toAdd = new ItemBuilder(OPPO).withQuantity(VALID_QUANTITY_SONY).withSku(VALID_SKU_SONY).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty inventory list -> added */
        deleteAllItems();
        assertCommandSuccess(IPHONE);

        /* Case: add a item with tags, command with parameters in random order -> added */
        toAdd = SONY;
        command = AddCommand.COMMAND_WORD + TAG_DESC_GADGET + PRICE_DESC_SONY + QUANTITY_DESC_SONY
                + IMAGE_DESC_SONY + NAME_DESC_SONY + TAG_DESC_SMARTPHONE + SKU_DESC_SONY;
        assertCommandSuccess(command, toAdd);

        /* Case: add a item, missing tags -> added */
        assertCommandSuccess(NOKIA);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the item list before adding -> added */
        showItemsWithName(KEYWORD_MATCHING_SAMSUNG);
        assertCommandSuccess(XIAOMI);

        /* ------------------------ Perform add operation while a item card is selected --------------------------- */

        /* Case: selects first card in the item list, add a item -> added, card selection remains unchanged */
        selectItem(Index.fromOneBased(1));
        assertCommandSuccess(GOOGLE);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate item -> rejected */
        command = ItemUtil.getAddCommand(NOKIA);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different quantity -> rejected */
        toAdd = new ItemBuilder(NOKIA).withQuantity(VALID_QUANTITY_SONY).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different SKU -> rejected */
        toAdd = new ItemBuilder(NOKIA).withSku(VALID_SKU_SONY).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different image -> rejected */
        toAdd = new ItemBuilder(NOKIA).withImage(VALID_IMAGE_SONY).build();
        command = ItemUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different tags -> rejected */
        command = ItemUtil.getAddCommand(NOKIA) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO + SKU_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing price -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + QUANTITY_DESC_OPPO + SKU_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing quantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + SKU_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing SKU -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing image -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO + SKU_DESC_OPPO;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + ItemUtil.getItemDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO
                + SKU_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + INVALID_PRICE_DESC + QUANTITY_DESC_OPPO
                + SKU_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + INVALID_QUANTITY_DESC
                + SKU_DESC_OPPO + IMAGE_DESC_OPPO;
        assertCommandFailure(command, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid SKU -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO
                + INVALID_SKU_DESC + IMAGE_DESC_OPPO;
        assertCommandFailure(command, Sku.MESSAGE_SKU_CONSTRAINTS);

        /* Case: invalid image -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO
                + SKU_DESC_OPPO + INVALID_IMAGE_DESC;
        assertCommandFailure(command, Image.MESSAGE_IMAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_OPPO + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO
                + SKU_DESC_OPPO + IMAGE_DESC_OPPO + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code ItemListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Item toAdd) {
        assertCommandSuccess(ItemUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Item)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Item)
     */
    private void assertCommandSuccess(String command, Item toAdd) {
        Model expectedModel = getModel();
        expectedModel.addItem(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Item)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ItemListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Item)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ItemListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
