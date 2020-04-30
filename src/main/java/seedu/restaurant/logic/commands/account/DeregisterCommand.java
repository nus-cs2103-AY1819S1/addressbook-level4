package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.Account;

//@@author AZhiKai
/**
 * Deregisters an existing {@code Account}. This command should only be executed by a user with high privileges
 * when roles are implemented in v2.0.
 */
public class DeregisterCommand extends Command {

    public static final String COMMAND_WORD = "deregister";
    public static final String COMMAND_ALIAS = "dereg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deregister an existing account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "azhikai";

    public static final String MESSAGE_SUCCESS = "Account deregistered: %1$s";
    public static final String MESSAGE_USERNAME_NOT_FOUND = "This username does not exist";
    public static final String MESSAGE_CANNOT_SELF_DEREGISTER = "You cannot deregister your own account";

    private final Account account;

    public DeregisterCommand(Account account) {
        requireNonNull(account);
        this.account = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasAccount(account)) {
            throw new CommandException(MESSAGE_USERNAME_NOT_FOUND);
        }

        Account retrievedAccount = model.getAccount(account);

        if (UserSession.getAccount().equals(retrievedAccount)) {
            throw new CommandException(MESSAGE_CANNOT_SELF_DEREGISTER);
        }

        model.removeAccount(retrievedAccount);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, account));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeregisterCommand // instanceof handles nulls
                    && account.getUsername().equals(((DeregisterCommand) other).account.getUsername()));
    }
}
