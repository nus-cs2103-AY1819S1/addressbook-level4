package seedu.clinicio.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.logic.commands.LogoutCommand.MESSAGE_SUCCESS;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.commons.events.ui.LogoutClinicIoEvent;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.ui.testutil.EventsCollectorRule;

public class LogoutCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_logout_success() {
        UserSession.createSession(ADAM);
        assertCommandSuccess(new LogoutCommand(), model, commandHistory, MESSAGE_SUCCESS, expectedModel, analytics);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof LogoutClinicIoEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
