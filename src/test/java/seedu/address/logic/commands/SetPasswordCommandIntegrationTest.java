package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.ModelUtil.getTypicalModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LoginCredentials;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.exceptions.InvalidDataException;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.user.Password;
import seedu.address.model.user.PasswordTest;
import seedu.address.testutil.TypicalExpenses;

public class SetPasswordCommandIntegrationTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = getTypicalModel();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noOldPassword_assertSetPasswordSuccessful() throws NoUserSelectedException, ParseException,
            InvalidDataException, NonExistentUserException {
        CommandResult commandResult = new LoginCommand(new LoginCredentials(TypicalExpenses.SAMPLE_USERNAME, null))
                .execute(model, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_LOGIN_SUCCESS, TypicalExpenses.SAMPLE_USERNAME.toString()),
                commandResult.feedbackToUser);
        assertTrue(model.hasSelectedUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        commandResult = new SetPasswordCommand(null, PasswordTest.VALID_PASSWORD,
                PasswordTest.VALID_PASSWORD_STRING).execute(model, commandHistory);
        assertEquals(model.getExpenseTracker().getPassword().get(), PasswordTest.VALID_PASSWORD);
        assertEquals(SetPasswordCommand.MESSAGE_SET_PASSWORD_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_oldPasswordInvalid_assertSetPasswordFailure() throws NoUserSelectedException, ParseException,
            InvalidDataException, NonExistentUserException {
        CommandResult commandResult = new LoginCommand(new LoginCredentials(TypicalExpenses.SAMPLE_USERNAME, null))
                .execute(model, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_LOGIN_SUCCESS, TypicalExpenses.SAMPLE_USERNAME.toString()),
                commandResult.feedbackToUser);
        commandResult = new SetPasswordCommand(null, PasswordTest.VALID_PASSWORD,
                PasswordTest.VALID_PASSWORD_STRING).execute(model, commandHistory);
        assertEquals(model.getExpenseTracker().getPassword().get(), PasswordTest.VALID_PASSWORD);
        assertEquals(SetPasswordCommand.MESSAGE_SET_PASSWORD_SUCCESS, commandResult.feedbackToUser);
        commandResult = new SetPasswordCommand(
                new Password(PasswordTest.INVALID_PASSWORD_STRING_SHORT, false),
                PasswordTest.VALID_PASSWORD, PasswordTest.VALID_PASSWORD_STRING).execute(model, commandHistory);
        assertEquals(model.getExpenseTracker().getPassword().get(), PasswordTest.VALID_PASSWORD);
        assertEquals(SetPasswordCommand.MESSAGE_INCORRECT_PASSWORD, commandResult.feedbackToUser);
    }
}
