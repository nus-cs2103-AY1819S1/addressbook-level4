package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.RestaurantBook;

//@@author yican95
/**
 * Clears the menu.
 */
public class ClearMenuCommand extends Command {

    public static final String COMMAND_WORD = "clear-menu";
    public static final String COMMAND_ALIAS = "cm";
    public static final String MESSAGE_SUCCESS = "Menu has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetMenuData(new RestaurantBook());
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
