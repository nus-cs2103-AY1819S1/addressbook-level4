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
 * Sorts and lists all items in menu either by name or price.
 */
public class SortMenuCommand extends Command {

    public static final String COMMAND_WORD = "sort-menu";

    public static final String COMMAND_ALIAS = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all items by name in alphabetical order or "
            + "by price in ascending order "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: SORTING METHOD(name or price)\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORTED = "Menu has been sorted by %1$s";

    /**
     * Sorting Method that can be used
     */
    public enum SortMethod {
        NAME, PRICE
    }

    private final SortMethod sortMethod;

    public SortMenuCommand(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortMenu(sortMethod);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SORTED, sortMethod.name()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortMenuCommand // instanceof handles nulls
                    && sortMethod.equals(((SortMenuCommand) other).sortMethod)); // state check
    }
}
