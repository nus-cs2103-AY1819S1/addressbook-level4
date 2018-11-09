package seedu.restaurant.logic.commands.ingredient;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * Deletes an ingredient identified using its name from the restaurant book.
 */
public class DeleteIngredientByNameCommand extends DeleteIngredientCommand {

    private final IngredientName targetName;

    public DeleteIngredientByNameCommand(IngredientName targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            Ingredient ingredientToDelete = model.findIngredient(targetName);
            model.deleteIngredient(ingredientToDelete);
            model.commitRestaurantBook();
            EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete));
        } catch (IngredientNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientByNameCommand // instanceof handles nulls
                    && (targetName.equals(((DeleteIngredientByNameCommand) other).targetName))); // state check
    }
}
