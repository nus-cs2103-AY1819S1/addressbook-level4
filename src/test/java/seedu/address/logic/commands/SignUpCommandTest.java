package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalUsers.BENNY;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.SignUpCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleUserDataUtil;
import seedu.address.testutil.Assert;

public class SignUpCommandTest {
    private Model model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private CommandHistory commandHistory = new CommandHistory();
    private String expectedMessage = SignUpCommand.MESSAGE_SUCCESS;

    @Test
    public void execute_signUpAgainAfterSignUp() {
        model.addUser(BENNY);
        model.loginUser(BENNY.getUsername());
        SignUpCommand signUpCommand = new SignUpCommand(BENNY);
        Assert.assertThrows(CommandException.class, () -> signUpCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_signUp() {
        expectedModel.addUser(BENNY);
        expectedModel.loginUser(BENNY.getUsername());
        assertCommandSuccess(new SignUpCommand(BENNY), model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_signUpWithExistingUser() {
        model.addUser(BENNY);
        model.loginUser(BENNY.getUsername());
        model.logoutUser();
        SignUpCommand signUpCommand = new SignUpCommand(BENNY);
        Assert.assertThrows(CommandException.class, () -> signUpCommand.execute(model, commandHistory));
    }
}
