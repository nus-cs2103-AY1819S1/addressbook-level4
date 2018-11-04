package systemtests;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_ITEM_NAME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_ITEM_PRICE_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_NAME_DESC_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_NAME_DESC_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_TAG_DESC_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_TAG_DESC_CHEESE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_RECIPE_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.restaurant.testutil.EventsUtil.postNow;
import static seedu.restaurant.testutil.menu.TypicalItems.BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.CHEESE_BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.FRIES;
import static seedu.restaurant.testutil.menu.TypicalItems.HAINANESE_CHICKEN_RICE;
import static seedu.restaurant.testutil.menu.TypicalItems.ICED_TEA;
import static seedu.restaurant.testutil.menu.TypicalItems.KEYWORD_MATCHING_EGG;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.logic.commands.menu.AddItemCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.menu.ItemUtil;

public class AddCommandSystemTest extends RestaurantBookSystemTest {

    private Model model;

    // Don't name it setUp() which overrides the one in RestaurantBookSystemTest, causing the tests to fail
    @Before
    public void prepare() {
        model = getModel();
        postNow(new LoginEvent(new AccountBuilder().build()));
    }

    @Test
    public void add() {
        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a item without tags to a non-empty restaurant book, command with leading spaces and
         * trailing spaces -> added
         */
        Item toAdd = BURGER;
        String command = "   " + AddItemCommand.COMMAND_WORD + ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Burger to the list -> Burger deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Burger to the list -> Burger added again */
        command = RedoCommand.COMMAND_WORD;
        model.addItem(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add an item with all fields same as another item in the restaurant book except name -> added */
        toAdd = new ItemBuilder(BURGER).withName(VALID_ITEM_NAME_FRIES).build();
        command = AddItemCommand.COMMAND_WORD + ITEM_NAME_DESC_FRIES + ITEM_PRICE_DESC_BURGER;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty restaurant book -> added */
        deleteAllItems();
        assertCommandSuccess(CHEESE_BURGER);

        /* Case: add a item with tags, command with parameters in random order -> added */
        toAdd = FRIES;
        command = AddItemCommand.COMMAND_WORD + ITEM_NAME_DESC_FRIES + ITEM_TAG_DESC_CHEESE
                + ITEM_PRICE_DESC_FRIES + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add an item, missing tags -> added */
        assertCommandSuccess(BURGER);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showItemsWithName(KEYWORD_MATCHING_EGG);
        assertCommandSuccess(HAINANESE_CHICKEN_RICE);

        /* ------------------------ Perform add operation while an item card is selected --------------------------- */

        /* Case: selects first card in the item list, add an item -> added, card selection remains unchanged */
        selectItem(Index.fromOneBased(1));
        assertCommandSuccess(ICED_TEA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate item -> rejected */
        command = ItemUtil.getAddItemCommand(HAINANESE_CHICKEN_RICE);
        assertCommandFailure(command, AddItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different price -> rejected */
        toAdd = new ItemBuilder(HAINANESE_CHICKEN_RICE).withPrice(VALID_ITEM_PRICE_FRIES).build();
        command = ItemUtil.getAddItemCommand(toAdd);
        assertCommandFailure(command, AddItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different tag -> rejected */
        toAdd = new ItemBuilder(HAINANESE_CHICKEN_RICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        command = ItemUtil.getAddItemCommand(toAdd);
        assertCommandFailure(command, AddItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: add a duplicate item except with different recipe -> rejected */
        toAdd = new ItemBuilder(HAINANESE_CHICKEN_RICE).withRecipe(VALID_ITEM_RECIPE_FRIES).build();
        command = ItemUtil.getAddItemCommand(toAdd);
        assertCommandFailure(command, AddItemCommand.MESSAGE_DUPLICATE_ITEM);

        /* Case: missing name -> rejected */
        command = AddItemCommand.COMMAND_WORD + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_BURGER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));

        /* Case: missing price -> rejected */
        command = AddItemCommand.COMMAND_WORD + ITEM_NAME_DESC_BURGER + ITEM_TAG_DESC_BURGER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + ItemUtil.getItemDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddItemCommand.COMMAND_WORD + INVALID_ITEM_NAME_DESC + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_BURGER;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        command = AddItemCommand.COMMAND_WORD + ITEM_NAME_DESC_BURGER + INVALID_ITEM_PRICE_DESC + ITEM_TAG_DESC_BURGER;
        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddItemCommand.COMMAND_WORD + ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_BURGER
                + INVALID_TAG_DESC;
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
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Item toAdd) {
        assertCommandSuccess(ItemUtil.getAddItemCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Item)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Item)
     */
    private void assertCommandSuccess(String command, Item toAdd) {
        Model expectedModel = getModel();
        expectedModel.addItem(toAdd);
        String expectedResultMessage = String.format(AddItemCommand.MESSAGE_SUCCESS, toAdd);

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
