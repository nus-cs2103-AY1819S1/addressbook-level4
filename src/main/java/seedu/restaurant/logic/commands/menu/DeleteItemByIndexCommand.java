package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ENDING_INDEX;

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
public class DeleteItemByIndexCommand extends Command {
    public static final String COMMAND_WORD = "delete-item-index";

    public static final String COMMAND_ALIAS = "dii";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer, starting index) "
            + "[" + PREFIX_ENDING_INDEX + "INDEX](cannot be smaller than the starting index)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted %1$d items";

    private final Index targetIndex;
    private final Index endingIndex;

    public DeleteItemByIndexCommand(Index targetIndex, Index endingIndex) {
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
                || (other instanceof DeleteItemByIndexCommand // instanceof handles nulls
                    && targetIndex.equals(((DeleteItemByIndexCommand) other).targetIndex)
                    && endingIndex.equals(((DeleteItemByIndexCommand) other).endingIndex)); // state check
    }
}
