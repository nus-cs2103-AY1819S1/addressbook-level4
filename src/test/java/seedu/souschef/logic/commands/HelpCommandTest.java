package seedu.souschef.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.souschef.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.souschef.commons.events.ui.ShowHelpRequestEvent;
import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model<Recipe> model = new ModelSetCoordinator().getRecipeModel();
    private Model<Recipe> expectedModel = new ModelSetCoordinator().getRecipeModel();
    private History history = new History();

    @Test
    public void execute_help_success() {
        assertCommandSuccess(new HelpCommand(), model, history, SHOWING_HELP_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
