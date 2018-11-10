package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.souschef.commons.core.Messages;
import seedu.souschef.logic.CrossFilterPredicate;
import seedu.souschef.logic.CrossSortComparator;
import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;


/**
 * Command to perform inventory command.
 */
public class CrossFindCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = "view NUMBER_OF_SERVINGS include [inventory] KEYWORD... "
            + "prioritize [inventory] KEYWORD...\n"
            + "Example: " + COMMAND_WORD + " 4 include beef egg prioritize inventory cheese";

    private final Model<CrossRecipe> crossRecipeModel;
    private final Model<Ingredient> ingredientModel;
    private final CrossSortComparator comparator;
    private final CrossFilterPredicate predicate;
    private final Map<Recipe, List<IngredientDefinition>> matchedCrossRecipeMap;
    private final double numberOfServings;

    public CrossFindCommand(Model<CrossRecipe> crossRecipeModel, Model<Ingredient> ingredientModel,
                            CrossSortComparator comparator,
                            CrossFilterPredicate predicate,
                            Map<Recipe, List<IngredientDefinition>> matchedCrossRecipeMap, double numberOfServings) {
        requireNonNull(crossRecipeModel);
        this.crossRecipeModel = crossRecipeModel;
        this.ingredientModel = ingredientModel;
        this.predicate = predicate;
        this.comparator = comparator;
        this.matchedCrossRecipeMap = matchedCrossRecipeMap;
        this.numberOfServings = numberOfServings;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {

        crossRecipeModel.sort(comparator);
        crossRecipeModel.updateFilteredList(predicate);

        List<CrossRecipe> resultList = crossRecipeModel.getFilteredList();
        for (CrossRecipe crossRecipe : resultList) {
            Recipe recipe = crossRecipe.getRecipe();
            Map<IngredientDefinition, IngredientPortion> ingredients = recipe.getIngredients();
            Map<IngredientDefinition, IngredientPortion> resultIngredients = new HashMap<>();
            List<Ingredient> inventory = ingredientModel.getFilteredList();

            for (IngredientDefinition key : ingredients.keySet()) {
                resultIngredients.put(key, ingredients.get(key).multiplyAmount(numberOfServings));
            }

            for (Ingredient inventoryIngredient : inventory) {
                IngredientDefinition key = new IngredientDefinition(inventoryIngredient.getName());
                if (ingredients.containsKey(key)) {
                    resultIngredients.replace(key, resultIngredients.get(key).subtractAmount(inventoryIngredient));
                }
            }

            crossRecipeModel.update(crossRecipe, new CrossRecipe(recipe, resultIngredients));
        }

        return new CommandResult(String.format(Messages.MESSAGE_LISTED_OVERVIEW,
                resultList.size(), history.getContextString()));
    }
}
