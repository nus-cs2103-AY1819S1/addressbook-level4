package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.logic.parser.Context.CROSS;
import static seedu.souschef.logic.parser.Context.INGREDIENT;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import java.util.List;

import seedu.souschef.logic.History;
import seedu.souschef.logic.IngredientDateComparator;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * Lists all recipes in the address book to the user.
 */
public class ListCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s.";

    private final Model model;

    public ListCommand(Model model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) {
        requireNonNull(model);
        Context context = history.getContext();
        if (context.equals(INGREDIENT)) {
            model.sort(new IngredientDateComparator());
        } else if (context.equals(CROSS)) {
            model.updateFilteredList(Model.PREDICATE_SHOW_ALL_CROSSRECIPES);
            List<CrossRecipe> crossRecipeList = model.getFilteredList();
            for (CrossRecipe crossRecipe : crossRecipeList) {
                Recipe recipe = crossRecipe.getRecipe();
                model.update(crossRecipe, new CrossRecipe(recipe, recipe.getIngredients()));
            }
        }
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(String.format(MESSAGE_SUCCESS, history.getKeyword()));
    }
}
