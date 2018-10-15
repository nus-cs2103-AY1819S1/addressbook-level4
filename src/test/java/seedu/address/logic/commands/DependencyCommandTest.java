package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtTwoIndexes;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Dependency;
import seedu.address.model.task.Task;



public class DependencyCommandTest {
    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task dependeeTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task dependantTask = model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());

        DependencyCommand dependencyCommand = new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        Task newTask = DependencyCommand.createDependeeTask(dependeeTask, dependantTask);
        String expectedMessage = String.format(DependencyCommand.MESSAGE_SUCCESS, newTask);
        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateTask(dependeeTask, newTask);
        expectedModel.commitTaskManager();
        assertCommandSuccess(dependencyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DependencyCommand dependencyCommand = new DependencyCommand(outOfBoundIndex, outOfBoundIndex);
        assertCommandFailure(dependencyCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
