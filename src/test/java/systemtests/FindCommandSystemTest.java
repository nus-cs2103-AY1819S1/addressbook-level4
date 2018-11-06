package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.meeting.commons.core.Messages.MESSAGE_PERSONS_FOUND_OVERVIEW;
import static seedu.meeting.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.meeting.testutil.TypicalPersons.BENSON;
import static seedu.meeting.testutil.TypicalPersons.CARL;
import static seedu.meeting.testutil.TypicalPersons.DANIEL;
import static seedu.meeting.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.commands.DeleteCommand;
import seedu.meeting.logic.commands.FindCommand;
import seedu.meeting.logic.commands.FindPersonCommand;
import seedu.meeting.logic.commands.RedoCommand;
import seedu.meeting.logic.commands.UndoCommand;
import seedu.meeting.model.Model;
import seedu.meeting.model.tag.Tag;

public class FindCommandSystemTest extends MeetingBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in MeetingBook, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM
            + " s/" + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find multiple persons in MeetingBook, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " s/Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find multiple persons in MeetingBook, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " s/Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find multiple persons in MeetingBook, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " s/"
            + "Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find multiple persons in MeetingBook, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM
            + " s/Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in MeetingBook after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getMeetingBook().getPersonList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find person in MeetingBook, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find person in MeetingBook, keyword is substring of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find person in MeetingBook, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find person not in MeetingBook -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find phone number of person in MeetingBook -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find address of person in MeetingBook -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " "
            + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find email of person in MeetingBook -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find tags of person in MeetingBook -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty MeetingBook -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedPersonCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_FOUND_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertPersonListDisplaysExpected(expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertPersonListDisplaysExpected(expectedModel);
        assertSelectedPersonCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
