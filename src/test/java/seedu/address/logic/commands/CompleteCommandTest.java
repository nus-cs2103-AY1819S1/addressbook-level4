package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_COMPLETED_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CompleteCommand}.
 */
public class CompleteCommandTest {

    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        CompleteCommand completeCommand = new CompleteCommand(INDEX_FIRST_TASK);

        Task completedTask = simpleCompleteTask(taskToComplete);
        String expectedMessage = String.format(CompleteCommand.MESSAGE_SUCCESS, completedTask);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(taskToComplete, completedTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(completeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex);

        assertCommandFailure(completeCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        CompleteCommand completeCommand = new CompleteCommand(INDEX_FIRST_TASK);

        Task completedTask = simpleCompleteTask(taskToComplete);
        String expectedMessage = String.format(CompleteCommand.MESSAGE_SUCCESS, completedTask);

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(taskToComplete, completedTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(completeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task manager list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskManager().getTaskList().size());

        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex);

        assertCommandFailure(completeCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        CompleteCommand completeCommand = new CompleteCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(taskToComplete, simpleCompleteTask(taskToComplete));
        expectedModel.commitTaskManager();

        // complete -> first task completed
        completeCommand.execute(model, commandHistory);

        // undo -> reverts task manager back to previous state and filtered task list to show all
        // tasks
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex);

        // execution failed -> task manager state not added into model
        assertCommandFailure(completeCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single task manager state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Task} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously
     * deleted task in the unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the task object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTaskCompleted() throws Exception {
        CompleteCommand completeCommand = new CompleteCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());

        showTaskAtIndex(model, INDEX_SECOND_TASK);
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        expectedModel.updateTask(taskToComplete, simpleCompleteTask(taskToComplete));
        expectedModel.commitTaskManager();

        // completes -> completes second task in unfiltered task list / first task in filtered task
        // list
        completeCommand.execute(model, commandHistory);

        // undo -> reverts task manager back to previous state and filtered task list to show all tasks
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(taskToComplete, model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        // redo -> deletes same second task in unfiltered task list
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_alreadyCompletedTask_throwsCommandException() {
        CompleteCommand completeCommand = new CompleteCommand(INDEX_COMPLETED_TASK);

        assertCommandFailure(completeCommand, model, commandHistory, CompleteCommand.MESSAGE_ALREADY_COMPLETED);
    }

    @Test
    public void equals() {
        CompleteCommand completeFirstCommand = new CompleteCommand(INDEX_FIRST_TASK);
        CompleteCommand completeSecondCommand = new CompleteCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteCommand completeFirstCommandCopy = new CompleteCommand(INDEX_FIRST_TASK);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

    /**
     * Creates a new completed task from given task.
     *
     * @param taskToComplete taskToComplete will be treated as the immutable task to copy attributes from.
     * @return A new completed version of taskToComplete.
     */
    private Task simpleCompleteTask(Task taskToComplete) {
        return new Task(// returns a completed task.
                taskToComplete.getName(),
                taskToComplete.getDueDate(),
                taskToComplete.getPriorityValue(),
                taskToComplete.getDescription(),
                taskToComplete.getLabels(),
                Status.COMPLETED,
                taskToComplete.getDependency()
        );
    }

}
