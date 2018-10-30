package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.address.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ThanePark;
import seedu.address.model.UserPrefs;
import seedu.address.model.ride.Ride;
import seedu.address.testutil.RideBuilder;
import seedu.address.testutil.UpdateRideDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * OpenCommand.
 */
public class OpenCommandTest {

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON, new UpdateRideDescriptor());
        Ride editedRide = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Ride rideInFilteredList = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        Ride editedRide = new RideBuilder(rideInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredRideList().get(0), editedRide);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRideUnfilteredList_failure() {
        OpenCommand openCommand = new OpenCommand(INDEX_SECOND_RIDE);

        assertCommandFailure(openCommand, model, commandHistory, OpenCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_duplicateRideFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit ride in filtered list into a duplicate in address book
        OpenCommand openCommand = new OpenCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(openCommand, model, commandHistory, OpenCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_invalidRideIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        OpenCommand openCommand = new OpenCommand(outOfBoundIndex);

        assertCommandFailure(openCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    /**
     * open ride where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidRideIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_RIDE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRideList().size());

        OpenCommand openCommand = new OpenCommand(outOfBoundIndex);

        assertCommandFailure(openCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        OpenCommand openCommand = new OpenCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(openCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final OpenCommand standardCommand = new OpenCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        OpenCommand commandWithSameValues = new OpenCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new OpenCommand(INDEX_SECOND_RIDE)));
    }
}
