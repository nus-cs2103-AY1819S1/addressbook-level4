package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.Ingredient;

/**
 * Lists all ingredients in the restaurant book to the user.
 */
public class LowStockCommand extends Command {

    public static final String COMMAND_WORD = "low-stock";

    public static final String COMMAND_ALIAS = "ls";

    public static final String MESSAGE_SUCCESS = "Listed all ingredients that are low in stock count";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Predicate<Ingredient> predicate = ingredient ->
                ingredient.getNumUnits().getNumberOfUnits() < ingredient.getMinimum().getMinimumUnit();
        model.updateFilteredIngredientList(predicate);
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
