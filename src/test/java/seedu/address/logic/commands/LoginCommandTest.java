package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LAUNCHED;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chivent
public class LoginCommandTest extends DefaultCommandTest {
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_command_failure() {
        // unable to write test for success due to connectivity issues
        // success test would require a permanent set of stored google credentials, thus is insecure.
        LoginCommand loginCommand = new LoginCommand();
        assertCommandSuccess(loginCommand, model, commandHistory, MESSAGE_LAUNCHED, expectedModel);
    }
}
