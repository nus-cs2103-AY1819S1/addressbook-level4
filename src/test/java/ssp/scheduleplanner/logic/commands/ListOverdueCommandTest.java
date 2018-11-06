package ssp.scheduleplanner.logic.commands;

import org.junit.Test;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;
import ssp.scheduleplanner.model.task.OverduePredicate;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.testutil.TaskBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

public class ListOverdueCommandTest {

    @Test
    public void execute_success() {
        CommandHistory commandHistory = new CommandHistory();
        Task validOverdueTask = (new TaskBuilder()).withDate("101010").build();
        int SYSTEM_DATE =
                    Integer.parseInt(new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime()));
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        model.addTask(validOverdueTask);
        expectedModel.addTask(validOverdueTask);
        expectedModel.updateFilteredTaskList(new OverduePredicate(SYSTEM_DATE));
        System.out.println(expectedModel.getFilteredTaskList());
        System.out.println(model.getFilteredTaskList());
        assertCommandSuccess(new ListOverdueCommand(), model, commandHistory, ListOverdueCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void task_remain_afterFilter() {
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Model expectedModel = new ModelManager();
        Task validOverdueTask = (new TaskBuilder().withDate("010101")).build();
        model.addTask(validOverdueTask);
        model.updateFilteredTaskList(new OverduePredicate(030101));
        expectedModel.addTask(validOverdueTask);

        assertTrue(model.hasTask(validOverdueTask));
        assertTrue(model.getFilteredTaskList().contains(validOverdueTask));
        assertEquals(model.getFilteredTaskList(), expectedModel.getFilteredTaskList());
    }

    @Test
    public void task_gone_afterFilter() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        Task validOverdueTask = new TaskBuilder().withDate("010101").build();
        Task validNotOverdue = new TaskBuilder().withDate("121212").build();

        model.addTask(validOverdueTask);
        model.addTask(validNotOverdue);
        expectedModel.addTask(validOverdueTask);
        model.updateFilteredTaskList(new OverduePredicate(011212));

        assertFalse(model.getFilteredTaskList().equals(expectedModel.getFilteredTaskList()));
        assertFalse(model.getFilteredTaskList().contains(validNotOverdue));
    }






}
