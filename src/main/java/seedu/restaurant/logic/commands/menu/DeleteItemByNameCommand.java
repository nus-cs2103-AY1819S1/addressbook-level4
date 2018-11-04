package seedu.restaurant.logic.commands.menu;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.exceptions.ItemNotFoundException;

//@@author yican95
/**
 * Deletes an item identified using its name from the menu.
 */
public class DeleteItemByNameCommand extends Command {

    public static final String COMMAND_WORD = "delete-item-name";

    public static final String COMMAND_ALIAS = "din";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by name used in the displayed item list.\n"
            + "Parameters: NAME (must be in the displayed item list) "
            + "or Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Apple Juice";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Item: %1$s";
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
            return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete));
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
