package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.HomeCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

public class HomeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_home_success() {
        CommandResult result = new HomeCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BackToHomeEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
