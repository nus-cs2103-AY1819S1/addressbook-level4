package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_LOGGED_OUT;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_NONE;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chivent
public class LogoutCommandTest {
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();
    private String credentialPath = "./src/main/resources/user_credentials/StoredCredential";

    @Test
    public void executeSuccessCommand() throws IOException {
        FileUtil.createIfMissing(Paths.get("./src/main/resources/user_credentials/StoredCredential").toFile());
        LogoutCommand logoutCommand = new LogoutCommand();
        File file = new File(credentialPath);
        assertCommandSuccess(logoutCommand, model, commandHistory, MESSAGE_LOGGED_OUT, expectedModel);
        assertFalse(file.exists());
    }

    @Test
    public void executeFailCommand() {
        LogoutCommand logoutCommand = new LogoutCommand();
        File file = new File(credentialPath);
        assertCommandSuccess(logoutCommand, model, commandHistory, MESSAGE_NONE, expectedModel);
        assertFalse(file.exists());
    }

}
