package seedu.address.logic.commands.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeliverymanAtIndex;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToDeliveryManListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class DeliverymanSelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
        getTypicalDeliverymenList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
        getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredDeliverymenList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_THIRD);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredDeliverymenList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeliverymanAtIndex(model, INDEX_FIRST);
        showDeliverymanAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showDeliverymanAtIndex(model, INDEX_FIRST);
        showDeliverymanAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of order book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getDeliverymenList().getDeliverymenList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeliverymanSelectCommand selectFirstCommand = new DeliverymanSelectCommand(INDEX_FIRST);
        DeliverymanSelectCommand selectSecondCommand = new DeliverymanSelectCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        DeliverymanSelectCommand selectFirstCommandCopy = new DeliverymanSelectCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different common -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        DeliverymanSelectCommand selectCommand = new DeliverymanSelectCommand(index);
        String expectedMessage = String.format(DeliverymanSelectCommand.MESSAGE_SELECT_ORDER_SUCCESS,
            index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToDeliveryManListRequestEvent lastEvent =
            (JumpToDeliveryManListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        DeliverymanSelectCommand selectCommand = new DeliverymanSelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
