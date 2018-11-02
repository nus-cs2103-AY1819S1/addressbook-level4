package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_DELETE_FAVOURITE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_DELETE_HEALTHPLAN_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_DELETE_INGREDIENT_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_DELETE_MEALPLANNER_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_DELETE_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.souschef.commons.core.index.Index;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.parser.ParserUtil;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements CommandParser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand<Recipe> parseRecipe(Model model, String args) throws ParseException {
        try {
            Index targetIndex = ParserUtil.parseIndex(args);
            requireNonNull(model);
            List<Recipe> lastShownList = model.getFilteredList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_RECIPE_USAGE));
            }
            Recipe toDelete = lastShownList.get(targetIndex.getZeroBased());

            return new DeleteCommand<>(model, toDelete);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_RECIPE_USAGE), pe);
        }
    }

    @Override
    public DeleteCommand<Ingredient> parseIngredient(Model model, String args) throws ParseException {
        requireNonNull(model);
        requireNonNull(args);

        List<Ingredient> lastShownList = model.getFilteredList();
        String[] tokens = args.trim().split("\\s+");
        int index;
        try {
            index = Integer.parseInt(tokens[0]) - 1;
            if (index >= lastShownList.size() || tokens.length % 2 == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_DELETE_INGREDIENT_USAGE));
            }
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_DELETE_INGREDIENT_USAGE));
        }

        Ingredient toDelete = lastShownList.get(index);

        return new DeleteCommand<>(model, toDelete);
    }


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand<HealthPlan> parseHealthPlan(Model model, String args) throws ParseException {
        try {
            Index targetIndex = ParserUtil.parseIndex(args);
            requireNonNull(model);
            List<HealthPlan> lastShownList = model.getFilteredList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_DELETE_HEALTHPLAN_USAGE));
            }
            HealthPlan toDelete = lastShownList.get(targetIndex.getZeroBased());

            return new DeleteCommand<>(model, toDelete);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_HEALTHPLAN_USAGE), pe);
        }
    }

    /**
     *
     * @param model favouriteModel
     * @param args userInput
     * @return DeleteCommand
     * @throws ParseException
     */
    public DeleteCommand<Favourites> parseFavourites(Model model, String args) throws ParseException {
        try {
            Index targetIndex = ParserUtil.parseIndex(args);
            requireNonNull(model);
            List<Favourites> lastShownList = model.getFilteredList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_FAVOURITE_USAGE));
            }
            Favourites toDelete = lastShownList.get(targetIndex.getZeroBased());

            return new DeleteCommand<>(model, toDelete);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_FAVOURITE_USAGE), pe);
        }
    }

    /**
     * Deletes a specified Day from the Meal Planner.
     * @param model mealPlannerModel
     * @param args userInput
     * @return DeleteCommand
     * @throws ParseException
     */
    public DeleteCommand<Day> parseMealPlan(Model model, String args) throws ParseException {
        try {
            Index targetIndex = ParserUtil.parseIndex(args);
            requireNonNull(model);
            List<Day> lastShownList = model.getFilteredList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_DELETE_MEALPLANNER_USAGE));
            }
            Day toDelete = lastShownList.get(targetIndex.getZeroBased());

            return new DeleteCommand<>(model, toDelete);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_MEALPLANNER_USAGE), pe);
        }
    }
}
