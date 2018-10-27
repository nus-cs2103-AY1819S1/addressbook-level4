package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.EditTransactionDescriptor;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Calculates an interest value for a given transaction (either using simple or compound scheme as specified by the
 * user).
 */
public class InterestCommand extends Command {
    public static final String COMMAND_WORD = "interest";
    public static final String COMMAND_ALIAS = "int";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculates interest on all transactions "
            + "based on the interest scheme and value that the user inputs.\n"
            + "Parameters: SCHEME INTEREST_RATE...\n"
            + "Example: " + COMMAND_WORD + " simple 1.1%";
    private static final String MESSAGE_INCORRECT_ARGUMENTS = "In";
    private final String scheme;
    private final String rate;


    public InterestCommand(String scheme, String rate) {
        this.scheme = scheme;
        this.rate = rate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();
        for (int i = 0; i < lastShownList.size(); i++) {
            Transaction target = lastShownList.get(i);
            Transaction editedTransaction = Transaction.copy(target);
            editedTransaction.calculateInterest(scheme, rate);
            model.updateTransaction(target, editedTransaction);
        }
        return new CommandResult("");
    }
}
