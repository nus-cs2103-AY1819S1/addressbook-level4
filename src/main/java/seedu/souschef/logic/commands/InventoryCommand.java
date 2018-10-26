package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;
import java.util.List;

import seedu.souschef.commons.core.Messages;
import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.InventoryComparator;
import seedu.souschef.logic.InventoryPredicate;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;


/**
 * Command to perform inventory command.
 */
public class InventoryCommand extends Command {
    private static final String MESSAGE_USAGE = null;

    private final Model<Recipe> recipeModel;
    private final Model<Ingredient> ingredientModel;
    private final String userInput;

    public InventoryCommand(Model<Recipe> recipeModel, Model<Ingredient> ingredientModel, String userInput) {
        requireNonNull(recipeModel);
        requireNonNull(ingredientModel);

        this.recipeModel = recipeModel;
        this.ingredientModel = ingredientModel;
        this.userInput = userInput;
    }

    @Override
    public CommandResult execute(CommandHistory history) throws CommandException {
        String[] tokens = userInput.split("\\s+");
        double numberOfServings;
        try {
            if (tokens.length == 1) {
                numberOfServings = 1;
            } else {
                numberOfServings = Double.parseDouble(tokens[1]);
            }
        } catch (NumberFormatException ne) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        HashMap<String, Double> must = new HashMap<>();
        HashMap<String, Double> optional = new HashMap<>();
        List<Ingredient> lastShownIngredientList = ingredientModel.getFilteredList();

        int index = 2;
        if (index < tokens.length && tokens[index].equals("must")) {
            index++;
            while (index < tokens.length) {
                if (tokens[index].equals("optional")) {
                    break;
                }
                String mustKey = new IngredientDefinition(tokens[index]).toString();
                must.put(mustKey, Double.valueOf(0));
                index++;
            }
        }
        if (index < tokens.length && tokens[index].equals("optional")) {
            index++;
            while (index < tokens.length) {
                String optionalKey = new IngredientDefinition(tokens[index]).toString();
                if (must.containsKey(optionalKey)) {
                    throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
                }
                optional.put(optionalKey, Double.valueOf(0));
                index++;
            }
        }

        for (Ingredient ingredient : lastShownIngredientList) {
            String key = new IngredientDefinition(ingredient.getName(), ingredient.getUnit()).toString();
            Double amount = ingredient.getAmount().getValue();
            if (must.containsKey(key)) {
                must.replace(key, must.get(key) + amount);
            }

            if (optional.containsKey(key)) {
                optional.replace(key, optional.get(key) + amount);
            } else {
                optional.put(key, amount);
            }
        }

        List<Recipe> lastShownRecipeList = recipeModel.getFilteredList();
        HashMap<Recipe, HashMap<String, Double>> recipes = new HashMap<>();

        for (Recipe recipe : lastShownRecipeList) {
            HashMap<String, Double> ingredients = recipe.getIngredients();
            HashMap<String, Double> matchedIngredients = new HashMap<>();
            for (String key : optional.keySet()) {
                if (ingredients.containsKey(key)) {
                    matchedIngredients.put(key, optional.get(key));
                }
            }
            recipes.put(recipe, matchedIngredients);
        }

        recipeModel.sort(new InventoryComparator(recipes));
        recipeModel.updateFilteredList(new InventoryPredicate(must));

        return new CommandResult(
                String.format(Messages.MESSAGE_LISTED_OVERVIEW, recipeModel.getFilteredList().size(),
                        history.getContext().toString().toLowerCase()));
    }
}
