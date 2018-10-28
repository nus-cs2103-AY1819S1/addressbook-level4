package seedu.souschef.logic.parser.commandparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.souschef.logic.CrossFilterPredicate;
import seedu.souschef.logic.CrossSortComparator;
import seedu.souschef.logic.commands.CrossFindCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.shop.CrossRecipe;

/**
 * Parses input arguments and creates a new CrossFindCommand object
 */
public class CrossFindCommandParser {


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CrossFindCommand parse(Model<CrossRecipe> crossRecipeModel, Model<Ingredient> ingredientModel,
                                  String argument) throws ParseException {
        String[] tokens = argument.trim().split("\\s+");
        double numberOfServings;
        if (tokens.length < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossFindCommand.MESSAGE_USAGE));
        }
        try {
            numberOfServings = Double.parseDouble(tokens[0]);
        } catch (NumberFormatException ne) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossFindCommand.MESSAGE_USAGE));
        }

        Map<IngredientDefinition, Double> include = new HashMap<>();
        Map<IngredientDefinition, Double> prioritize = new HashMap<>();

        ingredientModel.updateFilteredList(Model.PREDICATE_SHOW_ALL_INGREDIENTS);
        List<Ingredient> ingredientList = ingredientModel.getFilteredList();

        int index = 1;
        boolean hasInventory = false;
        if (index < tokens.length && tokens[index].equals("include")) {
            index++;
            if (tokens[index].equals("inventory")) {
                hasInventory = true;
                for (Ingredient ingredient : ingredientList) {
                    IngredientDefinition key = new IngredientDefinition(ingredient.getName().toString());
                    Double amount = ingredient.getAmount().getValue();
                    include.put(key, amount);
                }
                index++;
            }
            while (index < tokens.length) {
                if (tokens[index].equals("prioritize")) {
                    break;
                }
                IngredientDefinition key = new IngredientDefinition(tokens[index]);
                include.put(key, Double.valueOf(0));
                index++;
            }
        }

        if (index < tokens.length && tokens[index].equals("prioritize")) {
            index++;
            if (hasInventory == false && tokens[index].equals("inventory")) {
                hasInventory = true;
                for (Ingredient ingredient : ingredientList) {
                    IngredientDefinition key = new IngredientDefinition(ingredient.getName().toString());
                    Double amount = ingredient.getAmount().getValue();
                    prioritize.put(key, amount);
                }
                index++;
            }
            while (index < tokens.length) {
                IngredientDefinition key = new IngredientDefinition(tokens[index]);
                if (include.containsKey(key)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            CrossFindCommand.MESSAGE_USAGE));
                }
                prioritize.put(key, Double.valueOf(0));
                index++;
            }
        }

        if (!hasInventory) {
            for (Ingredient ingredient : ingredientList) {
                IngredientDefinition key = new IngredientDefinition(ingredient.getName().toString());
                Double amount = ingredient.getAmount().getValue();
                if (include.containsKey(key)) {
                    include.replace(key, amount);
                }
                if (prioritize.containsKey(key)) {
                    prioritize.replace(key, amount);
                }
            }
        }

        crossRecipeModel.updateFilteredList(Model.PREDICATE_SHOW_ALL_CROSSRECIPES);
        List<CrossRecipe> crossRecipeList = crossRecipeModel.getFilteredList();

        Map<Recipe, Map<IngredientDefinition, Double>> pairs = new HashMap<>();

        for (CrossRecipe pair : crossRecipeList) {
            Recipe recipe = pair.getRecipe();
            Map<IngredientDefinition, Double> ingredients = pair.getIngredients();
            Map<IngredientDefinition, Double> matchedIngredients = new HashMap<>();

            for (IngredientDefinition key : prioritize.keySet()) {
                if (ingredients.containsKey(key)) {
                    matchedIngredients.put(key, prioritize.get(key));
                }
            }
            pairs.put(recipe, matchedIngredients);
        }

        return new CrossFindCommand(crossRecipeModel, new CrossSortComparator(pairs),
                new CrossFilterPredicate(include), pairs, include, numberOfServings);
    }
}
