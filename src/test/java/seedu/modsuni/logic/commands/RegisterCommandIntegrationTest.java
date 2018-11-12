package seedu.modsuni.logic.commands;

import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code
 * RegisterCommand}.
 */
public class RegisterCommandIntegrationTest {

    private static Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(
            new ModuleList(),
            new UserPrefs(),
            getTypicalCredentialStore());
    }

    @Test
    public void execute_newCredential_success() {
        model = new ModelManager(
            new ModuleList(),
            new UserPrefs(),
            getTypicalCredentialStore());

        Credential validCredential = new Credential(
            new Username("u"),
            new Password("#Qwerty123"));
        User dummyUser = new StudentBuilder().build();

        Model expectedModel = new ModelManager(
            new ModuleList(),
            new UserPrefs(),
            getTypicalCredentialStore());
        expectedModel.addCredential(validCredential);

        Path dummyPath = Paths.get("dummy.xml");

        RegisterCommand toExecute = new RegisterCommand(validCredential,
            dummyUser, dummyPath);

        assertCommandSuccess(toExecute, model,
            commandHistory,
            String.format(RegisterCommand.MESSAGE_SUCCESS, dummyUser, dummyPath),
            expectedModel);
    }

    @Test
    public void execute_duplicateCredential_throwsCommandException() {
        model = new ModelManager(
            new ModuleList(),
            new UserPrefs(),
            getTypicalCredentialStore());

        Path dummyPath = Paths.get("dummy.xml");

        assertCommandFailure(new RegisterCommand(CREDENTIAL_STUDENT_MAX,
                new StudentBuilder().build(), dummyPath),
            model,
            commandHistory,
            RegisterCommand.MESSAGE_DUPLICATE_USERNAME);
    }


    @Test
    public void execute_alreadyLoggedIn_throwsCommandException() {
        model = new ModelManager(
            new ModuleList(),
            new UserPrefs(),
            getTypicalCredentialStore());
        model.setCurrentUser(STUDENT_MAX);

        Path dummyPath = Paths.get("dummy.xml");

        assertCommandFailure(new RegisterCommand(CREDENTIAL_STUDENT_MAX,
                new StudentBuilder().build(), dummyPath),
            model,
            commandHistory,
            RegisterCommand.MESSAGE_ALREADY_LOGGED_IN);
    }
}
