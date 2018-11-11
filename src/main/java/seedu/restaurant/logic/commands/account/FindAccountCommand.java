package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.UsernameContainsKeywordPredicate;

//@@author AZhiKai

/**
 * Finds and lists all accounts whose username contains any of the argument keywords. Keyword matching is case
 * insensitive.
 */
public class FindAccountCommand extends Command {

    public static final String COMMAND_WORD = "find-account";

    public static final String COMMAND_ALIAS = "fa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find all accounts whose username contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " root";

    public static final String MESSAGE_SUCCESS = "Listed %d accounts";

    private final UsernameContainsKeywordPredicate predicate;

    public FindAccountCommand(UsernameContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAccountList(predicate);
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredAccountList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAccountCommand // instanceof handles nulls
                    && predicate.equals(((FindAccountCommand) other).predicate)); // state check
    }
}
