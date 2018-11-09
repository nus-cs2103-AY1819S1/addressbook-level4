package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.jxmusic.commons.events.ui.ExitAppRequestEvent;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
   @Rule
   public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

   private Model model = new ModelManager();

   @Test
   public void execute_exit_success() {
       CommandResult result = new ExitCommand().execute(model);
       assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
       assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
       assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
   }
}
