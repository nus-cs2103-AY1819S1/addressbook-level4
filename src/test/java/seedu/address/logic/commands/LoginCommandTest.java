package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.LoginCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleUserDataUtil;
import seedu.address.testutil.Assert;

/**
 * Contains tests for Login Command
 */
public class LoginCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private CommandHistory commandHistory = new CommandHistory();

    private Password invalidPassword = new Password("theRock");
    private Password validPassword = new Password("pwwd123");
    private String expectedMessage = LoginCommand.MESSAGE_SUCCESS;
    private Username invalidUsername = new Username("theMyth");
    private Username validUsername = new Username("navekom");

    @Test
    public void execute_loginAgainAfterLogin() {
        model.loginUser(validUsername);
        LoginCommand loginCommand = new LoginCommand(validUsername, validPassword);
        Assert.assertThrows(CommandException.class, () -> loginCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_login() {
        expectedModel.loginUser(validUsername);
        assertCommandSuccess(new LoginCommand(validUsername, validPassword),
                model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_loginWithNonExistingUser() {
        LoginCommand loginCommand = new LoginCommand(invalidUsername, validPassword);
        Assert.assertThrows(CommandException.class, () -> loginCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_loginValidUserInvalidPassword() {
        model.loginUser(validUsername);
        LoginCommand loginCommand = new LoginCommand(validUsername, invalidPassword);
        Assert.assertThrows(CommandException.class, () -> loginCommand.execute(model, commandHistory));
    }
}
