package seedu.restaurant.logic.commands.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class LogoutCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private Account account = new AccountBuilder().build();

    @Before
    public void setUp() {
        EventsCenter.getInstance().post(new LoginEvent(account));
    }

    @Test
    public void session_isAuthenticated_returnTrue() {
        assertTrue(UserSession.isAuthenticated());
    }

    @Test
    public void execute() throws CommandException {
        CommandResult commandResult = new LogoutCommand().execute(model, commandHistory);
        assertEquals(LogoutCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getAccount());
    }

    @Test
    public void execute_logoutTwice_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_NOT_AUTHENTICATED);

        new LogoutCommand().execute(model, commandHistory); // OK
        new LogoutCommand().execute(model, commandHistory); // Not OK since already in logged out state
    }

    @Test
    public void logout_clearVersionedRestaurantBook_loginStateReset() throws CommandException {
        model.commitRestaurantBook(); // Shift the pointer by 1

        Assert.assertTrue(model.canUndoRestaurantBook());
        model.undoRestaurantBook();
        Assert.assertFalse(model.canUndoRestaurantBook());

        Assert.assertTrue(model.canRedoRestaurantBook());
        model.redoRestaurantBook();
        Assert.assertFalse(model.canRedoRestaurantBook());

        new LogoutCommand().execute(model, commandHistory); // this triggers version pointer to reset to 0
        EventsCenter.getInstance().post(new LoginEvent(account));
        assertFalse(model.canUndoRestaurantBook());
        assertFalse(model.canRedoRestaurantBook());
    }
}
