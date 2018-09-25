package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Analytics;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_exit_success() {
        CommandResult result = new ExitCommand().execute(model, commandHistory, analytics);
        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
