package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_MEETING;
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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.testutil.ScheduledEventBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code EditEventAddressCommand}.
 */
public class EditEventAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToEdit = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new ScheduledEventBuilder(eventToEdit)
                .withEventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)
                .build();
        EventAddress updatedAddress = editedEvent.getEventAddress();
        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT, updatedAddress);

        String expectedMessage = String.format(editEventAddressCommand.MESSAGE_EDIT_EVENT_ADDRESS_SUCCESS,
                eventToEdit.getEventName(), updatedAddress);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(eventToEdit, editedEvent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editEventAddressCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateOrIndexUnfilteredList_throwsCommandException() {

        // checks for date and index inputs to EditEventAddressCommand

        EventDate outOfBoundDate = new EventDate("2019-12-31");
        EditEventAddressCommand editEventAddressCommand
                = new EditEventAddressCommand(outOfBoundDate, INDEX_FIRST_EVENT,
                new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));

        assertCommandFailure(editEventAddressCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);

        // meeting is top of event list by date (latest date first)
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventListByDate().get(0).size() + 1);
        editEventAddressCommand = new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex, new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));

        assertCommandFailure(editEventAddressCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validDateAndIndexFilteredList_success() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_DOCTORAPPT));

        Event eventToEdit = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new ScheduledEventBuilder(eventToEdit)
                .withEventAddress(VALID_EVENT_ADDRESS_MEETING)
                .build();
        EventAddress updatedAddress = editedEvent.getEventAddress();
        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT),
                INDEX_FIRST_EVENT, updatedAddress);

        String expectedMessage = String.format(EditEventAddressCommand.MESSAGE_EDIT_EVENT_ADDRESS_SUCCESS,
                eventToEdit.getEventName(), updatedAddress);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateEvent(eventToEdit, editedEvent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editEventAddressCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateFilteredList_throwsCommandException() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        EventDate outOfBoundDate = new EventDate(VALID_EVENT_DATE_DOCTORAPPT);
        // ensures that outOfBoundDate is still in bounds of address book list (unfiltered)
        assertTrue(model.getAddressBook().getEventList().stream()
                .anyMatch(event -> event.getEventDate().equals(outOfBoundDate)));

        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(outOfBoundDate, INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));

        assertCommandFailure(editEventAddressCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtDateAndIndex(model, INDEX_FIRST_EVENT, new EventDate(VALID_EVENT_DATE_MEETING));

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex, new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));

        assertCommandFailure(editEventAddressCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validDateAndIndexUnfilteredList_success() throws Exception {
        // eventToEdit = MEETING
        Event eventToEdit = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new ScheduledEventBuilder(eventToEdit)
                .withEventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)
                .build();
        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT, editedEvent.getEventAddress());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateEvent(eventToEdit, editedEvent);
        expectedModel.commitAddressBook();

        // delete -> first event deleted
        editEventAddressCommand.execute(model, commandHistory);

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
        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(outOfBoundDate, INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));

        // execution failed -> address book state not added into model
        assertCommandFailure(editEventAddressCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex, new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));

        // execution failed -> address book state not added into model
        assertCommandFailure(editEventAddressCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits an {@code Event}'s address from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} edits the address of the same event object regardless
     * of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEventDeleted() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showEventAtDateAndIndex(model, INDEX_SECOND_EVENT, new EventDate(VALID_EVENT_DATE_MEETING)); // DINNER event
        Event eventToEdit = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new ScheduledEventBuilder(eventToEdit)
                .withEventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)
                .build();

        EditEventAddressCommand editEventAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                        INDEX_FIRST_EVENT, editedEvent.getEventAddress());

        expectedModel.updateEvent(eventToEdit, editedEvent);
        expectedModel.commitAddressBook();

        // delete -> deletes second event in unfiltered event list / first event in filtered event list
        editEventAddressCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered event list to show all events
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(eventToEdit, model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased()));

        // redo -> deletes same second event in unfiltered event list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        EditEventAddressCommand editFirstCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));
        EditEventAddressCommand editDifferentDateCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_MEETING), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));
        EditEventAddressCommand editDifferentIndexCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_SECOND_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));
        EditEventAddressCommand editFDifferentAddressCommand =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_MEETING));

        // same object -> returns true
        assertTrue(editFirstCommand.equals(editFirstCommand));

        // same values -> returns true
        EditEventAddressCommand editFirstCommandCopy =
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT));
        assertTrue(editFirstCommand.equals(editFirstCommandCopy));

        // different types -> returns false
        assertFalse(editFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(editFirstCommand.equals(editDifferentDateCommand));

        // different index -> returns false
        assertFalse(editFirstCommand.equals(editDifferentIndexCommand));

        // different address -> returns false
        assertFalse(editFirstCommand.equals(editFDifferentAddressCommand));
    }

}
