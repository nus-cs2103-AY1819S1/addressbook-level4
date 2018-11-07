package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.restaurant.commons.events.ui.accounts.JumpToAccountListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.Account;

//@@author AZhiKai

/**
 * Selects an account identified using its displayed index from the ingredient list.
 */
public class SelectAccountCommand extends Command {

    public static final String COMMAND_WORD = "select-account";

    public static final String COMMAND_ALIAS = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the account identified by the index number used in the displayed account list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ACCOUNT_SUCCESS = "Selected account: %1$s";
    public static final String MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX = "The account index provided is invalid";

    private final Index targetIndex;

    public SelectAccountCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Account> filteredAccountList = model.getFilteredAccountList();

        if (targetIndex.getZeroBased() >= filteredAccountList.size()) {
            throw new CommandException(MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        EventsCenter.getInstance().post(new JumpToAccountListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ACCOUNT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectAccountCommand // instanceof handles nulls
                    && targetIndex.equals(((SelectAccountCommand) other).targetIndex)); // state check
    }
}
