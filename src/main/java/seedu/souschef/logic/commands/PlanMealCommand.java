package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.Recipe;

/**
 * Adds a recipe to a specified meal slot of a specified day of the meal planner.
 */
public class PlanMealCommand extends Command {
    public static final String COMMAND_WORD = "plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds the recipe identified by the index number used in the "
        + "displayed recipe list to the specified date in the Meal Planner.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1 2018-08-08 lunch";

    public static final String MESSAGE_PLAN_RECIPE_SUCCESS = "Planned Recipe: %1$s";

    private final Recipe toAdd;
    private final Meal meal;

    public PlanMealCommand(Recipe toAdd, Meal meal) {
        this.toAdd = toAdd;
        this.meal = meal;
    }

    @Override
    public CommandResult execute(CommandHistory history) {
        meal.setRecipe(toAdd);
        return new CommandResult(String.format(MESSAGE_PLAN_RECIPE_SUCCESS, toAdd));
    }
}
