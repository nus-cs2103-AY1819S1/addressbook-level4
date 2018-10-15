package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtTwoIndexes;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;



public class DependencyCommandTest {
    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());

        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task newTask = DependencyCommand.createDependantTask(dependantTask, dependeeTask);
        String expectedMessage = String.format(DependencyCommand.MESSAGE_SUCCESS, newTask);
        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(dependantTask, newTask);
        expectedModel.commitTaskManager();
        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DependencyCommand dependencyCommand = new DependencyCommand(outOfBoundIndex, outOfBoundIndex);
        assertCommandFailure(dependencyCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtTwoIndexes(model, INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());

        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task newTask = DependencyCommand.createDependantTask(dependantTask, dependeeTask);
        String expectedMessage = String.format(DependencyCommand.MESSAGE_SUCCESS, newTask);
        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(dependantTask, newTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtTwoIndexes(model, INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DependencyCommand dependencyCommand = new DependencyCommand(outOfBoundIndex, outOfBoundIndex);
        assertCommandFailure(dependencyCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());

        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task newTask = DependencyCommand.createDependantTask(dependantTask, dependeeTask);

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(dependantTask, newTask);
        expectedModel.commitTaskManager();
        // add dependency
        dependencyCommand.execute(model, commandHistory);
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
        DependencyCommand dependencyCommand = new DependencyCommand(outOfBoundIndex, outOfBoundIndex);

        // execution failed -> task manager state not added into model
        assertCommandFailure(dependencyCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single task manager state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);

        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTaskDependency() throws Exception {

        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        showTaskAtTwoIndexes(model, INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());

        Task newTask = DependencyCommand.createDependantTask(dependantTask, dependeeTask);

        expectedModel.updateTask(dependantTask, newTask);
        expectedModel.commitTaskManager();
        // dependency -> dependency for second task in unfiltered task list / first task in filtered task
        // list
        dependencyCommand.execute(model, commandHistory);
        // undo -> reverts task manager back to previous state and filtered task list to show all tasks
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(dependantTask.getDependency(), model.getFilteredTaskList().get(
                INDEX_FIRST_TASK.getZeroBased()).getDependency());
        // redo -> deletes same second task in unfiltered task list=-
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DependencyCommand firstCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        DependencyCommand anotherFirstCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        assertEquals(firstCommand, anotherFirstCommand);

        DependencyCommand secondCommand = new DependencyCommand(INDEX_SECOND_TASK, INDEX_FIRST_TASK);
        DependencyCommand thirdCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_THIRD_TASK);
        assertNotEquals(firstCommand, secondCommand);
        assertNotEquals(firstCommand, thirdCommand);

    }
}
