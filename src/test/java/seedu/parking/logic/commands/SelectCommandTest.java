package seedu.parking.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.logic.commands.CommandTestUtil.showCarparkAtIndex;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;
import static seedu.parking.testutil.TypicalIndexes.INDEX_SECOND_CARPARK;
import static seedu.parking.testutil.TypicalIndexes.INDEX_THIRD_CARPARK;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import seedu.parking.commons.core.Messages;
import seedu.parking.commons.core.index.Index;
import seedu.parking.commons.events.ui.JumpToListRequestEvent;
import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;
import seedu.parking.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    public SelectCommandTest() throws IOException {
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCarparkIndex = Index.fromOneBased(model.getFilteredCarparkList().size());

        assertExecutionSuccess(INDEX_FIRST_CARPARK);
        assertExecutionSuccess(INDEX_THIRD_CARPARK);
        assertExecutionSuccess(lastCarparkIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCarparkList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCarparkAtIndex(model, INDEX_FIRST_CARPARK);
        showCarparkAtIndex(expectedModel, INDEX_FIRST_CARPARK);

        assertExecutionSuccess(INDEX_FIRST_CARPARK);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCarparkAtIndex(model, INDEX_FIRST_CARPARK);
        showCarparkAtIndex(expectedModel, INDEX_FIRST_CARPARK);

        Index outOfBoundsIndex = INDEX_SECOND_CARPARK;
        // ensures that outOfBoundIndex is still in bounds of car park finder list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getCarparkFinder().getCarparkList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_CARPARK);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_CARPARK);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_CARPARK);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different carpark -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_CARPARK_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
