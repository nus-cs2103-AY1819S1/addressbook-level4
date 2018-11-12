package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCalendarEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalScheduler;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ELEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectEventCommand}.
 */
public class SelectEventCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredAndSortedCalendarEventList().size());

        assertExecutionSuccess(INDEX_FIRST_ELEMENT);
        assertExecutionSuccess(INDEX_THIRD_ELEMENT);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredAndSortedCalendarEventList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);
        showCalendarEventAtIndex(expectedModel, INDEX_FIRST_ELEMENT);

        assertExecutionSuccess(INDEX_FIRST_ELEMENT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCalendarEventAtIndex(model, INDEX_FIRST_ELEMENT);
        showCalendarEventAtIndex(expectedModel, INDEX_FIRST_ELEMENT);

        Index outOfBoundsIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getScheduler().getCalendarEventList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CALENDAR_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectEventCommand selectFirstCommand = new SelectEventCommand(INDEX_FIRST_ELEMENT);
        SelectEventCommand selectSecondCommand = new SelectEventCommand(INDEX_SECOND_ELEMENT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectEventCommand selectFirstCommandCopy = new SelectEventCommand(INDEX_FIRST_ELEMENT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different calendarevent -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectEventCommand} with the given {@code index} and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectEventCommand selectEventCommand = new SelectEventCommand(index);
        String expectedMessage = String.format(SelectEventCommand.MESSAGE_SELECT_CALENDAR_EVENT_SUCCESS,
            index.getOneBased());

        assertCommandSuccess(selectEventCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectEventCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectEventCommand selectEventCommand = new SelectEventCommand(index);
        assertCommandFailure(selectEventCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
