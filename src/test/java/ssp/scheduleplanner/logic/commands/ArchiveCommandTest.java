package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandFailure;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.showTaskAtIndex;
import static ssp.scheduleplanner.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static ssp.scheduleplanner.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import org.junit.Test;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.commons.core.index.Index;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;
import ssp.scheduleplanner.model.task.Task;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToArchive = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_TASK_SUCCESS, taskToArchive);

        ModelManager expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        expectedModel.archiveTask(taskToArchive);
        expectedModel.commitSchedulePlanner();

        assertCommandSuccess(archiveCommand, model, commandHistory, expectedMessage, expectedModel);
        //Check whether expected model and actual model has archived task in archived task list.
        assertTrue(expectedModel.hasArchivedTask(taskToArchive));
        assertTrue(model.hasArchivedTask(taskToArchive));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToArchive = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_TASK_SUCCESS, taskToArchive);

        Model expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        expectedModel.archiveTask(taskToArchive);
        expectedModel.commitSchedulePlanner();
        showNoTask(expectedModel);

        assertCommandSuccess(archiveCommand, model, commandHistory, expectedMessage, expectedModel);
        //Check whether expected model and actual model has archived task in archived task list.
        assertTrue(expectedModel.hasArchivedTask(taskToArchive));
        assertTrue(model.hasArchivedTask(taskToArchive));
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSchedulePlanner().getTaskList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Task taskToArchive = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        expectedModel.archiveTask(taskToArchive);
        expectedModel.commitSchedulePlanner();

        // archive -> first task archived
        archiveCommand.execute(model, commandHistory);

        //Check whether expected model and actual model has archived task in archived task list.
        assertTrue(expectedModel.hasArchivedTask(taskToArchive));
        assertTrue(model.hasArchivedTask(taskToArchive));

        // undo -> reverts addressbook back to previous state and filtered task list to show all tasks
        expectedModel.undoSchedulePlanner();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //Check whether expected model and actual model has archived task in archived task list.
        assertFalse(expectedModel.hasArchivedTask(taskToArchive));
        assertFalse(model.hasArchivedTask(taskToArchive));

        // redo -> same first task archived again
        expectedModel.redoSchedulePlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        //Check whether expected model and actual model has archived task in archived task list.
        assertTrue(expectedModel.hasArchivedTask(taskToArchive));
        assertTrue(model.hasArchivedTask(taskToArchive));
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(archiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Archives a {@code Task} from a filtered list.
     * 2. Undo the archive.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously archived task in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the archive. This ensures {@code RedoCommand} archives the task object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTaskArchived() throws Exception {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());

        showTaskAtIndex(model, INDEX_SECOND_TASK);
        Task taskToArchive = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        expectedModel.archiveTask(taskToArchive);
        expectedModel.commitSchedulePlanner();

        // archive -> archives second task in unfiltered task list / first task in filtered task list
        archiveCommand.execute(model, commandHistory);

        // undo -> reverts scheduleplanner back to previous state and filtered task list to show all tasks
        expectedModel.undoSchedulePlanner();

        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(taskToArchive, model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        // redo -> archives same second task in unfiltered task list
        expectedModel.redoSchedulePlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeRepeatitive_validIndexUnfilteredList_success() {
        Task taskToArchive = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_TASK_SUCCESS, taskToArchive);

        ModelManager expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        expectedModel.archiveTask(taskToArchive);
        expectedModel.commitSchedulePlanner();

        assertCommandSuccess(archiveCommand, model, commandHistory, expectedMessage, expectedModel);
        expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        //Add back the archived task
        expectedModel.addTask(taskToArchive);
        model.undoSchedulePlanner();
        assertEquals(expectedModel.getFilteredTaskList(), model.getFilteredTaskList());
        //Archive feature should not fail even if two same tasks are archived.
        expectedModel.archiveTask(taskToArchive);
        assertNotEquals(expectedModel.getFilteredArchivedTaskList(), model.getFilteredArchivedTaskList());

    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_TASK);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_TASK);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
