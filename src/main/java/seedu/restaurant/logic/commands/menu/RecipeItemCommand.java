package seedu.restaurant.logic.commands.menu;

import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_RECIPE;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.JumpToListRequestEvent;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Recipe;

//@@author yican95
/**
 * Adds a recipe to a item in the menu.
 */
public class RecipeItemCommand extends Command {
    public static final String COMMAND_WORD = "recipe-item";
    public static final String COMMAND_ALIAS = "ri";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Updates the recipe for a item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RECIPE + "RECIPE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RECIPE + "Boil water over high heat and reduce the heat to medium-low, "
            + "pour in the vinegar and 2 teaspoons of salt and egg.";

    public static final String MESSAGE_ADD_RECIPE_SUCCESS = "Added recipe to Item: %1$s";
    public static final String MESSAGE_DELETE_RECIPE_SUCCESS = "Removed recipe from Item: %1$s";

    private final Index index;
    private final Recipe recipe;

    /**
     * @param index of the item in the filtered item list to edit
     * @param recipe the recipe of the item
     */
    public RecipeItemCommand(Index index, Recipe recipe) {
        requireAllNonNull(index, recipe);

        this.index = index;
        this.recipe = recipe;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = new Item(itemToEdit.getName(), itemToEdit.getPrice(), recipe, itemToEdit.getTags(),
                itemToEdit.getRequiredIngredients());

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitRestaurantBook();

        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(generateSuccessMessage(editedItem));
    }
    /**
     * Generates a command execution success message based on whether the recipe is added to or removed from
     * {@code itemToEdit}.
     */
    private String generateSuccessMessage(Item editedItem) {
        String message = !recipe.toString().isEmpty() ? MESSAGE_ADD_RECIPE_SUCCESS : MESSAGE_DELETE_RECIPE_SUCCESS;
        return String.format(message, editedItem);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RecipeItemCommand)) {
            return false;
        }
        // state check
        RecipeItemCommand e = (RecipeItemCommand) other;
        return index.equals(e.index)
                && recipe.equals(e.recipe);
    }
}
