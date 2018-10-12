package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.JANUARY_2_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.JANUARY_3_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.KEYWORD_MATCHING_JANUARY;
import static seedu.address.testutil.TypicalEvents.PLAY_JANUARY_1_2018_SINGLE;

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

public class FindCommandSystemTest extends SchedulerSystemTest {

    @Test
    public void find() {
        /* Case: find multiple events in scheduler, command with leading spaces and trailing spaces
         * -> 3 events found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JANUARY + "   ";
        Model expectedModel = getModel();
        // event names of january 1 2018, january 2 2018 and january 3 2018 contains "January"
        ModelHelper.setFilteredList(expectedModel, JANUARY_1_2018_SINGLE,
                JANUARY_2_2018_SINGLE, JANUARY_3_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where event list is displaying the events we are finding
         * -> 3 events found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JANUARY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find event where event list is not displaying the event we are finding -> 1 event found */
        command = FindCommand.COMMAND_WORD + " Play";
        ModelHelper.setFilteredList(expectedModel, PLAY_JANUARY_1_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple events in scheduler, 2 keywords -> 4 events found */
        command = FindCommand.COMMAND_WORD + " January Play";
        ModelHelper.setFilteredList(expectedModel, JANUARY_1_2018_SINGLE, JANUARY_2_2018_SINGLE,
                JANUARY_3_2018_SINGLE, PLAY_JANUARY_1_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple events in scheduler, 2 keywords in reversed order -> 4 events found */
        command = FindCommand.COMMAND_WORD + " Play January";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple events in scheduler, 2 keywords with 1 repeat -> 4 events found */
        command = FindCommand.COMMAND_WORD + " Play January Play";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple events in scheduler, 2 matching keywords and 1 non-matching keyword
         * -> 4 events found
         */
        command = FindCommand.COMMAND_WORD + " Play January NonMatchingKeyWord";
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

        /* Case: find events in scheduler after deleting 1 of them -> 2 event found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getScheduler().getEventList().contains(JANUARY_1_2018_SINGLE));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JANUARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, JANUARY_2_2018_SINGLE,
                JANUARY_3_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find event in scheduler, keyword is same as name but of different case -> 2 event found */
        command = FindCommand.COMMAND_WORD + " jAnUaRy";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find event in scheduler, keyword is substring of name -> 0 events found */
        command = FindCommand.COMMAND_WORD + " Jan";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in scheduler, name is substring of keyword -> 0 events found */
        command = FindCommand.COMMAND_WORD + " Januarys";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find event not in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " Study";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find start date time of event in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " " + PLAY_JANUARY_1_2018_SINGLE.getStartDateTime().getPrettyString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find end date time of event in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " " + PLAY_JANUARY_1_2018_SINGLE.getEndDateTime().getPrettyString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find description of event in scheduler (description words not matching any event name)
         * -> 0 events found
         */
        command = FindCommand.COMMAND_WORD + " " + PLAY_JANUARY_1_2018_SINGLE.getDescription().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find description of event in scheduler (description words match an event name)
         * -> 2 events found
         */
        command = FindCommand.COMMAND_WORD + " " + JANUARY_2_2018_SINGLE.getDescription().value;
        ModelHelper.setFilteredList(expectedModel, JANUARY_2_2018_SINGLE,
                JANUARY_3_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority of event in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " " + PLAY_JANUARY_1_2018_SINGLE.getPriority().name();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find venue of event in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " " + PLAY_JANUARY_1_2018_SINGLE.getVenue().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find repeat type of event in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " " + PLAY_JANUARY_1_2018_SINGLE.getRepeatType().name();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find repeat until date time of event in scheduler -> 0 events found */
        command = FindCommand.COMMAND_WORD + " " +
                PLAY_JANUARY_1_2018_SINGLE.getRepeatUntilDateTime().getPrettyString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of event in scheduler -> 0 events found */ //[TODO] fix tag
        //List<Tag> tags = new ArrayList<>(PLAY_JANUARY_1_2018_SINGLE.getTags());
        //command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        //assertCommandSuccess(command, expectedModel);
        //assertSelectedCardUnchanged();

        /* Case: find while a event is selected -> selected card deselected */
        showAllEvents();
        selectEvent(Index.fromOneBased(1));
        assertFalse(getEventListPanel().getHandleToSelectedCard().getEventName()
                .equals(PLAY_JANUARY_1_2018_SINGLE.getEventName().value));
        command = FindCommand.COMMAND_WORD + " Play";
        ModelHelper.setFilteredList(expectedModel, PLAY_JANUARY_1_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find event in empty scheduler -> 0 events found */
        deleteAllEvents();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JANUARY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, PLAY_JANUARY_1_2018_SINGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Play";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EVENTS_LISTED_OVERVIEW} with the number of event in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_EVENTS_LISTED_OVERVIEW, expectedModel.getFilteredEventList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
