package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.commons.events.ui.menu.JumpToItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;

//@@author yican95
/**
 * Selects an item identified using it's displayed index from the menu.
 */
public class SelectItemCommand extends Command {

    public static final String COMMAND_WORD = "select-item";

    public static final String COMMAND_ALIAS = "si";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ITEM_SUCCESS = "Selected Item: %1$s";

    private final Index targetIndex;

    public SelectItemCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Item> filteredItemList = model.getFilteredItemList();

        if (targetIndex.getZeroBased() >= filteredItemList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        EventsCenter.getInstance().post(new JumpToItemListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ITEM_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectItemCommand // instanceof handles nulls
                    && targetIndex.equals(((SelectItemCommand) other).targetIndex)); // state check
    }
}
