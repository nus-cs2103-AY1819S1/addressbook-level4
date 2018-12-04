package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.showRideAtIndex;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_THIRD_RIDE;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.testutil.UpdateRideDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * UpdateCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Ride editedRide = new RideBuilder().build();
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder(editedRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_RIDE, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getThanePark()), new UserPrefs());
        expectedModel.updateRide(model.getFilteredRideList().get(0), editedRide);
        expectedModel.commitThanePark();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRide = Index.fromOneBased(model.getFilteredRideList().size());
        Ride lastRide = model.getFilteredRideList().get(indexLastRide.getZeroBased());

        RideBuilder rideInList = new RideBuilder(lastRide);
        Ride editedRide = rideInList.withName(VALID_NAME_BOB).withMaintenance(VALID_MAINTENANCE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB)
                .withMaintenance(VALID_MAINTENANCE_BOB).withTags(VALID_TAG_HUSBAND).build();
        UpdateCommand editCommand = new UpdateCommand(indexLastRide, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getThanePark()), new UserPrefs());
        expectedModel.updateRide(lastRide, editedRide);
        expectedModel.commitThanePark();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_RIDE, new UpdateRideDescriptor());
        Ride editedRide = model.getFilteredRideList().get(INDEX_FIRST_RIDE.getZeroBased());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getThanePark()), new UserPrefs());
        expectedModel.commitThanePark();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRideAtIndex(model, INDEX_FIRST_RIDE);

        Ride rideInFilteredList = model.getFilteredRideList().get(INDEX_FIRST_RIDE.getZeroBased());
        Ride editedRide = new RideBuilder(rideInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_RIDE,
                new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getThanePark()), new UserPrefs());
        expectedModel.updateRide(model.getFilteredRideList().get(0), editedRide);
        expectedModel.commitThanePark();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRideUnfilteredList_failure() {
        Ride firstRide = model.getFilteredRideList().get(INDEX_FIRST_RIDE.getZeroBased());
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder(firstRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_SECOND_RIDE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, UpdateCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_duplicateRideFilteredList_failure() {
        showRideAtIndex(model, INDEX_FIRST_RIDE);

        // edit ride in filtered list into a duplicate in thanepark book
        Ride rideInList = model.getThanePark().getRideList().get(INDEX_SECOND_RIDE.getZeroBased());
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_RIDE,
                new UpdateRideDescriptorBuilder(rideInList).build());

        assertCommandFailure(editCommand, model, commandHistory, UpdateCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_invalidRideIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of thanepark book
     */
    @Test
    public void execute_invalidRideIndexFilteredList_failure() {
        showRideAtIndex(model, INDEX_FIRST_RIDE);
        Index outOfBoundIndex = INDEX_SECOND_RIDE;
        // ensures that outOfBoundIndex is still in bounds of thanepark book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThanePark().getRideList().size());

        UpdateCommand editCommand = new UpdateCommand(outOfBoundIndex,
                new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Ride editedRide = new RideBuilder().build();
        Ride rideToEdit = model.getFilteredRideList().get(INDEX_FIRST_RIDE.getZeroBased());
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder(editedRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_RIDE, descriptor);
        Model expectedModel = new ModelManager(new ThanePark(model.getThanePark()), new UserPrefs());
        expectedModel.updateRide(rideToEdit, editedRide);
        expectedModel.commitThanePark();

        // edit -> first ride edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts thanepark back to previous state and filtered ride list to showWithFilePath all rides
        expectedModel.undoThanePark();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first ride edited again
        expectedModel.redoThanePark();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        // execution failed -> thanepark book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        // single thanepark book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Ride} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited ride in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the ride object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRideEdited() throws Exception {
        Ride editedRide = new RideBuilder().buildDifferent();
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder(editedRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_RIDE, descriptor);
        Model expectedModel = new ModelManager(new ThanePark(model.getThanePark()), new UserPrefs());

        showRideAtIndex(model, INDEX_THIRD_RIDE);
        Ride rideToEdit = model.getFilteredRideList().get(INDEX_FIRST_RIDE.getZeroBased());
        expectedModel.updateRide(rideToEdit, editedRide);
        expectedModel.commitThanePark();

        // edit -> edits second ride in unfiltered ride list / first ride in filtered ride list
        editCommand.execute(model, commandHistory);

        // undo -> reverts thanepark back to previous state and filtered ride list to showWithFilePath all rides
        expectedModel.undoThanePark();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredRideList().get(INDEX_FIRST_RIDE.getZeroBased()), rideToEdit);
        // redo -> edits same second ride in unfiltered ride list
        expectedModel.redoThanePark();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(INDEX_FIRST_RIDE, DESC_AMY);

        // same values -> returns true
        UpdateRideDescriptor copyDescriptor = new UpdateCommand.UpdateRideDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues = new UpdateCommand(INDEX_FIRST_RIDE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_SECOND_RIDE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_FIRST_RIDE, DESC_BOB)));
    }

}
