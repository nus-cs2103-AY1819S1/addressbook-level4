package seedu.scheduler.logic.commands;

import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;


import seedu.scheduler.model.Model;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GetGoogleEventsCommandCommand.
 */
public class GetGoogleEventsCommandTest {
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeGGEvents_valid_success() throws Exception {
        //TODO:Implement the test logic
        GetGoogleEventsCommand getGoogleEventsCommand = new GetGoogleEventsCommand();

        String expectedMessage = String.format(GetGoogleEventsCommand.MESSAGE_GGEVENTS_SUCCESS);

        Model expectedModel = new ModelManager(new Scheduler(model.getScheduler()), new UserPrefs());

        expectedModel.getFilteredEventList();
        expectedModel.commitScheduler();


    }
}
