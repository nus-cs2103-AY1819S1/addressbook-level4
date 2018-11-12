package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;
import seedu.address.model.accounting.Amount;
import seedu.address.model.user.Username;

/**
 * Login user(creditor) add a pending debt(send a debt request) to other user(debtor).
 */
public class AddDebtCommand extends Command {
    public static final String COMMAND_WORD = "addDebt";

    // TODO
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a request to debtor and add to record. "
            + "Parameters: "
            + PREFIX_USERNAME + "DEBTOR\n"
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Kate Ng "
            + PREFIX_AMOUNT + "6.5";

    public static final String MESSAGE_SUCCESS = "A debt request of %2$f SGD to %1$s is sent";
    public static final String MESSAGE_NO_SUCH_USER = "Input user not exist.";
    public static final String MESSAGE_INVALID_AMOUNT = "Input amount must be larger than zero "
            + "and less than a hundred million.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before adding a debt.";
    public static final String MESSAGE_CANNOT_ADD_DEBT_TO_ONESELF = "You cannot create debt to yourself.";

    private final Username debtor;
    private final Amount amount;

    public AddDebtCommand(Username debtor, Amount amount) {
        requireNonNull(debtor);
        requireNonNull(amount);
        this.debtor = debtor;
        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }
        if (!model.hasUser(debtor)) {
            throw new CommandException(MESSAGE_NO_SUCH_USER);
        }
        if (model.isSameAsCurrentUser(debtor)) {
            throw new CommandException(MESSAGE_CANNOT_ADD_DEBT_TO_ONESELF);
        }
        if (!(100000000 > amount.toDouble() && amount.toDouble() > 0)) {
            throw new CommandException(MESSAGE_INVALID_AMOUNT);
        }
        model.addDebt(debtor, amount);
        return new CommandResult(String.format(MESSAGE_SUCCESS, debtor, amount.toDouble()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddDebtCommand // instanceof handles nulls
                && debtor.equals(((AddDebtCommand) other).debtor)
                && amount.equals(((AddDebtCommand) other).amount));
    }

}
