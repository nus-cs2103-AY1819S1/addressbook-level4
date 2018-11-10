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



public class DependenciesCommandTest {
    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        //Adding a task dependency
        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task newTask = DependencyCommand.createDependantTask(dependantTask, dependeeTask);
        String expectedMessageAdd = String.format(DependencyCommand.MESSAGE_ADD_SUCCESS, newTask.getName(),
                dependeeTask.getName());
        ModelManager expectedModelAdd = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModelAdd.updateTask(dependantTask, newTask);
        expectedModelAdd.commitTaskManager();
        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessageAdd, expectedModelAdd);
        //Remove a task dependency
        ModelManager expectedModelRemove = expectedModelAdd;
        expectedModelRemove.updateTask(newTask, dependantTask);
        expectedModelRemove.commitTaskManager();
        String expectedMessageRemove = String.format(DependencyCommand.MESSAGE_REMOVE_SUCCESS, newTask.getName(),
                dependeeTask.getName());
        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessageRemove, expectedModelRemove);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DependencyCommand dependencyCommand = new DependencyCommand(outOfBoundIndex, outOfBoundIndex);
        assertCommandFailure(dependencyCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_containsCyclicDependencyUnfilteredList_throwsCommandException() {
        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        Task newDependeeTask = DependencyCommand.createDependantTask(dependeeTask, dependantTask);

        model.updateTask(dependeeTask, newDependeeTask);

        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        assertCommandFailure(dependencyCommand, model, commandHistory, DependencyCommand.MESSAGE_CYCLIC_DEPENDENCY);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtTwoIndexes(model, INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        Task dependantTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        //Add task dependency
        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task newTask = DependencyCommand.createDependantTask(dependantTask, dependeeTask);
        String expectedMessage = String.format(DependencyCommand.MESSAGE_ADD_SUCCESS, newTask.getName(),
                dependeeTask.getName());
        ModelManager expectedModelAdd = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModelAdd.updateTask(dependantTask, newTask);
        expectedModelAdd.commitTaskManager();

        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessage, expectedModelAdd);

        //Remove a task dependency
        ModelManager expectedModelRemove = expectedModelAdd;
        expectedModelRemove.updateTask(newTask, dependantTask);
        expectedModelRemove.commitTaskManager();
        String expectedMessageRemove = String.format(DependencyCommand.MESSAGE_REMOVE_SUCCESS, newTask.getName(),
                dependeeTask.getName());
        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessageRemove, expectedModelRemove);
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

        ModelManager expectedModelAdd = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModelAdd.updateTask(dependantTask, newTask);
        expectedModelAdd.commitTaskManager();
        // add dependency
        dependencyCommand.executePrimitive(model, commandHistory);
        // undo -> reverts task manager back to previous state and filtered task list to show all
        // tasks
        expectedModelAdd.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModelAdd);
        // redo -> same first task added
        expectedModelAdd.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModelAdd);

        //Remove a task dependency
        ModelManager expectedModelRemove = expectedModelAdd;
        expectedModelRemove.updateTask(newTask, dependantTask);
        expectedModelRemove.commitTaskManager();
        String expectedMessageRemove = String.format(DependencyCommand.MESSAGE_REMOVE_SUCCESS, newTask.getName(),
                dependeeTask.getName());
        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessageRemove, expectedModelRemove);

        // undo -> reverts task manager back to previous state and filtered task list to show all
        // tasks
        expectedModelRemove.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
                expectedModelRemove);
        // redo -> same first task dependency deleted again
        expectedModelAdd.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
                expectedModelRemove);
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
        dependencyCommand.executePrimitive(model, commandHistory);
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
    public void equals() {
        DependencyCommand firstCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        DependencyCommand anotherFirstCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        assertEquals(firstCommand, anotherFirstCommand);

        DependencyCommand secondCommand = new DependencyCommand(INDEX_SECOND_TASK, INDEX_FIRST_TASK);
        DependencyCommand thirdCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_THIRD_TASK);
        assertNotEquals(firstCommand, secondCommand);
        assertNotEquals(firstCommand, thirdCommand);

    }
}
