package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PASSWORD;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;

//@@author AZhiKai
/**
 * Logs the user into an existing {@code Account}, and create a {@code UserSession}.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to the system with an existing account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "azhikai "
            + PREFIX_PASSWORD + "1122qq";

    public static final String MESSAGE_SUCCESS = "Successfully logged in to %s";
    public static final String MESSAGE_ACCOUNT_NOT_FOUND = "The account does not exist";
    public static final String MESSAGE_WRONG_PASSWORD = "The credential is invalid";
    public static final String MESSAGE_ALREADY_AUTHENTICATED = "You are already logged in";

    private final Account toLogin;

    public LoginCommand(Account toLogin) {
        requireNonNull(toLogin);
        this.toLogin = toLogin;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (UserSession.isAuthenticated()) {
            throw new CommandException(MESSAGE_ALREADY_AUTHENTICATED);
        }

        if (!model.hasAccount(toLogin)) {
            throw new CommandException(MESSAGE_ACCOUNT_NOT_FOUND);
        }

        Account retrievedAccount = model.getAccount(toLogin);

        boolean isVerified = Password.verifyPassword(toLogin.getPassword().toString(),
                retrievedAccount.getPassword().toString().getBytes());

        if (!isVerified) {
            throw new CommandException(MESSAGE_WRONG_PASSWORD);
        }

        EventsCenter.getInstance().post(new LoginEvent(retrievedAccount));
        return new CommandResult(String.format(MESSAGE_SUCCESS, retrievedAccount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                    && toLogin.equals(((LoginCommand) other).toLogin));
    }
}
