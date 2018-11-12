package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;

/**
 * List the logged in user's creditors.
 */
public class ListCreditorCommand extends Command {
    public static final String COMMAND_WORD = "listCreditor";

    public static final String MESSAGE_SUCCESS = "Listed all creditor.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all login user's creditor.";

    public static final String MESSAGE_NOT_LOGGED_IN =
            "You must login before listing your creditor(s).";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }
        model.debtListing(model.getCreditorList());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}

