package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.Ride;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Ride rideToDelete = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RIDE_SUCCESS, rideToDelete);

        ModelManager expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());
        expectedModel.deleteRide(rideToDelete);
        expectedModel.commitThanePark();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Ride rideToDelete = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RIDE_SUCCESS, rideToDelete);

        Model expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());
        expectedModel.deleteRide(rideToDelete);
        expectedModel.commitThanePark();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_RIDE;
        // ensures that outOfBoundIndex is still in bounds of thanepark book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThanePark().getRideList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Ride rideToDelete = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());
        expectedModel.deleteRide(rideToDelete);
        expectedModel.commitThanePark();

        // delete -> first ride deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered ride list to showWithFilePath all persons
        expectedModel.undoThanePark();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first ride deleted again
        expectedModel.redoThanePark();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> thanepark book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        // single thanepark book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Ride} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted ride in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the ride object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Ride rideToDelete = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deleteRide(rideToDelete);
        expectedModel.commitThanePark();

        // delete -> deletes second ride in unfiltered ride list / first ride in filtered ride list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered ride list to showWithFilePath all persons
        expectedModel.undoThanePark();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(rideToDelete, model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second ride in unfiltered ride list
        expectedModel.redoThanePark();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RIDE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different ride -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to showWithFilePath no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredRideList(p -> false);

        assertTrue(model.getFilteredRideList().isEmpty());
    }
}
