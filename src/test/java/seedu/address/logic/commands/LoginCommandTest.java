package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LAUNCHED;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.google.PhotoHandler;

//@@author chivent
public class LoginCommandTest {
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeCommandFailure() {
        // unable to write test for success due to connectivity issues
        // success test would require a permanent set of stored google credentials, thus is insecure.
        LoginCommand loginCommand = new LoginCommand();
        assertCommandSuccess(loginCommand, model, commandHistory, MESSAGE_LAUNCHED, expectedModel);
    }

    @Test
    public void executeCommandSuccess() {
        model.setPhotoHandler(new PhotoHandler(null, "user"));
        LoginCommand loginCommand = new LoginCommand();
        assertCommandSuccess(loginCommand, model, commandHistory, "Logged in as user.", expectedModel);
    }
}
