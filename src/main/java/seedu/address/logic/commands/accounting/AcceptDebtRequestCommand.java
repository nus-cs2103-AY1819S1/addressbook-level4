package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEBTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accounting.Amount;
import seedu.address.model.accounting.DebtId;
import seedu.address.model.accounting.DebtStatus;
import seedu.address.model.user.Username;

/**
 * Accepts a debt request the user received.
 */
public class AcceptDebtRequestCommand extends Command {

    public static final String COMMAND_WORD = "acceptDebtRequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accept a debt request. "
            + "Parameters: "
            + PREFIX_USERNAME + "CREDITOR\n"
            + PREFIX_AMOUNT + "AMOUNT\n"
            + PREFIX_DEBTID + "DEBTID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "Kate Ng"
            + PREFIX_AMOUNT + "6.5"
            + PREFIX_DEBTID + "16101400043732";

    public static final String MESSAGE_SUCCESS = "Debt Request Accepted: %1$s %2$s %3$s";
    public static final String MESSAGE_NO_SUCH_USER = "Input user not exist.";
    public static final String MESSAGE_NO_SUCH_DEBT = "Input debt not exist.";
    public static final String MESSAGE_AMOUNT_NOT_MATCH = "Input amount does not match the debt.";
    public static final String MESSAGE_USER_NOT_MATCH = "Input user does not match the debt";
    public static final String MESSAGE_DEBT_NOT_PENDING = "The debt is not under request.";

    private final Username creditor;
    private final Amount amount;
    private final DebtId debtId;


    public AcceptDebtRequestCommand(Username creditor, Amount amount, DebtId debtId) {
        requireNonNull(creditor);
        requireNonNull(amount);
        requireNonNull(debtId);
        this.creditor = creditor;
        this.amount = amount;
        this.debtId = debtId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasUser(creditor)) {
            throw new CommandException(MESSAGE_NO_SUCH_USER);
        }
        if (!model.hasDebtId(debtId)) {
            throw new CommandException(MESSAGE_NO_SUCH_DEBT);
        }
        if (!model.matchAmount(debtId, amount)) {
            throw new CommandException(MESSAGE_AMOUNT_NOT_MATCH);
        }
        if (!model.matchUser(debtId, creditor)) {
            throw new CommandException(MESSAGE_USER_NOT_MATCH);
        }
        if (model.matchStatus(debtId, DebtStatus.PENDING)) {
            throw new CommandException(MESSAGE_DEBT_NOT_PENDING);
        }
        model.acceptedDebtRequest(creditor, amount, debtId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, creditor, amount, debtId));
    }
}
