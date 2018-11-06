package seedu.restaurant.logic.commands.account;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class LoginCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());

    @Before
    public void setUp() {
        // Logs out before every test case
        UserSession.destroy();
    }

    @Test
    public void session_notAuthenticated_returnFalse() {
        assertFalse(UserSession.isAuthenticated());
    }

    @Test
    public void execute_validAccount_setUserSession() throws CommandException {
        Account validAccount = new AccountBuilder().build();
        CommandResult commandResult = new LoginCommand(validAccount).execute(model, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Since logged in, session will be set
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }

    @Test
    public void execute_loginIfAlreadyLoggedIn_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ALREADY_AUTHENTICATED);

        Account validAccount = new AccountBuilder().build();
        new LoginCommand(validAccount).execute(model, commandHistory);
        new LoginCommand(validAccount).execute(model, commandHistory);
    }

    @Test
    public void execute_validAccountWrongPassword_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_WRONG_PASSWORD);

        Account invalidAccount = new AccountBuilder().withPassword("1122qq!@#123").build();
        new LoginCommand(invalidAccount).execute(model, commandHistory);
    }

    @Test
    public void execute_nonExistentAccount_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ACCOUNT_NOT_FOUND);

        Account invalidAccount = new AccountBuilder().withUsername("invalidusername").build();
        new LoginCommand(invalidAccount).execute(model, commandHistory);
    }
}
