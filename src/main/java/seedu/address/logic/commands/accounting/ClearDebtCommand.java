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
 * Login user(creditor) clear an amount of debt for a user(debtor).
 */
public class ClearDebtCommand extends Command {
    public static final String COMMAND_WORD = "clearDebt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear a debt. "
            + "Parameters: "
            + PREFIX_USERNAME + "DEBTOR\n"
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Kate Ng"
            + PREFIX_AMOUNT + "6.5";

    public static final String MESSAGE_SUCCESS = " %1$s's debt of %2$f SGD is cleared.";
    public static final String MESSAGE_NO_SUCH_USER = "Input user not exist.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before clearing a debt.";
    public static final String MESSAGE_NO_DEBT_BETWEEN = "There are no debt between you and input user";
    public static final String MESSAGE_INVALID_AMOUNT = "Input amount must be less than or equal to the debt amount";
    public static final String MESSAGE_NOT_ALLOWED = "You are not allowed to clear this debt.";
    public static final String MESSAGE_LESS_THAN_ZERO = "Input amount should be larger than zero.";
    private final Username debtor;
    private final Amount amount;


    public ClearDebtCommand(Username debtor, Amount amount) {
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
        if (!model.debtExist(debtor)) {
            throw new CommandException(MESSAGE_NO_DEBT_BETWEEN);
        }
        if (!model.allowToClear(debtor, amount)) {
            throw new CommandException(MESSAGE_INVALID_AMOUNT);
        }
        if (model.isSameAsCurrentUser(debtor)) {
            throw new CommandException(MESSAGE_NOT_ALLOWED);
        }
        if (!(amount.toDouble() > 0)) {
            throw new CommandException(MESSAGE_LESS_THAN_ZERO);
        }
        model.clearDebt(debtor, amount);
        return new CommandResult(String.format(MESSAGE_SUCCESS, debtor, amount.toDouble()));
    }
}
