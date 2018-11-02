package seedu.restaurant.logic.commands.ingredient;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.Ingredient;

/**
 * Deletes an ingredient identified using its displayed index from the ingredient book.
 */
public class DeleteIngredientByIndexCommand extends DeleteIngredientCommand {

    private final Index targetIndex;

    public DeleteIngredientByIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteIngredient(ingredientToDelete);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientByIndexCommand // instanceof handles nulls
                    && (targetIndex.equals(((DeleteIngredientByIndexCommand) other).targetIndex))); // state check
    }
}
