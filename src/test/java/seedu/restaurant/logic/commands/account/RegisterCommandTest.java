package seedu.restaurant.logic.commands.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class RegisterCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Test
    public void executeSuccess() throws CommandException {
        Account validAccount = new AccountBuilder(DEMO_ADMIN).build();
        CommandResult commandResult = new RegisterCommand(validAccount).execute(model, commandHistory);

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        Assert.assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateUsername_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RegisterCommand.MESSAGE_DUPLICATE_USERNAME);
        Account validAccount = new AccountBuilder(DEMO_ADMIN).build();
        new RegisterCommand(validAccount).execute(model, commandHistory);
        new RegisterCommand(validAccount).execute(model, commandHistory);
    }

    @Test
    public void execute_accountExists_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        Account invalidAccount = new AccountBuilder().build();
        new LoginCommand(invalidAccount).execute(model, commandHistory);
    }
}
