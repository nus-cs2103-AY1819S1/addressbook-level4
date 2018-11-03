package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;

//@@author yican95
/**
 * Lists all items in the menu to the user.
 */
public class ListItemsCommand extends Command {

    public static final String COMMAND_WORD = "list-items";

    public static final String COMMAND_ALIAS = "li";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
