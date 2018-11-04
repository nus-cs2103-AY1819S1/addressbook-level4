package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;
import ssp.scheduleplanner.model.task.DateWeekSamePredicate;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListWeekCommand.
 */
public class ListWeekCommandTest {

    @Test
    public void execute_success() {
        ListWeekCommand lwc = new ListWeekCommand();
        CommandHistory commandHistory = new CommandHistory();
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Calendar c = Calendar.getInstance();
        List<String> dateList = new ArrayList<String>();

        String todayDate = LocalDate.now().getDayOfWeek().name();
        int numDaysTillSunday = lwc.numDaysTillSunday(todayDate);

        dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));

        //check how many days from current date until closest Sunday, generate corresponding tasks for each day
        //add each task to both model and expected model
        //each date from current date until closest Sunday is added to dateList as predicate
        for (int i = 0; i < numDaysTillSunday; i++) {
            Task validToday = new TaskBuilder().withDate(new SimpleDateFormat("ddMMyy")
                    .format(c.getTime())).build();
            c.add(Calendar.DATE, 1);
            dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
            model.addTask(validToday);
            expectedModel.addTask(validToday);
        }

        //update expectedModel by using the dateList as predicate
        expectedModel.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        assertCommandSuccess(new ListWeekCommand(), model, commandHistory, ListWeekCommand.MESSAGE_SUCCESS,
                expectedModel);

    }

    @Test
    public void appendDateList_test() {
        ListWeekCommand lwc = new ListWeekCommand();
        List<String> dateList = new ArrayList<String>();
        List<String> expectedDateList = new ArrayList<String>();

        Calendar c = Calendar.getInstance();

        //expectedDateList contains 7 values as we are appending 6 days, excluding current date
        expectedDateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
        for (int i = 0; i < 6; i++) {
            c.add(Calendar.DATE, 1);
            expectedDateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
        }

        lwc.appendDateList(dateList, 6);

        assertEquals(dateList, expectedDateList);
        assertTrue(dateList.containsAll(expectedDateList));
        assertTrue(expectedDateList.containsAll(dateList));
    }

    @Test
    public void numDaysTillSunday_test() {
        ListWeekCommand lwc = new ListWeekCommand();
        String testMonday = LocalDate.of(2018, 10, 22).getDayOfWeek().name();
        String testTuesday = LocalDate.of(2018, 10, 23).getDayOfWeek().name();
        String testWednesday = LocalDate.of(2018, 10, 24).getDayOfWeek().name();
        String testThursday = LocalDate.of(2018, 10, 25).getDayOfWeek().name();
        String testFriday = LocalDate.of(2018, 10, 26).getDayOfWeek().name();
        String testSaturday = LocalDate.of(2018, 10, 27).getDayOfWeek().name();
        String testSunday = LocalDate.of(2018, 10, 28).getDayOfWeek().name();

        assertEquals(lwc.numDaysTillSunday(testMonday), 6);
        assertEquals(lwc.numDaysTillSunday(testTuesday), 5);
        assertEquals(lwc.numDaysTillSunday(testWednesday), 4);
        assertEquals(lwc.numDaysTillSunday(testThursday), 3);
        assertEquals(lwc.numDaysTillSunday(testFriday), 2);
        assertEquals(lwc.numDaysTillSunday(testSaturday), 1);
        assertEquals(lwc.numDaysTillSunday(testSunday), 0);
    }

    @Test
    public void task_remain_afterFilter() {
        //after update the filteredtasklist with a specific date predicate, model would have that task(s) from that date
        //till the closest sunday.
        //131018 is a saturday -> task from 131018 and 141018 will remain.
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Model modelCheck = new ModelManager();
        Task validTaskSat = new TaskBuilder().withDate("201018").build();
        Task validTaskSun = new TaskBuilder().withDate("211018").build();

        List<String> dateList = new ArrayList<String>();
        dateList.add("201018");
        dateList.add("211018");

        model.addTask(validTaskSat);
        model.addTask(validTaskSun);
        modelCheck.addTask(validTaskSat);
        modelCheck.addTask(validTaskSun);

        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        assertTrue(model.getFilteredTaskList().equals(modelCheck.getFilteredTaskList()));
        assertTrue(model.getFilteredTaskList().contains(validTaskSat));
        assertTrue(model.getFilteredTaskList().contains(validTaskSun));
        assertEquals(model.getFilteredTaskList(), modelCheck.getFilteredTaskList());
    }

    @Test
    public void task_gone_afterFilter() {
        //after update the filteredtasklist with a specific date predicate, model would have that task(s) from that date
        //till the closest sunday and not other days.
        //131018 is a saturday -> task from 131018 and 141018 will remain
        //131018 is a saturday -> task from 081018(monday) till 121018(friday) will not remain
        Model model = new ModelManager();
        Model modelCheck = new ModelManager();
        Task validTaskMon = new TaskBuilder().withDate("081018").build();
        Task validTaskTue = new TaskBuilder().withDate("091018").build();
        Task validTaskWed = new TaskBuilder().withDate("101018").build();
        Task validTaskThu = new TaskBuilder().withDate("111018").build();
        Task validTaskFri = new TaskBuilder().withDate("121018").build();
        Task validTaskSat = new TaskBuilder().withDate("131018").build();
        Task validTaskSun = new TaskBuilder().withDate("141018").build();

        model.addTask(validTaskMon);
        model.addTask(validTaskTue);
        model.addTask(validTaskWed);
        model.addTask(validTaskThu);
        model.addTask(validTaskFri);
        model.addTask(validTaskSat);
        model.addTask(validTaskSun);
        modelCheck.addTask(validTaskSat);
        modelCheck.addTask(validTaskSun);

        List<String> dateList = new ArrayList<String>();
        dateList.add("131018");
        dateList.add("141018");
        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        assertFalse(model.getFilteredTaskList().contains(validTaskMon));
        assertFalse(model.getFilteredTaskList().contains(validTaskTue));
        assertFalse(model.getFilteredTaskList().contains(validTaskWed));
        assertFalse(model.getFilteredTaskList().contains(validTaskThu));
        assertFalse(model.getFilteredTaskList().contains(validTaskFri));

        assertTrue(model.getFilteredTaskList().contains(validTaskSat));
        assertTrue(model.getFilteredTaskList().contains(validTaskSun));
        assertEquals(model.getFilteredTaskList(), modelCheck.getFilteredTaskList());
    }
}
