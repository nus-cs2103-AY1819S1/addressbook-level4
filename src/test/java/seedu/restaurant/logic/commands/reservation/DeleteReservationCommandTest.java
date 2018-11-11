package seedu.restaurant.logic.commands.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.reservation.ReservationCommandTestUtil.showReservationAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.reservation.Reservation;

//@@author m4dkip
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteReservationCommand}.
 */
public class DeleteReservationCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteReservationCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
                reservationToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReservationAtIndex(model, INDEX_FIRST);

        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteReservationCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
                reservationToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitRestaurantBook();
        showNoReservation(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showReservationAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getReservationList().size());

        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first reservation deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered reservation list to show all reservations
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first reservation deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(outOfBoundIndex);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Reservation} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be
     * shown now. Verify that the index of the previously deleted reservation in the unfiltered list is different
     * from the index at the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the
     * reservation object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameReservationDeleted() throws Exception {
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showReservationAtIndex(model, INDEX_SECOND);
        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second reservation in unfiltered list / first reservation in filtered list
        deleteReservationCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered reservation list to show all reservations
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(reservationToDelete, model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second reservation in unfiltered reservation list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteReservationCommand deleteFirstCommand = new DeleteReservationCommand(INDEX_FIRST);
        DeleteReservationCommand deleteSecondCommand = new DeleteReservationCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteReservationCommand deleteFirstCommandCopy = new DeleteReservationCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different reservation -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReservation(Model model) {
        model.updateFilteredReservationList(p -> false);

        assertTrue(model.getFilteredReservationList().isEmpty());
    }
}
