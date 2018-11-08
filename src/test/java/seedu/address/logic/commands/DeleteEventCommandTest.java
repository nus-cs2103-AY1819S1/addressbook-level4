package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtDateAndIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateOrIndexIndexUnfilteredList_throwsCommandException() {

        // checks for both inputs to DeleteEventCommand

        EventDate outOfBoundDate = new EventDate("2019-12-31");
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundDate, INDEX_FIRST_EVENT);

        assertCommandFailure(deleteEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);

        // meeting is top of event list by date (latest date first)
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventListByDate().get(0).size() + 1);
        deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDateAndIndexFilteredList_success() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        Event eventToDelete = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateFilteredList_throwsCommandException() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        EventDate outOfBoundDate = new EventDate(VALID_EVENT_DATE_DOCTORAPPT);
        // ensures that outOfBoundDate is still in bounds of address book list (unfiltered)
        assertTrue(model.getAddressBook().getEventList().stream()
                .anyMatch(event -> event.getEventDate().equals(outOfBoundDate)));

        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundDate, INDEX_FIRST_EVENT);

        assertCommandFailure(deleteEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validDateAndIndexUnfilteredList_success() throws Exception {
        // eventToDelete = MEETING
        Event eventToDelete = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();

        // delete -> first event deleted
        deleteEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered event list (by date) to show all events
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first event deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidDateUnfilteredList_failure() {
        EventDate outOfBoundDate = new EventDate("2019-12-31");
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundDate, INDEX_FIRST_EVENT);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteEventCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes an {@code Event} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the same event object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEventDeleted() throws Exception {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showEventAtDateAndIndex(model, INDEX_SECOND_EVENT, new EventDate(VALID_EVENT_DATE_MEETING)); // DINNER event
        Event eventToDelete = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second event in unfiltered event list / first event in filtered event list
        deleteEventCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all events
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(eventToDelete, model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased()));

        // redo -> deletes same second event in unfiltered event list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand =
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT);
        DeleteEventCommand deleteDifferentDateCommand =
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_MEETING), INDEX_FIRST_EVENT);
        DeleteEventCommand deleteDifferentIndexCommand =
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy =
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event date -> returns false
        assertFalse(deleteFirstCommand.equals(deleteDifferentDateCommand));

        // different event index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteDifferentIndexCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
