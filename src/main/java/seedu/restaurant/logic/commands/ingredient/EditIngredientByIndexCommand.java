package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

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
 * Edits the details of an existing ingredient in the restaurant book.
 */
public class EditIngredientByIndexCommand extends EditIngredientCommand {

    private final Index targetIndex;
    private final EditIngredientDescriptor editIngredientDescriptor;

    /**
     * @param index of the ingredient in the filtered ingredient list to edit
     * @param editIngredientDescriptor details to edit the ingredient with
     */
    public EditIngredientByIndexCommand(Index index, EditIngredientDescriptor editIngredientDescriptor) {
        requireNonNull(index);
        requireNonNull(editIngredientDescriptor);

        this.targetIndex = index;
        this.editIngredientDescriptor = new EditIngredientDescriptor(editIngredientDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToEdit = lastShownList.get(targetIndex.getZeroBased());
        Ingredient editedIngredient = createEditedIngredient(ingredientToEdit, editIngredientDescriptor);

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
        if (!(other instanceof EditIngredientByIndexCommand)) {
            return false;
        }

        // state check
        EditIngredientByIndexCommand e = (EditIngredientByIndexCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editIngredientDescriptor.equals(e.editIngredientDescriptor);
    }

}
