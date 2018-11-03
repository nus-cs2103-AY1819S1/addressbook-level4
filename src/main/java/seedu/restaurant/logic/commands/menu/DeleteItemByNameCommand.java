package seedu.restaurant.logic.commands.menu;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.exceptions.ItemNotFoundException;

/**
 * Deletes an item identified using its name from the menu.
 */
public class DeleteItemByNameCommand extends DeleteItemCommand {

    private final Name targetName;

    public DeleteItemByNameCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            Item itemToDelete = model.findItem(targetName);
            model.deleteItem(itemToDelete);
            model.commitRestaurantBook();
            EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, 1));
        } catch (ItemNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_NAME_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteItemByNameCommand // instanceof handles nulls
                    && (targetName.equals(((DeleteItemByNameCommand) other).targetName))); // state check
    }
}
