package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;

/**
 * Deletes an item identified using it's displayed index from the menu.
 */
public class DeleteItemCommandByIndex extends DeleteItemCommand {

    private final Index targetIndex;
    private final Index endingIndex;

    public DeleteItemCommandByIndex(Index targetIndex, Index endingIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
        this.endingIndex = endingIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (endingIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        int numOfItems = endingIndex.getOneBased() - targetIndex.getZeroBased();

        for (int i = endingIndex.getZeroBased(); i >= targetIndex.getZeroBased(); i--) {
            Item itemToDelete = lastShownList.get(i);
            model.deleteItem(itemToDelete);
        }

        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, numOfItems));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteItemCommandByIndex // instanceof handles nulls
                    && targetIndex.equals(((DeleteItemCommandByIndex) other).targetIndex)
                    && endingIndex.equals(((DeleteItemCommandByIndex) other).endingIndex)); // state check
    }
}
