package seedu.souschef.logic.parser.commandparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.souschef.commons.core.index.Index;
import seedu.souschef.logic.commands.PlanMealCommand;
import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.parser.ParserUtil;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.planner.Breakfast;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Dinner;
import seedu.souschef.model.planner.Lunch;
import seedu.souschef.model.recipe.Recipe;


/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parseIndex(Model model, String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCommand(model, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * for the Meal Planner and returns a SelectCommand object for execution.
     * @throws ParseException
     */
    public SelectCommand parseMealPlan(Model mealPlannerModel,
                                       Model recipeModel, String args) throws ParseException {

        String[] arguments = args.split("\\s+");

        if (arguments.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectCommand.MEALPLANNER_MESSAGE_USAGE));
        }

        if (!arguments[1].equalsIgnoreCase(Breakfast.SLOT)
            && !arguments[1].equalsIgnoreCase(Lunch.SLOT)
            && !arguments[1].equalsIgnoreCase(Dinner.SLOT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanMealCommand.MESSAGE_USAGE));
        }

        List<Recipe> recipeList = recipeModel.getFilteredList();
        List<Day> mealPlannerList = mealPlannerModel.getFilteredList();
        int mealPlanListIndex = Integer.parseInt(arguments[0]) - 1;

        if ((mealPlanListIndex < 0) || mealPlanListIndex >= mealPlannerList.size()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectCommand.MEALPLANNER_MESSAGE_USAGE));
        }

        if (mealPlannerList.get(mealPlanListIndex).getMeal(arguments[1]).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectCommand.MEALPLANNER_MESSAGE_USAGE));
        }

        Recipe targetRecipe = mealPlannerList.get(mealPlanListIndex).getMeal(arguments[1]).getRecipe();
        Integer recipeListIndex = recipeList.indexOf(targetRecipe) + 1;
        Index index = ParserUtil.parseIndex(recipeListIndex.toString());

        return new SelectCommand(recipeModel, index);
    }
}
