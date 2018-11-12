package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.superta.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.superta.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;
import static seedu.superta.testutil.TypicalSuperTaClient.CARL;
import static seedu.superta.testutil.TypicalSuperTaClient.DANIEL;
import static seedu.superta.testutil.TypicalSuperTaClient.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import seedu.superta.commons.core.index.Index;
import seedu.superta.logic.commands.DeleteCommand;
import seedu.superta.logic.commands.FindCommand;
import seedu.superta.logic.commands.RedoCommand;
import seedu.superta.logic.commands.UndoCommand;
import seedu.superta.model.Model;
import seedu.superta.model.tag.Tag;

public class FindCommandSystemTest extends SuperTaClientSystemTest {

    /**
     * Old find test.
     */
    public void find() {
        // TODO: Maybe fix this test? Currently breaking because of changes.

        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where student list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student where student list is not displaying the student we are finding -> 1 student found */
        command = FindCommand.COMMAND_WORD + " n/Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book using name and phone number. */
        command = FindCommand.COMMAND_WORD + " n/Benson p/87652533";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book using name and phone number in reversed order. */
        command = FindCommand.COMMAND_WORD + " p/87652533 n/Benson";
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

        /* Case: find same persons in address book after deleting 1 of them -> 1 student found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getSuperTaClient().getStudentList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student in address book, keyword is same as name but of different case -> 1 student found */
        command = FindCommand.COMMAND_WORD + " n/MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student in address book, keyword is substring of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of student in address book -> 1 student found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + DANIEL.getPhone().value;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of student in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of student in address book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a student is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " n/Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find student in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredStudentList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
