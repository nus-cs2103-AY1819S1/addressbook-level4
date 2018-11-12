package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_RESTAURANTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalRestaurants.KEYWORD_MATCHING_STARBUCKS;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_B;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_C;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_D;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple restaurants in address book, command with leading spaces and trailing spaces
         * -> 2 restaurants found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_STARBUCKS + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, RESTAURANT_D);
        // finding starbucks
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where restaurant list is displaying the restaurants we are finding
         * -> 2 restaurants found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_STARBUCKS;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find restaurant where restaurant list is not displaying the restaurant we are finding
        -> 1 restaurant found */
        command = FindCommand.COMMAND_WORD + " Subway";
        ModelHelper.setFilteredList(expectedModel, RESTAURANT_C);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple restaurants in address book, 2 keywords -> 2 restaurants found */
        command = FindCommand.COMMAND_WORD + " The Royals Bistro";
        ModelHelper.setFilteredList(expectedModel, RESTAURANT_B);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple restaurants in address book, 2 keywords in reversed order -> 2 restaurants found */
        command = FindCommand.COMMAND_WORD + " Bistro Royals";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple restaurants in address book, 2 keywords with 1 repeat -> 2 restaurants found */
        command = FindCommand.COMMAND_WORD + " Royals Bistro Bistro";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple restaurants in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 restaurants found
         */
        command = FindCommand.COMMAND_WORD + " Royals Bistro NonMatchingKeyWord";
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

        /* Case: find same restaurants in address book after deleting 1 of them -> 1 restaurant found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getRestaurantList().contains(RESTAURANT_B));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_STARBUCKS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, RESTAURANT_D);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find restaurant in address book, keyword is same as name but of different case -> 1 restaurant found */
        command = FindCommand.COMMAND_WORD + " StArBuCkS";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find restaurant in address book, keyword is substring of name -> 0 restaurants found */
        command = FindCommand.COMMAND_WORD + " star";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find restaurant in address book, name is substring of keyword -> 0 restaurants found */
        command = FindCommand.COMMAND_WORD + " Starbucksssss";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find restaurant not in address book -> 0 restaurants found */
        command = FindCommand.COMMAND_WORD + " sushi";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of restaurant in address book -> 0 restaurants found */
        command = FindCommand.COMMAND_WORD + " " + RESTAURANT_D.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of restaurant in address book -> 0 restaurants found */
        command = FindCommand.COMMAND_WORD + " " + RESTAURANT_D.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of restaurant in address book -> 0 restaurants found */
        List<Tag> tags = new ArrayList<>(RESTAURANT_D.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a restaurant is selected -> selected card deselected */
        showAllRestaurants();
        selectRestaurant(Index.fromOneBased(1));
        assertFalse(getRestaurantListPanel().getHandleToSelectedCard().getName()
                .equals(RESTAURANT_D.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Starbucks";
        ModelHelper.setFilteredList(expectedModel, RESTAURANT_D);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find restaurant in empty address book -> 0 restaurants found */
        deleteAllRestaurants();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_STARBUCKS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, RESTAURANT_D);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Starbucks";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_RESTAURANTS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_RESTAURANTS_LISTED_OVERVIEW, expectedModel.getFilteredRestaurantList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
