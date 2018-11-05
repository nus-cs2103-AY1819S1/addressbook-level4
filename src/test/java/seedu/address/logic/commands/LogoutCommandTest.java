package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_LOGGED_OUT;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_NONE;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chivent
public class LogoutCommandTest extends DefaultCommandTest {
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();
    private String credentialPath = "./src/main/resources/user_credentials/StoredCredential";

    @Test
    public void execute_command() {
        LogoutCommand logoutCommand = new LogoutCommand();
        File file = new File(credentialPath);
        if (file.exists()) {
            assertCommandSuccess(logoutCommand, model, commandHistory, MESSAGE_LOGGED_OUT, expectedModel);
        } else {
            assertCommandSuccess(logoutCommand, model, commandHistory, MESSAGE_NONE, expectedModel);
        }
        assertFalse(file.exists());
    }
}
