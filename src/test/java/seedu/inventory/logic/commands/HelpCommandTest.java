package seedu.inventory.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.inventory.commons.events.ui.ShowHelpRequestEvent;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        assertCommandSuccess(new HelpCommand(), model, commandHistory, SHOWING_HELP_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
