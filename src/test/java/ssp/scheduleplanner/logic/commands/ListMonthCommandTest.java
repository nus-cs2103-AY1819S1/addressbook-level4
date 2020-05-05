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
public class ListMonthCommandTest {

    @Test
    public void execute_success() {
        ListMonthCommand lmc = new ListMonthCommand();
        CommandHistory commandHistory = new CommandHistory();
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Calendar c = Calendar.getInstance();
        List<String> dateList = new ArrayList<String>();

        LocalDate currentDate = LocalDate.now();
        int numDaysTillEndOfMonth = lmc.numDaysTillEndOfMonth(currentDate);

        dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));

        //check how many days from current date until end of month, generate corresponding tasks for each day
        //add each task to both model and expected model
        //each date from current date until last day of month is added to dateList as predicate
        for (int i = 0; i < numDaysTillEndOfMonth; i++) {
            Task validToday = new TaskBuilder().withDate(new SimpleDateFormat("ddMMyy")
                    .format(c.getTime())).build();
            // increment day by 1
            c.add(Calendar.DATE, 1);
            // add next day into dateList
            dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
            model.addTask(validToday);
            expectedModel.addTask(validToday);
        }

        //update expectedModel by using the dateList as predicate
        expectedModel.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        assertCommandSuccess(new ListMonthCommand(), model, commandHistory, ListMonthCommand.MESSAGE_SUCCESS,
                expectedModel);

    }

    @Test
    public void appendDateList_test_success() {
        ListMonthCommand lmc = new ListMonthCommand();
        List<String> dateList = new ArrayList<String>();
        List<String> expectedDateList = new ArrayList<String>();

        Calendar c = Calendar.getInstance();

        //expectedDateList contains 30 values as we are appending 30 days, excluding current date
        expectedDateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
        for (int i = 0; i < 30; i++) {
            c.add(Calendar.DATE, 1);
            expectedDateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
        }

        lmc.appendDateList(dateList, 30);

        assertEquals(dateList, expectedDateList);
        assertTrue(dateList.containsAll(expectedDateList));
        assertTrue(expectedDateList.containsAll(dateList));
    }

    @Test
    public void numDaysTillEndOfMonth_test_success() {
        ListMonthCommand lmc = new ListMonthCommand();
        LocalDate testDay1 = LocalDate.of(2018, 10, 1);
        LocalDate testDay6 = LocalDate.of(2018, 10, 6);
        LocalDate testDay11 = LocalDate.of(2018, 10, 11);
        LocalDate testDay16 = LocalDate.of(2018, 10, 16);
        LocalDate testDay21 = LocalDate.of(2018, 10, 21);
        LocalDate testDay26 = LocalDate.of(2018, 10, 26);
        LocalDate testDay31 = LocalDate.of(2018, 10, 31);
        LocalDate testDefault = LocalDate.of(2018, 10, 1);

        assertEquals(lmc.numDaysTillEndOfMonth(testDay1), 30);
        assertEquals(lmc.numDaysTillEndOfMonth(testDay6), 25);
        assertEquals(lmc.numDaysTillEndOfMonth(testDay11), 20);
        assertEquals(lmc.numDaysTillEndOfMonth(testDay16), 15);
        assertEquals(lmc.numDaysTillEndOfMonth(testDay21), 10);
        assertEquals(lmc.numDaysTillEndOfMonth(testDay26), 5);
        assertEquals(lmc.numDaysTillEndOfMonth(testDay31), 0);
        assertEquals(lmc.numDaysTillEndOfMonth(testDefault), 30);
    }

    @Test
    public void dateListSamePredicate_tasksRemainAfterFilter_success() {
        // update FilteredTaskList with a specific date predicate, model would have that task(s) from that date
        //till the closest sunday.
        //131018 is a saturday -> task from 131018 and 141018 will remain.
        Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        Model modelCheck = new ModelManager();
        Task validTaskDay1 = new TaskBuilder().withDate("011018").build();
        Task validTaskDay30 = new TaskBuilder().withDate("301018").build();

        List<String> dateList = new ArrayList<String>();
        dateList.add("011018");
        dateList.add("301018");

        model.addTask(validTaskDay1);
        model.addTask(validTaskDay30);
        modelCheck.addTask(validTaskDay1);
        modelCheck.addTask(validTaskDay30);

        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        assertTrue(model.getFilteredTaskList().equals(modelCheck.getFilteredTaskList()));
        assertTrue(model.getFilteredTaskList().contains(validTaskDay1));
        assertTrue(model.getFilteredTaskList().contains(validTaskDay30));
        assertEquals(model.getFilteredTaskList(), modelCheck.getFilteredTaskList());
    }

    @Test
    public void dateListSamePredicate_correctTasksRemainAfterFilter_success() {
        // update the FilteredTaskList with a predicate, that checks for tasks whose dates match those in the list,
        // updated list will contain task(s) that are on the specified dates.
        // Example:  dates 261018 - 31101 in predicate -> only 261018 and 311018 will be displayed
        //
        Model model = new ModelManager();
        Model modelCheck = new ModelManager();
        Task validTaskDay1 = new TaskBuilder().withDate("011018").build();
        Task validTaskDay6 = new TaskBuilder().withDate("061018").build();
        Task validTaskDay11 = new TaskBuilder().withDate("111018").build();
        Task validTaskDay16 = new TaskBuilder().withDate("161018").build();
        Task validTaskDay21 = new TaskBuilder().withDate("211018").build();
        Task validTaskDay26 = new TaskBuilder().withDate("261018").build();
        Task validTaskDay31 = new TaskBuilder().withDate("311018").build();

        model.addTask(validTaskDay1);
        model.addTask(validTaskDay6);
        model.addTask(validTaskDay11);
        model.addTask(validTaskDay16);
        model.addTask(validTaskDay21);
        model.addTask(validTaskDay26);
        model.addTask(validTaskDay31);
        modelCheck.addTask(validTaskDay26);
        modelCheck.addTask(validTaskDay31);

        List<String> dateList = new ArrayList<String>();
        dateList.add("261018");
        dateList.add("271018");
        dateList.add("281018");
        dateList.add("291018");
        dateList.add("301018");
        dateList.add("311018");
        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        assertFalse(model.getFilteredTaskList().contains(validTaskDay1));
        assertFalse(model.getFilteredTaskList().contains(validTaskDay6));
        assertFalse(model.getFilteredTaskList().contains(validTaskDay11));
        assertFalse(model.getFilteredTaskList().contains(validTaskDay16));
        assertFalse(model.getFilteredTaskList().contains(validTaskDay21));

        assertTrue(model.getFilteredTaskList().contains(validTaskDay26));
        assertTrue(model.getFilteredTaskList().contains(validTaskDay31));
        assertEquals(model.getFilteredTaskList(), modelCheck.getFilteredTaskList());
    }
}
