package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

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
 * Edits the details of an existing ingredient in the restaurant book.
 */
public class EditIngredientByNameCommand extends EditIngredientCommand {

    private final IngredientName targetName;
    private final EditIngredientDescriptor editIngredientDescriptor;

    /**
     * @param name of the ingredient in the filtered ingredient list to edit
     * @param editIngredientDescriptor details to edit the ingredient with
     */
    public EditIngredientByNameCommand(IngredientName name, EditIngredientDescriptor editIngredientDescriptor) {
        requireNonNull(name);
        requireNonNull(editIngredientDescriptor);

        this.targetName = name;
        this.editIngredientDescriptor = new EditIngredientDescriptor(editIngredientDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Ingredient ingredientToEdit;
        Ingredient editedIngredient;

        try {
            ingredientToEdit = model.findIngredient(targetName);
            editedIngredient = createEditedIngredient(ingredientToEdit, editIngredientDescriptor);
        } catch (IngredientNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
        }

        if (!ingredientToEdit.isSameIngredient(editedIngredient) && model.hasIngredient(editedIngredient)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.updateIngredient(ingredientToEdit, editedIngredient);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditIngredientByNameCommand)) {
            return false;
        }

        // state check
        EditIngredientByNameCommand e = (EditIngredientByNameCommand) other;
        return targetName.equals(e.targetName)
                && editIngredientDescriptor.equals(e.editIngredientDescriptor);
    }

}
