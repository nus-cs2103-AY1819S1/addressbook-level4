package seedu.restaurant.logic.commands.account;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;

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
public class DeregisterCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Test
    public void execute_accountExists_success() throws CommandException {
        Account validAccount = new AccountBuilder(DEMO_ADMIN).build();
        CommandResult registerCommandResult = new RegisterCommand(validAccount).execute(model, commandHistory);

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, validAccount),
                registerCommandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        CommandResult deregisterCommandResult = new DeregisterCommand(validAccount).execute(model, commandHistory);

        assertEquals(String.format(DeregisterCommand.MESSAGE_SUCCESS, validAccount),
                deregisterCommandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_accountNotExists_throwCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeregisterCommand.MESSAGE_USERNAME_NOT_FOUND);
        Account invalidAccount = new AccountBuilder().withUsername("nonexistingusernametest").build();
        new DeregisterCommand(invalidAccount).execute(model, commandHistory);
    }
}
