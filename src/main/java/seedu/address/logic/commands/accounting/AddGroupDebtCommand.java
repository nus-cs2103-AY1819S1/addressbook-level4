package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.accounting.Amount;

/**
 * Login user(creditor) add a pending debt(send a debt request) to every other user(debtor) in the group.
 * The amount will split equally to every user in the group.
 */
public class AddGroupDebtCommand extends Command {
    public static final String COMMAND_WORD = "addGroupDebt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a request to debtor and add to record. "
            + "Parameters: "
            + PREFIX_GROUP + "DEBTOR\n"
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "My Lonely Club "
            + PREFIX_AMOUNT + "50";

    public static final String MESSAGE_SUCCESS = "A debt request of %2$f SGD to other member in %1$s is sent";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before adding a debt.";
    public static final String MESSAGE_GROUP_NOT_EXIST = "Input group does not exist.";
    public static final String MESSAGE_NOT_IN_GROUP = "Sorry, you are not a part of the group. ";
    public static final String MESSAGE_INVALID_AMOUNT = "Input amount must be larger than zero.";

    private final Name groupName;
    private final Amount amount;

    public AddGroupDebtCommand(Name groupName, Amount amount) {
        requireNonNull(groupName);
        requireNonNull(amount);
        this.groupName = groupName;
        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }
        if (!model.hasGroup(groupName)) {
            throw new CommandException(MESSAGE_GROUP_NOT_EXIST);
        }
        if (!model.isInGroup(groupName)) {
            throw new CommandException(MESSAGE_NOT_IN_GROUP);
        }
        if (!(amount.toDouble() > 0)) {
            throw new CommandException(MESSAGE_INVALID_AMOUNT);
        }
        model.addGroupDebt(groupName, amount);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, amount.toDouble()));
    }
}
