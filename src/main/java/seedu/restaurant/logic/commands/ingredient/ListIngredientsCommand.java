package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;

/**
 * Lists all ingredients in the restaurant book to the user.
 */
public class ListIngredientsCommand extends Command {

    public static final String COMMAND_WORD = "list-ingredients";

    public static final String COMMAND_ALIAS = "list-ing";

    public static final String MESSAGE_SUCCESS = "Listed all ingredients";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
