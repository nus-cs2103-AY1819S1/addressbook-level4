package seedu.restaurant.logic.commands.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.sales.SalesCommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.sales.SalesCommandTestUtil.showRecordAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.sales.JumpToRecordListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.ui.testutil.EventsCollectorRule;

//@@author HyperionNKJ
/**
 * Contains integration tests (interaction with the Model) for {@code SelectSalesCommand}.
 */
public class SelectSalesCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRecordIndex = Index.fromOneBased(model.getFilteredRecordList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_THIRD);
        assertExecutionSuccess(lastRecordIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST);
        showRecordAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRecordAtIndex(model, INDEX_FIRST);
        showRecordAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getRestaurantBook().getRecordList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectSalesCommand selectFirstCommand = new SelectSalesCommand(INDEX_FIRST);
        SelectSalesCommand selectSecondCommand = new SelectSalesCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectSalesCommand selectFirstCommandCopy = new SelectSalesCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different sales -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectSalesCommand} with the given {@code index}, and checks that {@code
     * JumpToRecordListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectSalesCommand selectSalesCommand = new SelectSalesCommand(index);
        SalesRecord expectedRecord = expectedModel.getFilteredRecordList().get(index.getZeroBased());
        String expectedMessage = String.format(String.format(SelectSalesCommand.MESSAGE_SELECT_RECORD_SUCCESS,
                expectedRecord.getDate().toString() + " " + expectedRecord.getName().toString(), index.getOneBased()));

        assertCommandSuccess(selectSalesCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToRecordListRequestEvent lastEvent =
                (JumpToRecordListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectSalesCommand} with the given {@code index}, and checks that a {@code CommandException} is
     * thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectSalesCommand selectSalesCommand = new SelectSalesCommand(index);
        assertCommandFailure(selectSalesCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
