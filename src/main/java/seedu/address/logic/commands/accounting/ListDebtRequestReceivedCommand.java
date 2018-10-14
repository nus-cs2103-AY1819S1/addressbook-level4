package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List the logged in user's debt requests.
 */
public class ListDebtRequestReceivedCommand extends Command {
    public static final String COMMAND_WORD = "listDebtReceivedRequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all request login user received.";
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.listDebtRequestReceived();
        return null;
    }

}

