package seedu.souschef.logic.parser.commandparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.model.ingredient.IngredientName.MESSAGE_NAME_CONSTRAINTS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.souschef.logic.CrossFilterPredicate;
import seedu.souschef.logic.commands.CrossFindCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.ingredient.IngredientName;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses input arguments and creates a new CrossFindCommand object
 */
public class CrossFindCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the CrossFindCommand
     * and returns an CrossFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private final String INCLUDE = "include";
    private final String PRIORITIZE = "prioritize";
    private final String INVENTORY = "inventory";
    
    public CrossFindCommand parse(Model<CrossRecipe> crossRecipeModel, Model<Ingredient> ingredientModel,
                                  String argument) throws ParseException {
        String[] tokens = argument.trim().toLowerCase().split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replaceAll("_", " ");
        }

        if (tokens.length < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossFindCommand.MESSAGE_USAGE));
        }

        double numberOfServings;
        try {
            numberOfServings = Double.parseDouble(tokens[0]);
        } catch (NumberFormatException ne) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CrossFindCommand.MESSAGE_USAGE));
        }

        List<IngredientDefinition> include = new ArrayList<>();
        List<IngredientDefinition> prioritize = new ArrayList<>();

        ingredientModel.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<Ingredient> ingredientList = ingredientModel.getFilteredList();

        int index = 1;
        boolean hasInventory = false;
        if (index < tokens.length && tokens[index].equals(INCLUDE)) {
            index++;
            if (tokens[index].equals(INVENTORY)) {
                hasInventory = true;
                for (Ingredient ingredient : ingredientList) {
                    include.add(new IngredientDefinition(ingredient.getName()));
                }
                index++;
            }
            while (index < tokens.length) {
                if (tokens[index].equals(PRIORITIZE)) {
                    break;
                }
                if (!IngredientName.isValid(tokens[index])) {
                    throw new ParseException(MESSAGE_NAME_CONSTRAINTS);
                }
                include.add(new IngredientDefinition(tokens[index]));
                index++;
            }
        }

        if (index < tokens.length && tokens[index].equals(PRIORITIZE)) {
            index++;
            if (hasInventory == false && tokens[index].equals(INVENTORY)) {
                for (Ingredient ingredient : ingredientList) {
                    prioritize.add(new IngredientDefinition(ingredient.getName()));
                }
                index++;
            }
            while (index < tokens.length) {
                if (!IngredientName.isValid(tokens[index])) {
                    throw new ParseException(MESSAGE_NAME_CONSTRAINTS);
                }
                IngredientDefinition key = new IngredientDefinition(tokens[index]);
                if (include.contains(key)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            CrossFindCommand.MESSAGE_USAGE));
                }
                prioritize.add(key);
                index++;
            }
        }

        crossRecipeModel.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        List<CrossRecipe> crossRecipeList = crossRecipeModel.getFilteredList();

        Map<Recipe, List<IngredientDefinition>> matchedCrossRecipeMap = new HashMap<>();

        for (CrossRecipe crossRecipe : crossRecipeList) {
            Recipe recipe = crossRecipe.getRecipe();
            Map<IngredientDefinition, IngredientPortion> ingredients = crossRecipe.getIngredients();
            List<IngredientDefinition> matchedIngredients = new ArrayList<>();

            for (IngredientDefinition key : prioritize) {
                if (ingredients.containsKey(key)) {
                    matchedIngredients.add(key);
                }
            }
            matchedCrossRecipeMap.put(recipe, matchedIngredients);
        }

        return new CrossFindCommand(crossRecipeModel, ingredientModel, new CrossFilterPredicate(include),
                matchedCrossRecipeMap, numberOfServings);
    }
}
