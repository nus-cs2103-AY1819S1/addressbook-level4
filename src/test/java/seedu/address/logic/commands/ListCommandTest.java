package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.J_TASK;
import static seedu.address.testutil.TypicalTasks.K_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.DueDateIsBeforeTodayPredicate;
import seedu.address.testutil.TaskManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listFiltered_beforeToday() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        ListCommand.ListFilter filter = ListCommand.ListFilter.DUE_TODAY;
        ListCommand command = new ListCommand(filter);

        TaskManager manager = (new TaskManagerBuilder())
                .withTask(J_TASK)
                .withTask(K_TASK)
                .build();
        // Build new ModelManagers to handle temporal differences
        ModelManager modelWithExtremeTemporalTasks =
                new ModelManager(manager, new UserPrefs());
        ModelManager expectedModelWithPastTask =
                new ModelManager(modelWithExtremeTemporalTasks.getTaskManager(), new UserPrefs());
        expectedModelWithPastTask.updateFilteredTaskList(new DueDateIsBeforeTodayPredicate());

        assertCommandSuccess(command, modelWithExtremeTemporalTasks, commandHistory,
                expectedMessage, expectedModelWithPastTask);
        assertEquals(Arrays.asList(J_TASK), modelWithExtremeTemporalTasks.getFilteredTaskList());
    }
}
