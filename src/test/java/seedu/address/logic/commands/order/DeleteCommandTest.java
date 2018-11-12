package seedu.address.logic.commands.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        expectedModel.commitOrderBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST);

        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        expectedModel.commitOrderBook();
        showNoOrder(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        expectedModel.commitOrderBook();

        // delete -> first common deleted
        deleteCommand.execute(model, commandHistory);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * 1. Deletes a {@code Order} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted common in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the order object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameOrderDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());

        showOrderAtIndex(model, INDEX_SECOND);
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteOrder(orderToDelete);
        expectedModel.commitOrderBook();

        // delete -> deletes second common in unfiltered common list / first common in filtered common list
        deleteCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different common -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);

        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}
