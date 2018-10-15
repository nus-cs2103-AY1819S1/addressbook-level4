package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.Model;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.Recipe;

/**
 *
 */
public class PlanMealCommand extends Command {
    public static final String COMMAND_WORD = "plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds the recipe identified by the index number used in the "
        + "displayed recipe list to the specified date in the Meal Planner.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1 2018-08-08 lunch";

    public static final String MESSAGE_PLAN_RECIPE_SUCCESS = "Planned Recipe: %1$s";

    private final Model mealPlannerModel;
    private final Recipe toAdd;
    private final Day toPlan;
    private final Meal meal;

    public PlanMealCommand(Model mealPlannerModel, Recipe toAdd, Day toPlan, Meal meal) {
        this.mealPlannerModel = mealPlannerModel;
        this.toAdd = toAdd;
        this.toPlan = toPlan;
        this.meal = meal;
    }

    @Override
    public CommandResult execute(CommandHistory history) {
        toPlan.setMealRecipe(meal, toAdd);
        mealPlannerModel.commitAppContent();
        return new CommandResult(String.format(MESSAGE_PLAN_RECIPE_SUCCESS, toAdd));
    }
}
