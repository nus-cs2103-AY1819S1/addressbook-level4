package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static seedu.address.logic.commands.HelpCommand.SHOWING_SHORT_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        String[] args = {""};
        assertCommandSuccess(new HelpCommand(args), model, commandHistory, SHOWING_SHORT_HELP_MESSAGE, expectedModel);

        BaseEvent recentEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(recentEvent instanceof ShowHelpRequestEvent);
        assertTrue(((ShowHelpRequestEvent) recentEvent).isSummarized);
        assertTrue(eventsCollectorRule
                .eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_helpmore_success() {
        String[] args = {" more"};
        assertCommandSuccess(new HelpCommand(args), model, commandHistory, SHOWING_HELP_MESSAGE, expectedModel);

        BaseEvent recentEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(recentEvent instanceof ShowHelpRequestEvent);
        assertFalse(((ShowHelpRequestEvent) recentEvent).isSummarized);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
