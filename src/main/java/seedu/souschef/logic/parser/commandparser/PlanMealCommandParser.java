package seedu.souschef.logic.parser.commandparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.List;

import seedu.souschef.logic.commands.PlanMealCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.Recipe;



/**
 *
 */
public class PlanMealCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the PlanMealCommand
     * and returns an PlanMealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlanMealCommand parsePlan(Model mealPlannerModel, Model recipeModel, String args) throws ParseException {
        try {
            String[] arguments = args.split("\\s+");

            if (arguments.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanMealCommand.MESSAGE_USAGE));
            }

            List<Recipe> recipeList = recipeModel.getFilteredList();
            List<Day> mealPlannerList = mealPlannerModel.getFilteredList();
            int listIndex = Integer.parseInt(arguments[0]) - 1;

            if ((listIndex < 0) || listIndex >= recipeList.size()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanMealCommand.MESSAGE_USAGE));
            }

            Recipe toAdd = recipeList.get(listIndex);
            LocalDate date = LocalDate.parse(arguments[1]);
            if (date.isBefore(LocalDate.now())) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanMealCommand.MESSAGE_USAGE));
            }

            Day toPlan;
            Day newDay = new Day(date);
            boolean dayIsPresent;

            if (mealPlannerList.contains(newDay)) {
                toPlan = mealPlannerList.get(mealPlannerList.indexOf(newDay));
                dayIsPresent = true;
            } else {
                toPlan = newDay;
                dayIsPresent = false;
            }
            Meal meal = toPlan.getMeal(arguments[2]);

            return new PlanMealCommand(mealPlannerModel, toAdd, meal, toPlan, dayIsPresent);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanMealCommand.MESSAGE_USAGE), pe);
        }
    }
}
