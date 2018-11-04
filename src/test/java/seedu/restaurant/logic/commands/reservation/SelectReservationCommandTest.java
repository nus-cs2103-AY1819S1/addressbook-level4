package seedu.restaurant.logic.commands.reservation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.reservation.ReservationCommandTestUtil.showReservationAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.reservation.JumpToReservationListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.ui.testutil.EventsCollectorRule;

//@@author m4dkip
/**
 * Contains integration tests (interaction with the Model) for {@code SelectReservationCommand}.
 */
public class SelectReservationCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastReservationIndex = Index.fromOneBased(model.getFilteredReservationList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_THIRD);
        assertExecutionSuccess(lastReservationIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReservationAtIndex(model, INDEX_FIRST);
        showReservationAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showReservationAtIndex(model, INDEX_FIRST);
        showReservationAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getRestaurantBook().getReservationList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectReservationCommand selectFirstCommand = new SelectReservationCommand(INDEX_FIRST);
        SelectReservationCommand selectSecondCommand = new SelectReservationCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectReservationCommand selectFirstCommandCopy = new SelectReservationCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different reservation -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectReservationCommand} with the given {@code index}, and checks that
     * {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectReservationCommand selectCommand = new SelectReservationCommand(index);
        String expectedMessage = String.format(SelectReservationCommand.MESSAGE_SELECT_RESERVATION_SUCCESS,
                index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToReservationListRequestEvent lastEvent =
                (JumpToReservationListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectReservationCommand} with the given {@code index}, and checks that a
     * {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectReservationCommand selectCommand = new SelectReservationCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
