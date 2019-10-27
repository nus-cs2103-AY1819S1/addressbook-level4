package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.DeleteEntryCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.entry.ResumeEntry;

public class DeleteEntryCommandTest {
    private Model model = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ResumeEntry entryToDelete = model.getFilteredEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete);

        ModelManager expectedModel = new ModelManager(model.getEntryBook(), new UserPrefs(), new Awareness());
        expectedModel.deleteEntry(entryToDelete);
        expectedModel.commitEntryBook();

        assertCommandSuccess(deleteEntryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEntryList().size() + 1);
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(outOfBoundIndex);

        assertCommandFailure(deleteEntryCommand, model, commandHistory, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteEntryCommand deleteFirstCommand = new DeleteEntryCommand(INDEX_FIRST_ENTRY);
        DeleteEntryCommand deleteSecondCommand = new DeleteEntryCommand(INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEntryCommand deleteFirstCommandCopy = new DeleteEntryCommand(INDEX_FIRST_ENTRY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different entry -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }


}
