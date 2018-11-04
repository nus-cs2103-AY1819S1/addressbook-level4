package seedu.address.logic.commands;

import static org.junit.Assert.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.User;
import seedu.address.ui.testutil.EventsCollectorRule;

public class PasswordCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        User.buildAdmin(User.ADMIN_DEFAULT_USERNAME, User.ADMIN_DEFUALT_PASSWORD);
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void execute_password_success() {
        String successMessage = PasswordCommand.STARTING_PASSWORD_MESSAGE + "\r\n"
            + PasswordCommand.ADMIN_MODIFICATION_MESSAGE;
        assertCommandSuccess(new PasswordCommand(), model, commandHistory, successMessage, expectedModel);
        assertNull(eventsCollectorRule.eventsCollector.getMostRecent());
        //Unfortunately, due to the way unit testing is setup, it is very difficult to test the other parts
        //of the password command. We do not have access to the CommandResult, thus the lack of lambdas, and
        //there are no interceptors built in (assertCommandSuccess always creates a new command)
    }
}
