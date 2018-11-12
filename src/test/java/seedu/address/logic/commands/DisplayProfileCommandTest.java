package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.DisplayProfileCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Username;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleUserDataUtil;
import seedu.address.testutil.Assert;

/**
 * Contains tests for DisplayProfile
 */
public class DisplayProfileCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private CommandHistory commandHistory = new CommandHistory();
    private String expectedMessage = DisplayProfileCommand.MESSAGE_SUCCESS;

    @Test
    public void execute_withoutLogin() {
        DisplayProfileCommand displayProfileCommand = new DisplayProfileCommand();
        Assert.assertThrows(CommandException.class, () -> displayProfileCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_displayProfile() {
        model.loginUser(new Username("navekom"));
        expectedModel.loginUser(new Username("navekom"));
        expectedModel.displayProfile();
        assertCommandSuccess(new DisplayProfileCommand(), model, commandHistory, expectedMessage, expectedModel);
    }
}
