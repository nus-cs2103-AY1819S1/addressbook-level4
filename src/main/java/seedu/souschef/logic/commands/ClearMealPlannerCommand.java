package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.Model;
import seedu.souschef.model.planner.Day;

/**
 * Clears the meal planner.
 */
public class ClearMealPlannerCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Clears the Meal Planner.";

    public static final String MESSAGE_CLEAR_PLANNER_SUCCESS = "Meal Planner cleared.";

    private final Model mealPlanner;

    public ClearMealPlannerCommand(Model<Day> mealPlanner) {
        this.mealPlanner = mealPlanner;
    }

    @Override
    public CommandResult execute(CommandHistory history) {
        this.mealPlanner.getFullList().remove(0, mealPlanner.getFullList().size() - 1);
        return new CommandResult(String.format(MESSAGE_CLEAR_PLANNER_SUCCESS));
    }
}
