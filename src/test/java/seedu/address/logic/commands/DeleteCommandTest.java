package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VOLUNTEER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VOLUNTEER;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validVolunteerIndexUnfilteredList_success() {
        Volunteer volunteerToDelete = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_VOLUNTEER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_VOLUNTEER_SUCCESS,
                volunteerToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVolunteer(volunteerToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidVolunteerIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validVolunteerIndexFilteredList_success() {
        showVolunteerAtIndex(model, INDEX_FIRST_VOLUNTEER);

        Volunteer volunteerToDelete = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_VOLUNTEER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_VOLUNTEER_SUCCESS,
                volunteerToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVolunteer(volunteerToDelete);
        expectedModel.commitAddressBook();
        showNoVolunteer(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidVolunteerIndexFilteredList_throwsCommandException() {
        showVolunteerAtIndex(model, INDEX_FIRST_VOLUNTEER);

        Index outOfBoundIndex = INDEX_SECOND_VOLUNTEER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVolunteerList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validVolunteerIndexUnfilteredList_success() throws Exception {
        Volunteer volunteerToDelete = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_VOLUNTEER);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVolunteer(volunteerToDelete);
        expectedModel.commitAddressBook();

        // delete -> first volunteer deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered volunteer list to show all volunteers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first volunteer deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidVolunteerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Volunteer} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted volunteer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the volunteer object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validVolunteerIndexFilteredList_sameVolunteerDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_VOLUNTEER);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showVolunteerAtIndex(model, INDEX_SECOND_VOLUNTEER);
        Volunteer volunteerToDelete = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        expectedModel.deleteVolunteer(volunteerToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second volunteer in unfiltered volunteer list / first volunteer in filtered volunteer list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered volunteer list to show all volunteers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(volunteerToDelete, model.getFilteredVolunteerList()
                .get(INDEX_FIRST_VOLUNTEER.getZeroBased()));
        // redo -> deletes same second volunteer in unfiltered volunteer list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_VOLUNTEER);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_VOLUNTEER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_VOLUNTEER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoVolunteer(Model model) {
        model.updateFilteredVolunteerList(p -> false);

        assertTrue(model.getFilteredVolunteerList().isEmpty());
    }
}
