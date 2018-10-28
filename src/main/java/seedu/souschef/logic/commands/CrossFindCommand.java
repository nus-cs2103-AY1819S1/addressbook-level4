package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.souschef.commons.core.Messages;
import seedu.souschef.logic.History;
import seedu.souschef.logic.CrossFilterPredicate;
import seedu.souschef.logic.CrossSortComparator;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.CrossRecipe;


/**
 * Command to perform inventory command.
 */
public class CrossFindCommand extends Command {
    public static final String COMMAND_WORD = "recipe";

    public static final String MESSAGE_USAGE = "Usage to be added later";//

    private final Model<CrossRecipe> crossRecipeModel;
    private final CrossSortComparator comparator;
    private final CrossFilterPredicate predicate;
    private final Map<Recipe, Map<IngredientDefinition, Double>> pairs;
    private final Map<IngredientDefinition, Double> include;
    private final double numberOfServings;

    public CrossFindCommand(Model<CrossRecipe> crossRecipeModel, CrossSortComparator comparator,
                            CrossFilterPredicate predicate, Map<Recipe, Map<IngredientDefinition, Double>> pairs,
                            Map<IngredientDefinition, Double> include, double numberOfServings) {
        requireNonNull(crossRecipeModel);
        this.crossRecipeModel = crossRecipeModel;
        this.predicate = predicate;
        this.comparator = comparator;
        this.pairs = pairs;
        this.include = include;
        this.numberOfServings = numberOfServings;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {

        crossRecipeModel.sort(comparator);
        crossRecipeModel.updateFilteredList(predicate);

        List<CrossRecipe> resultList = crossRecipeModel.getFilteredList();
        for (CrossRecipe pair : resultList) {
            Recipe recipe = pair.getRecipe();
            Map<IngredientDefinition, Double> matchedIngredients = pairs.get(recipe);
            for (IngredientDefinition key : include.keySet()) {
                matchedIngredients.put(key, include.get(key));
            }

            Map<IngredientDefinition, Double> ingredients = pair.getIngredients();
            Map<IngredientDefinition, Double> neededIngredients = new HashMap<>();

            for (IngredientDefinition key : ingredients.keySet()) {
                double totalAmount = numberOfServings * ingredients.get(key);
                double neededAmount;
                if (!matchedIngredients.containsKey(key)) {
                    neededAmount = totalAmount;
                } else if (totalAmount > matchedIngredients.get(key)){
                    neededAmount = totalAmount - matchedIngredients.get(key);
                } else {
                    neededAmount = 0;
                }

                neededIngredients.put(key, neededAmount);
            }

            crossRecipeModel.update(pair, new CrossRecipe(recipe, neededIngredients));
        }

        return new CommandResult(String.format(Messages.MESSAGE_LISTED_OVERVIEW,
                resultList.size(), history.getKeyword()));
    }
}
