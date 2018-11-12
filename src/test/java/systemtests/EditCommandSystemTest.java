package systemtests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.EVENT_NAME_DESC_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.REMINDER_DURATION_LIST_1H;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_TYPE_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_UNTIL_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VENUE_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VENUE_DESC_MA3220;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.scheduler.testutil.TypicalEvents.KEYWORD_MATCHING_STARTUP;
import static seedu.scheduler.testutil.TypicalEvents.MA2101_JANUARY_1_2018_YEARLY;
import static seedu.scheduler.testutil.TypicalEvents.MA3220_JANUARY_1_2019_SINGLE;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FOURTH_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import java.util.List;

import org.junit.Test;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.RedoCommand;
import seedu.scheduler.logic.commands.UndoCommand;
import seedu.scheduler.logic.parser.Flag;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.testutil.EventBuilder;

public class EditCommandSystemTest extends SchedulerSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_EVENT;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + EVENT_NAME_DESC_MA3220
                + "  " + START_DATETIME_DESC_MA3220 + "  " + END_DATETIME_DESC_MA3220 + "  " + DESCRIPTION_DESC_MA3220
                + "  " + VENUE_DESC_MA3220 + "  " + TAG_DESC_PLAY + " " + REMINDER_DURATION_LIST_1H;
        Event firstEditedEvent = new EventBuilder(MA3220_JANUARY_1_2019_SINGLE)
                .withEventUid(model.getFilteredEventList().get(index.getZeroBased()).getEventUid())
                .withEventSetUid(model.getFilteredEventList().get(index.getZeroBased()).getEventSetUid())
                .withRepeatType(model.getFilteredEventList().get(index.getZeroBased()).getRepeatType()).build();
        assertCommandSuccess(command, index, firstEditedEvent);

        /* Case: undo editing the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last event in the list -> last event edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateEvent(
                getModel().getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), firstEditedEvent);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit an event with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + EVENT_NAME_DESC_MA3220
                + START_DATETIME_DESC_MA3220 + END_DATETIME_DESC_MA3220 + DESCRIPTION_DESC_MA3220
                + VENUE_DESC_MA3220 + TAG_DESC_PLAY + REMINDER_DURATION_LIST_1H;
        assertCommandSuccess(command, index, firstEditedEvent);

        /* Case: edit an event with new values some same as event's values some different -> edited */
        assertTrue(getModel().getScheduler().getEventList().contains(firstEditedEvent));
        index = INDEX_SECOND_EVENT;
        assertNotEquals(getModel().getFilteredEventList().get(index.getZeroBased()), MA3220_JANUARY_1_2019_SINGLE);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + EVENT_NAME_DESC_MA3220
                + START_DATETIME_DESC_MA3220 + END_DATETIME_DESC_MA3220 + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + TAG_DESC_SCHOOL + REMINDER_DURATION_LIST_1H;
        Event secondEditedEvent = new EventBuilder(MA3220_JANUARY_1_2019_SINGLE)
                .withEventUid(getModel().getFilteredEventList().get(index.getZeroBased()).getEventUid())
                .withEventSetUid(getModel().getFilteredEventList().get(index.getZeroBased()).getEventSetUid())
                .withDescription(VALID_DESCRIPTION_MA2101)
                .withVenue(VALID_VENUE_MA2101).withTags(VALID_TAG_SCHOOL)
                .withRepeatType(getModel().getFilteredEventList().get(index.getZeroBased()).getRepeatType()).build();
        assertCommandSuccess(command, index, secondEditedEvent);

        /* Case: edit an event with new values same as another event's values -> edited */
        assertTrue(getModel().getScheduler().getEventList().contains(secondEditedEvent));
        index = INDEX_SECOND_EVENT;
        assertNotEquals(getModel().getFilteredEventList().get(index.getZeroBased()), firstEditedEvent);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + EVENT_NAME_DESC_MA3220
                + START_DATETIME_DESC_MA3220 + END_DATETIME_DESC_MA3220 + DESCRIPTION_DESC_MA3220
                + VENUE_DESC_MA3220 + TAG_DESC_PLAY;
        Event thirdEditedEvent = new EventBuilder(firstEditedEvent)
                .withEventUid(getModel().getFilteredEventList().get(index.getZeroBased()).getEventUid())
                .withEventSetUid(getModel().getFilteredEventList().get(index.getZeroBased()).getEventSetUid()).build();
        assertCommandSuccess(command, index, thirdEditedEvent);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_EVENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Event eventToEdit = getModel().getFilteredEventList().get(index.getZeroBased());
        Event fourthEditedEvent = new EventBuilder(eventToEdit).withTags().build();
        assertCommandSuccess(command, index, fourthEditedEvent);

        /* Case: edit an event in an eventSet (Repeated event) with new event name
         * (with option -a, targeting all repeating events).
         * -> edited */
        index = INDEX_THIRD_EVENT;
        command = EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + EVENT_NAME_DESC_CS2103
                + " " + FLAG_ALL;
        Event firstRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased()))
                .withEventName(VALID_EVENT_NAME_CS2103)
                .build();
        Event secondRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 1))
                .withEventName(VALID_EVENT_NAME_CS2103)
                .build();
        Event thirdRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 2))
                .withEventName(VALID_EVENT_NAME_CS2103)
                .build();
        Event fourthRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 3))
                .withEventName(VALID_EVENT_NAME_CS2103)
                .build();
        assertCommandSuccess(command, index,
                List.of(firstRepeatedEvent, secondRepeatedEvent, thirdRepeatedEvent, fourthRepeatedEvent), FLAG_ALL);

        /* Case: edit an event in an eventSet (second repeated event in an eventSet) with new event name
         * (with option -a, targeting all repeating events).
         * -> edited */
        index = INDEX_FOURTH_EVENT;
        command = EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + EVENT_NAME_DESC_MA2101
                + " " + FLAG_ALL;
        firstRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() - 1))
                .withEventName(VALID_EVENT_NAME_MA2101)
                .build();
        secondRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased()))
                .withEventName(VALID_EVENT_NAME_MA2101)
                .build();
        thirdRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 1))
                .withEventName(VALID_EVENT_NAME_MA2101)
                .build();
        fourthRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 2))
                .withEventName(VALID_EVENT_NAME_MA2101)
                .build();
        assertCommandSuccess(command, index,
                List.of(firstRepeatedEvent, secondRepeatedEvent, thirdRepeatedEvent, fourthRepeatedEvent), FLAG_ALL);

        /* Case: edit an event in an eventSet (Repeated event) with new event name
         * (with option -u, targeting all upcoming events).
         * -> edited */
        command = EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + EVENT_NAME_DESC_MA3220
                + " " + FLAG_UPCOMING;
        firstRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased()))
                .withEventName(VALID_EVENT_NAME_MA3220)
                .build();
        secondRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 1))
                .withEventName(VALID_EVENT_NAME_MA3220)
                .build();
        thirdRepeatedEvent = new EventBuilder(model.getFilteredEventList().get(index.getZeroBased() + 2))
                .withEventName(VALID_EVENT_NAME_MA3220)
                .build();
        assertCommandSuccess(command, index,
                List.of(firstRepeatedEvent, secondRepeatedEvent, thirdRepeatedEvent), FLAG_UPCOMING);


        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered event list, edit index within bounds of scheduler and event list -> edited */
        showEventsWithEventName("MA3220");
        index = INDEX_FIRST_EVENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredEventList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + VENUE_DESC_MA3220;
        eventToEdit = getModel().getFilteredEventList().get(index.getZeroBased());
        Event fifthEditedEvent = new EventBuilder(eventToEdit).withVenue(VALID_VENUE_MA3220).build();
        assertCommandSuccess(command, index, fifthEditedEvent);

        /* Case: filtered event list, edit index within bounds of scheduler but out of bounds of event list
         * -> rejected
         */
        showEventsWithEventName(KEYWORD_MATCHING_STARTUP);
        int invalidIndex = getModel().getScheduler().getEventList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + VENUE_DESC_MA3220,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a event card is selected -------------------------- */

        /* Case: selects first card in the event list, edit a event -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllEvents();
        index = INDEX_FIRST_EVENT;
        selectEvent(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + EVENT_NAME_DESC_MA2101
                + " " + START_DATETIME_DESC_MA2101 + " " + END_DATETIME_DESC_MA2101 + " " + DESCRIPTION_DESC_MA2101
                + "  " + VENUE_DESC_MA2101 + "  " + TAG_DESC_SCHOOL;
        Event sixthEditedEvent = new EventBuilder(MA2101_JANUARY_1_2018_YEARLY)
                .withEventUid(getModel().getFilteredEventList().get(index.getZeroBased()).getEventUid())
                .withEventSetUid(getModel().getFilteredEventList().get(index.getZeroBased()).getEventSetUid())
                .withRepeatType(getModel().getFilteredEventList().get(index.getZeroBased()).getRepeatType()).build();
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new event's name
        assertCommandSuccess(command, index, sixthEditedEvent, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + EVENT_NAME_DESC_MA3220,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + EVENT_NAME_DESC_MA3220,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredEventList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + EVENT_NAME_DESC_MA3220,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + EVENT_NAME_DESC_MA3220,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_EVENT_NAME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        /* Case: Editing repeat type without flag */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + REPEAT_TYPE_DESC_MA3220,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_REPEAT_EVENT_FAIL));

        /* Case: Editing repeat until without flag */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + REPEAT_UNTIL_DATETIME_DESC_MA3220,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_REPEAT_EVENT_FAIL));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Event, Index)} except that
     * the browser url and selected card remain unchanged. This command is specific for single events.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Event, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Event editedEvent) {
        assertCommandSuccess(command, toEdit, editedEvent, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the event at index {@code toEdit} being
     * updated to values specified {@code editedEvent}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Event editedEvent,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        Event eventToEdit = expectedModel.getFilteredEventList().get(toEdit.getZeroBased());
        expectedModel.updateEvent(eventToEdit, editedEvent);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit.getEventName()),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the event at index {@code toEdit}
     * and its specified repeating events being updated to values specified {@code editedEvents}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, List<Event> editedEvents, Flag flag) {
        Model expectedModel = getModel();
        Event eventToEdit = expectedModel.getFilteredEventList().get(toEdit.getZeroBased());
        if (flag.equals(FLAG_UPCOMING)) {
            expectedModel.updateUpcomingEvents(eventToEdit, editedEvents);
        } else {
            expectedModel.updateRepeatingEvents(eventToEdit, editedEvents);
        }
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit.getEventName()));
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
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see SchedulerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
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
     * {@code SchedulerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
