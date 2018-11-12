package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.LogoutCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Username;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleUserDataUtil;
import seedu.address.testutil.Assert;

/**
 * Contains tests for Logout Command
 */
public class LogoutCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private CommandHistory commandHistory = new CommandHistory();
    private String expectedMessage = LogoutCommand.MESSAGE_SUCCESS;
    private Username existingUsername = new Username("navekom");

    @Test
    public void execute_logoutWithNoCurrentUser() {
        LogoutCommand logoutCommand = new LogoutCommand();
        Assert.assertThrows(CommandException.class, () -> logoutCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_logout() {
        model.loginUser(existingUsername);
        assertCommandSuccess(new LogoutCommand(),
                model, commandHistory, expectedMessage, expectedModel);
    }
}
