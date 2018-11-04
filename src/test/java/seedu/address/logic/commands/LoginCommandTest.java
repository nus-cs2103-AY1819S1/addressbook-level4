package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
;

public class LoginCommandTest extends DefaultCommandTest{
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_command_success() {
        LoginCommand loginCommand = new LoginCommand();
        String expected = "Login unsuccessful";
        assertCommandSuccess(loginCommand, model, commandHistory, expected, expectedModel);
    }
}
