package seedu.address.logic.commands.accounting;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List the logged in user's debtors.
 */
public class ListDebtorCommand extends Command {
    public static final String COMMAND_WORD = "listDebtor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all login user's debtor.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.listDebtor();
        return null;
    }
}

