package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.showRideAtIndex;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_THIRD_RIDE;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Rule;
import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.commons.events.ui.JumpToListRequestEvent;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRideIndex = Index.fromOneBased(model.getFilteredRideList().size());

        assertExecutionSuccess(INDEX_FIRST_RIDE);
        assertExecutionSuccess(INDEX_THIRD_RIDE);
        assertExecutionSuccess(lastRideIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRideAtIndex(model, INDEX_FIRST_RIDE);
        showRideAtIndex(expectedModel, INDEX_FIRST_RIDE);

        assertExecutionSuccess(INDEX_FIRST_RIDE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRideAtIndex(model, INDEX_FIRST_RIDE);
        showRideAtIndex(expectedModel, INDEX_FIRST_RIDE);

        Index outOfBoundsIndex = INDEX_SECOND_RIDE;
        // ensures that outOfBoundIndex is still in bounds of thanepark book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getThanePark().getRideList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand selectFirstCommand = new ViewCommand(INDEX_FIRST_RIDE);
        ViewCommand selectSecondCommand = new ViewCommand(INDEX_SECOND_RIDE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        ViewCommand selectFirstCommandCopy = new ViewCommand(INDEX_FIRST_RIDE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different ride -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code ViewCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        ViewCommand viewCommand = new ViewCommand(index);
        String expectedName = expectedModel.getFilteredRideList().get(index.getZeroBased()).getName().fullName;
        String expectedMessage = String.format(ViewCommand.MESSAGE_SELECT_RIDE_SUCCESS, expectedName,
                index.getOneBased());

        assertCommandSuccess(viewCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code ViewCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ViewCommand viewCommand = new ViewCommand(index);
        assertCommandFailure(viewCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
