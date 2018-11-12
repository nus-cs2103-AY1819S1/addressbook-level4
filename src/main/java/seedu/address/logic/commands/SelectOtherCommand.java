package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.ListPanel;

/**
 * Selects a item identified using it's displayed index from the address book.
 */
public class SelectOtherCommand extends Command {

    public static final String COMMAND_WORD = "selectOther";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the item identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ITEM_SUCCESS = "Selected item: %1$s";

    private final Index targetIndex;
    private final String type;

    public SelectOtherCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.type = ListPanel.getType();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (ListPanel.getType() == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        int size;

        switch (ListPanel.getType()) {
        case "Friends":
            size = model.getFriendsList().size();
            break;
        case "FriendRequests":
            size = model.getFriendRequestsList().size();
            break;
        case "Jio":
            size = model.getJioList().size();
            break;
        case "Debt":
            size = model.getDebtList().size();
            break;
        case "Group":
            size = model.getGroupList().size();
            break;
        default:
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        if (targetIndex.getZeroBased() >= size) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(type, targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ITEM_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectOtherCommand // instanceof handles nulls
                && targetIndex.equals(((SelectOtherCommand) other).targetIndex)); // state check
    }
}
