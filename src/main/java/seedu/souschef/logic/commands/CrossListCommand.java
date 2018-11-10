package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * Restore original recipe list in cross context.
 */
public class CrossListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all recipes.";

    private final Model model;

    public CrossListCommand(Model model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) {
        requireNonNull(model);
        model.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<CrossRecipe> crossRecipeList = model.getFilteredList();
        for (CrossRecipe crossRecipe : crossRecipeList) {
            Recipe recipe = crossRecipe.getRecipe();
            model.update(crossRecipe, new CrossRecipe(recipe, recipe.getIngredients()));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
