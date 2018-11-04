package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;

//@@author AZhiKai
/**
 * List all {@code Account}.
 */
public class ListAccountsCommand extends Command {

    public static final String COMMAND_WORD = "list-accounts";

    public static final String COMMAND_ALIAS = "la";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
