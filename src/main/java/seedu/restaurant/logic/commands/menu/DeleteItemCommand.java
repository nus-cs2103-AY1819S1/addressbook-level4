package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;

//@@author yican95
/**
 * Deletes an item identified using it's displayed index from the menu.
 */
public class DeleteItemCommand extends Command {

    public static final String COMMAND_WORD = "delete-item";

    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: %1$s";

    private final Index targetIndex;

    public DeleteItemCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteItem(itemToDelete);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteItemCommand // instanceof handles nulls
                    && targetIndex.equals(((DeleteItemCommand) other).targetIndex)); // state check
    }
}
