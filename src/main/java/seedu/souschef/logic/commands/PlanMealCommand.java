package seedu.souschef.logic.commands;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.DayComparator;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.Recipe;

/**
 * Adds a recipe to a specified meal slot of a specified day of the meal planner.
 */
public class PlanMealCommand extends Command {
    public static final String COMMAND_WORD = "plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds the recipe identified by the index number used in the "
        + "displayed recipe list to the specified date and meal slot in the Meal Planner.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "            DATE (must be ranging from the present or future)\n"
        + "            MEAL (must be either breakfast, lunch or dinner)\n"
        + "Example: " + COMMAND_WORD + " 1 2019-08-08 lunch";

    public static final String MESSAGE_PLAN_RECIPE_SUCCESS = "Meal Plan successfully added.\n"
                                                           + "Recipe: %1$s\n"
                                                           + "Date/Meal: %2$s, %3$s";

    private final Model<Day> model;
    private final Recipe toAdd;
    private final Meal meal;
    private final Day toPlan;
    private final boolean dayIsPresent;

    public PlanMealCommand(Model<Day> model, Recipe toAdd, Meal meal, Day toPlan, boolean dayIsPresent) {
        this.model = model;
        this.toAdd = toAdd;
        this.meal = meal;
        this.toPlan = toPlan;
        this.dayIsPresent = dayIsPresent;
    }

    @Override
    public CommandResult execute(History history) {
        meal.setRecipe(toAdd);
        if (dayIsPresent) {
            model.update(toPlan, toPlan);
        } else {
            model.add(toPlan);
        }
        model.sort(new DayComparator());
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_PLAN_RECIPE_SUCCESS,
            toAdd.getName(), toPlan.getDate(), meal.getSlot()));
    }
}
