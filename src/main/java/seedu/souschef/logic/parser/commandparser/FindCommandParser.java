package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_FIND_FAVOURITES_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_FIND_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.souschef.logic.commands.FindCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.recipe.FavouriteNameContainsKeywordsPredicate;
import seedu.souschef.model.recipe.IngredientNameContainsKeywordsPredicate;
import seedu.souschef.model.recipe.NameContainsKeywordsPredicate;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements CommandParser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand<Recipe> parseRecipe(Model model, String args) throws ParseException {
        requireNonNull(model);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_FIND_RECIPE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand<>(model, new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand<Ingredient> parseIngredient(Model<Ingredient> model, String args) throws ParseException {
        return new FindCommand<>(model,
                new IngredientNameContainsKeywordsPredicate(parse(model, args, MESSAGE_FIND_INGREDIENT_USAGE)));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand<Day> parseMealPlan(Model<Day> model, String args) throws ParseException {
        return new FindCommand<>(model,
                new MealPlanContainsDatePredicate(parse(model, args, MESSAGE_FIND_MEALPLAN_USAGE)));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an List to be passed to predicates.
     * @throws ParseException if the user input does not conform the expected format
     */
    private List<String> parse(Model model, String args, String messageUsage) throws ParseException {
        requireNonNull(model);
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }
        String[] nameKeywords = trimmedArgs.toLowerCase().split("\\s+");

        return Arrays.asList(nameKeywords);
    }
}
