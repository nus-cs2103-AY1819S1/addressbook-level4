package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.inventory.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.inventory.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.inventory.testutil.TypicalItems.GOOGLE;
import static seedu.inventory.testutil.TypicalItems.KEYWORD_MATCHING_SAMSUNG;
import static seedu.inventory.testutil.TypicalItems.LG;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;
import static seedu.inventory.testutil.TypicalItems.SAMSUNGNOTE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.DeleteCommand;
import seedu.inventory.logic.commands.FindCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.model.Model;
import seedu.inventory.model.tag.Tag;

public class FindCommandSystemTest extends InventorySystemTest {

    @Test
    public void find() {
        /* Case: find multiple items in inventory, command with leading spaces and trailing spaces
         * -> 2 items found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SAMSUNG + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SAMSUNG, SAMSUNGNOTE); // Samsung S9 and Note 9 contains 'Samsung'
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where item list is displaying the items we are finding
         * -> 2 items found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SAMSUNG;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item where item list is not displaying the item we are finding -> 1 item found */
        command = FindCommand.COMMAND_WORD + " Google";
        ModelHelper.setFilteredList(expectedModel, GOOGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in inventory, 2 keywords -> 2 items found */
        command = FindCommand.COMMAND_WORD + " S9 Note";
        ModelHelper.setFilteredList(expectedModel, SAMSUNG, SAMSUNGNOTE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in inventory, 2 keywords in reversed order -> 2 items found */
        command = FindCommand.COMMAND_WORD + " Note S9";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in inventory, 2 keywords with 1 repeat -> 2 items found */
        command = FindCommand.COMMAND_WORD + " S9 Note S9";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in inventory, 2 matching keywords and 1 non-matching keyword
         * -> 2 items found
         */
        command = FindCommand.COMMAND_WORD + " S9 Note NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same items in inventory after deleting 1 of them -> 1 item found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getInventory().getItemList().contains(SAMSUNGNOTE));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SAMSUNG;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SAMSUNG);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item in inventory, keyword is same as name but of different case -> 1 item found */
        command = FindCommand.COMMAND_WORD + " SamSung";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item in inventory, keyword is substring of name -> 0 items found */
        command = FindCommand.COMMAND_WORD + " Sam";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item in inventory, name is substring of keyword -> 0 items found */
        command = FindCommand.COMMAND_WORD + " Samsun";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item not in inventory -> 0 items found */
        command = FindCommand.COMMAND_WORD + " Windows";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find quantity of item in inventory -> 0 items found */
        command = FindCommand.COMMAND_WORD + " " + LG.getQuantity().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find image of item in inventory -> 0 items found */
        command = FindCommand.COMMAND_WORD + " " + LG.getImage().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find SKU of item in inventory -> 0 items found */
        command = FindCommand.COMMAND_WORD + " " + LG.getSku().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of item in inventory -> 0 items found */
        List<Tag> tags = new ArrayList<>(LG.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a item is selected -> selected card deselected */
        showAllItems();
        selectItem(Index.fromOneBased(1));
        assertFalse(getItemListPanel().getHandleToSelectedCard().getName().equals(LG.getName().fullName));
        command = FindCommand.COMMAND_WORD + " LG";
        ModelHelper.setFilteredList(expectedModel, LG);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find item in empty inventory -> 0 items found */
        deleteAllItems();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SAMSUNG;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SAMSUNG);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Samsung";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ITEMS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_ITEMS_LISTED_OVERVIEW, expectedModel.getFilteredItemList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
