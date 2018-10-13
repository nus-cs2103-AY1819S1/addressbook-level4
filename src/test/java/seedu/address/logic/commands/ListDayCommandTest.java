package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.AMY;
import static seedu.address.testutil.TypicalTasks.getTypicalSchedulePlanner;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.DateSamePredicate;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDayCommand.
 */
public class ListDayCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    //private Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());

    @Test
    public void task_remain_afterFilter() {
        //after update the filteredtasklist with a specific date predicate, model would have that task remaining
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Task validTask = new TaskBuilder().withDate("111111").build();
        model.addTask(validTask);
        model.updateFilteredTaskList(new DateSamePredicate(validTask.getDate().value));
        assertTrue(model.hasTask(validTask));
    }

    @Test
    public void task_gone_afterFilter() {
        //after update the filteredtasklist with a specific date predicate, model would not have other task remaining
        Model model = new ModelManager();
        Model modelCheck = new ModelManager();
        Task validTask = new TaskBuilder().withDate("111111").build();
        Task validTaskCheck = new TaskBuilder().withDate("121212").build();
        model.addTask(validTask);
        model.addTask(validTaskCheck);
        modelCheck.addTask(validTaskCheck);
        model.updateFilteredTaskList(new DateSamePredicate(validTask.getDate().value));
        assertFalse(model.getFilteredTaskList() == modelCheck.getFilteredTaskList());
    }
}
