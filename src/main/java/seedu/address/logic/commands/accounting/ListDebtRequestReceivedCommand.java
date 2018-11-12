package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;

/**
 * List the logged in user's debt requests.
 */
public class ListDebtRequestReceivedCommand extends Command {
    public static final String COMMAND_WORD = "listDebtRequestReceived";

    public static final String MESSAGE_SUCCESS = "Listed all debt request received.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all request login user received.";

    public static final String MESSAGE_NOT_LOGGED_IN =
            "You must login before listing your received debt request.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }
        model.debtListing(model.getDebtRequestReceived());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}

