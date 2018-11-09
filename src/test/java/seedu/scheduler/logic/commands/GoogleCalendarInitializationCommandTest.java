package seedu.scheduler.logic.commands;

import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import org.junit.Test;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GetGoogleEventsCommandCommand.
 */
public class GoogleCalendarInitializationCommandTest {
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeGgEvents_valid_success() throws Exception {
        //TODO:Implement the test logic
        GoogleCalendarInitializationCommand
                googleCalendarInitializationCommand = new GoogleCalendarInitializationCommand();

        String expectedMessage = String.format(GoogleCalendarInitializationCommand.MESSAGE_INITIALIZE_SUCCESS);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());

        expectedModel.getFilteredEventList();
        expectedModel.commitScheduler();


    }
}
