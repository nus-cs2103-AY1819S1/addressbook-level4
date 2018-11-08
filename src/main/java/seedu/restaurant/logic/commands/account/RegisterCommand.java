package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PASSWORD;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.Account;

//@@author AZhiKai
/**
 * Adds a new {@code Account}.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";
    public static final String COMMAND_ALIAS = "reg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a new user account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "azhikai "
            + PREFIX_PASSWORD + "1122qq "
            + PREFIX_NAME + "Ang Zhi Kai";

    public static final String MESSAGE_SUCCESS = "New account registered: %1$s";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists. Please choose another "
            + "username";

    private final Account account;

    public RegisterCommand(Account account) {
        requireNonNull(account);
        this.account = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAccount(account)) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addAccount(account);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, account));
    }

    @Override
    public String toString() {
        return account.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                    && account.equals(((RegisterCommand) other).account));
    }
}
