package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.restaurant.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.restaurant.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_FRIES;
import static seedu.restaurant.testutil.EventsUtil.postNow;
import static seedu.restaurant.testutil.menu.TypicalItems.CHOCO_CAKE;
import static seedu.restaurant.testutil.menu.TypicalItems.FRIES;
import static seedu.restaurant.testutil.menu.TypicalItems.FRUIT_CAKE;
import static seedu.restaurant.testutil.menu.TypicalItems.HAINANESE_CHICKEN_RICE;
import static seedu.restaurant.testutil.menu.TypicalItems.HAINANESE_PORK_CHOP;
import static seedu.restaurant.testutil.menu.TypicalItems.KEYWORD_MATCHING_CAKE;
import static seedu.restaurant.testutil.menu.TypicalItems.KEYWORD_MATCHING_EGG;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.logic.commands.menu.DeleteItemByIndexCommand;
import seedu.restaurant.logic.commands.menu.FindItemCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.account.AccountBuilder;

public class FindCommandSystemTest extends RestaurantBookSystemTest {

    private Model model;

    @Before
    public void prepare() {
        model = getModel();
        postNow(new LoginEvent(new AccountBuilder().build()));
    }

    @Test
    public void find() {
        /* Case: find multiple item in restaurant book, command with leading spaces and trailing spaces
         * -> 2 items found
         */
        String command = "   " + FindItemCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CAKE + "   ";
        ModelHelper.setFilteredList(model, CHOCO_CAKE, FRUIT_CAKE); // Both are "Cake"
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where item list is displaying the items we are finding
         * -> 2 items found
         */
        command = FindItemCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CAKE;
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find item where item list is not displaying the item we are finding -> 1 item found */
        command = FindItemCommand.COMMAND_WORD + " " + VALID_ITEM_NAME_FRIES;
        ModelHelper.setFilteredList(model, FRIES);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find multiple item in restaurant book, 2 keywords -> 2 item found */
        command = FindItemCommand.COMMAND_WORD + " Chocolate Fruit";
        ModelHelper.setFilteredList(model, CHOCO_CAKE, FRUIT_CAKE);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in restaurant book, 2 keywords in reversed order -> 2 items found */
        command = FindItemCommand.COMMAND_WORD + " Fruit Chocolate";
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in restaurant book, 2 keywords with 1 repeat -> 2 items found */
        command = FindItemCommand.COMMAND_WORD + " Chocolate Fruit Chocolate";
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in restaurant book, 2 matching keywords and 1 non-matching keyword
         * -> 2 items found
         */
        command = FindItemCommand.COMMAND_WORD + " Chocolate Fruit NonMatchingKeyWord";
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same item in restaurant book after deleting 1 of them -> 1 item found */
        executeCommand(DeleteItemByIndexCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getRestaurantBook().getItemList().contains(CHOCO_CAKE));
        command = FindItemCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CAKE;
        model = getModel();
        ModelHelper.setFilteredList(model, FRUIT_CAKE);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find item in restaurant book, keyword is same as name but of different case -> 1 item found */
        command = FindItemCommand.COMMAND_WORD + " CaKe";
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find item in restaurant book, keyword is substring of name -> 0 item found */
        command = FindItemCommand.COMMAND_WORD + " Ca";
        ModelHelper.setFilteredList(model);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find item in restaurant book, name is substring of keyword -> 0 item found */
        command = FindItemCommand.COMMAND_WORD + " Caker";
        ModelHelper.setFilteredList(model);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find item not in restaurant book -> 0 item found */
        command = FindItemCommand.COMMAND_WORD + " Kek";
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find price of item in restaurant book -> 0 item found */
        command = FindItemCommand.COMMAND_WORD + " " + FRUIT_CAKE.getPrice().toString();
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find percent of item in restaurant book -> 0 item found */
        command = FindItemCommand.COMMAND_WORD + " " + FRUIT_CAKE.getPercent();
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find tags of item in restaurant book -> 0 item found */
        List<Tag> tags = new ArrayList<>(FRUIT_CAKE.getTags());
        command = FindItemCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: find while a item is selected -> selected card deselected */
        showAllItems();
        selectItem(Index.fromOneBased(1));
        assertFalse(getItemListPanel().getHandleToSelectedCard().getName().equals(FRUIT_CAKE.getName().toString()));
        command = FindItemCommand.COMMAND_WORD + " " + HAINANESE_PORK_CHOP;
        ModelHelper.setFilteredList(model, HAINANESE_PORK_CHOP);
        assertCommandSuccess(command, model);
        assertSelectedCardDeselected();

        /* Case: find item in empty restaurant book -> 0 item found */
        deleteAllItems();
        command = FindItemCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_EGG;
        model = getModel();
        ModelHelper.setFilteredList(model, HAINANESE_CHICKEN_RICE);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd " + KEYWORD_MATCHING_CAKE;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display box
     * displays {@code Messages#MESSAGE_ITEMS_LISTED_OVERVIEW} with the number of people in the filtered list, and the
     * model related components equal to {@code expectedModel}. These verifications are done by {@code
     * RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW,
                expectedModel.getFilteredItemList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display box
     * displays {@code expectedResultMessage} and the model related components equal to the current model. These
     * verifications are done by {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String,
     * Model)}.
     *
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
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
