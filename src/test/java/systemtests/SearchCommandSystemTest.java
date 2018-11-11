package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_D111;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_ENGINE;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author javenseow
public class SearchCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void search() {
        /* Case: search multiple persons in address book, command with leading spaces and trailing spaces
         * -> 1 person found
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_D111 + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON); // room of Benson
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where person list is displaying the person we are searching for
         * -> 1 person found
         */
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_D111;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person where person list is not displaying the person we are searching for -> 1 person found */
        command = SearchCommand.COMMAND_WORD + " B420";
        ModelHelper.setFilteredList(expectedModel, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple persons in hallper, 2 keywords in reversed order -> 2 persons found */
        command = SearchCommand.COMMAND_WORD + " biz soc";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple persons in hallper, 2 keywords with 1 repeat -> 2 persons found */
        command = SearchCommand.COMMAND_WORD + " soc biz soc";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple persons in hallper. 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = SearchCommand.COMMAND_WORD + " soc biz NonMatchingKeyword";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous search command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedMessageResult = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedMessageResult);

        /* Case: redo previous search command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedMessageResult = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedMessageResult);

        /* Case: search person in hallper, keyword is same as room but of different case
         * -> 1 person found
         */
        command = SearchCommand.COMMAND_WORD + " d111";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person in hallper, keyword is same as school but of different case
         * -> 2 persons found
         */
        command = SearchCommand.COMMAND_WORD + " engine";
        ModelHelper.setFilteredList(expectedModel, ELLE, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person in hallper, keyword is same as cca but of different case
         * -> 1 person found
         */
        command = SearchCommand.COMMAND_WORD + " basketball";
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person in hallper, different types of keywords -> 3 persons found */
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_D111 + " " + KEYWORD_MATCHING_ENGINE;
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person without any of the tag specified in hallper -> 0 persons found */
        command = SearchCommand.COMMAND_WORD + " choir";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search name of person in hallper -> 0 persons found */
        command = SearchCommand.COMMAND_WORD + " " + ALICE.getName().fullName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search email of person in hallper -> 0 persons found */
        command = SearchCommand.COMMAND_WORD + " " + ALICE.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search phone number of person in hallper -> 0 persons found */
        command = SearchCommand.COMMAND_WORD + " " + ALICE.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person in empty hallper -> 0 persons found */
        deleteAllPersons();
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_D111;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "SeArCh D111";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
