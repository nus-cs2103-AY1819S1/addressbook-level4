package ssp.scheduleplanner.logic.commands;

import static java.lang.Float.NaN;
import static org.junit.Assert.assertEquals;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import org.junit.Before;
import org.junit.Test;

import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ProgressWeekCommand.
 */
public class ProgressWeekCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    }

    @Test
    public void execute_progressToday_success() {
        float percentage = NaN;
        CommandResult result = new ProgressWeekCommand().execute(model, commandHistory);
        assertEquals(result.feedbackToUser, String.format(ProgressWeekCommand.MESSAGE_SUCCESS, percentage));
    }

}
