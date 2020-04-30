package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_ITEM_NAME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_ITEM_PRICE_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_NAME_DESC_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_NAME_DESC_CHEESE_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_NAME_DESC_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_NAME_DESC_ICED_TEA;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_ICED_TEA;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_TAG_DESC_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_TAG_DESC_CHEESE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_CHEESE_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.restaurant.testutil.EventsUtil.postNow;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.menu.TypicalItems.FRIES;
import static seedu.restaurant.testutil.menu.TypicalItems.ICED_TEA;
import static seedu.restaurant.testutil.menu.TypicalItems.KEYWORD_MATCHING_EGG;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.logic.commands.menu.EditItemCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.menu.ItemUtil;

public class EditCommandSystemTest extends RestaurantBookSystemTest {

    private Model model;

    @Before
    public void prepare() {
        model = getModel();
        postNow(new LoginEvent(new AccountBuilder().build()));
    }

    @Test
    public void edit() {
        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST;
        String command = " " + EditItemCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + ITEM_NAME_DESC_FRIES + "  " + ITEM_PRICE_DESC_FRIES + "  " + ITEM_TAG_DESC_CHEESE;
        Item editedItem = new ItemBuilder(FRIES).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: undo editing the last item in the list -> last item restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last item in the list -> last item edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateItem(getModel().getFilteredItemList().get(INDEX_FIRST.getZeroBased()), editedItem);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a item with new values same as another item's values but with different name -> edited */
        assertTrue(getModel().getRestaurantBook().getItemList().contains(FRIES));
        index = INDEX_SECOND;
        assertNotEquals(getModel().getFilteredItemList().get(index.getZeroBased()), FRIES);
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + ITEM_NAME_DESC_BURGER
                + ITEM_PRICE_DESC_FRIES + " " + ITEM_TAG_DESC_CHEESE + " ";
        editedItem = new ItemBuilder(FRIES).withName(VALID_ITEM_NAME_BURGER).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: edit a item with new values same as another item's values but with different price and tag
         * -> rejected
         */
        index = INDEX_SECOND;
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + ITEM_NAME_DESC_FRIES
                + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_BURGER;
        assertCommandFailure(command, EditItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST;
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Item itemToEdit = getModel().getFilteredItemList().get(index.getZeroBased());
        editedItem = new ItemBuilder(itemToEdit).withTags().build();
        assertCommandSuccess(command, index, editedItem);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered item list, edit index within bounds of restaurant book and item list -> edited */
        showItemsWithName(KEYWORD_MATCHING_EGG);
        index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredItemList().size());
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + " " + ITEM_NAME_DESC_CHEESE_BURGER;
        itemToEdit = getModel().getFilteredItemList().get(index.getZeroBased());
        editedItem = new ItemBuilder(itemToEdit).withName(VALID_ITEM_NAME_CHEESE_BURGER).build();
        assertCommandSuccess(command, index, editedItem);

        /* Case: filtered item list, edit index within bounds of restaurant book but out of bounds of item list
         * -> rejected
         */
        showItemsWithName(KEYWORD_MATCHING_EGG);
        int invalidIndex = getModel().getRestaurantBook().getItemList().size();
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " " + invalidIndex + ITEM_NAME_DESC_FRIES,
                MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a item card is selected -------------------------- */

        /* Case: selects first card in the item list, edit a item -> edited, card selection remains unchanged */
        showAllItems();
        index = INDEX_FIRST;
        selectItem(index);
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + ITEM_NAME_DESC_FRIES
                        + ITEM_PRICE_DESC_FRIES + ITEM_TAG_DESC_CHEESE;
        assertCommandSuccess(command, index, FRIES, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " 0" + ITEM_NAME_DESC_FRIES,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " -1" + ITEM_NAME_DESC_FRIES,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredItemList().size() + 1;
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " " + invalidIndex + ITEM_NAME_DESC_FRIES,
                MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + ITEM_NAME_DESC_FRIES,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                EditItemCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_ITEM_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_ITEM_PRICE_DESC, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: adds new item, edit it with new values same as another item's values -> rejected */
        executeCommand(ItemUtil.getAddItemCommand(ICED_TEA));
        assertTrue(getModel().getRestaurantBook().getItemList().contains(ICED_TEA));
        index = INDEX_FIRST;
        assertFalse(getModel().getFilteredItemList().get(index.getZeroBased()).equals(ICED_TEA));
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + ITEM_NAME_DESC_ICED_TEA
                + ITEM_PRICE_DESC_ICED_TEA + ITEM_TAG_DESC_CHEESE;
        assertCommandFailure(command, EditItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different tags -> rejected */
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + ITEM_NAME_DESC_BURGER
                + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_CHEESE;
        assertCommandFailure(command, EditItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: edit a item with new values same as another item's values but with different price -> rejected */
        command = EditItemCommand.COMMAND_WORD + " " + index.getOneBased() + ITEM_NAME_DESC_BURGER
                + ITEM_PRICE_DESC_FRIES + ITEM_NAME_DESC_BURGER;
        assertCommandFailure(command, EditItemCommand.MESSAGE_DUPLICATE_ITEM);
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
                String.format(EditItemCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem), expectedSelectedCardIndex);
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
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see RestaurantBookSystemTest#assertSelectedCardChanged(Index)
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
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
