package seedu.souschef.logic.commands;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.planner.Day;

/**
 * Clears the meal planner.
 */
public class ClearMealPlannerCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Clears the %1$s.";

    public static final String MESSAGE_CLEAR_PLANNER_SUCCESS = "Meal Planner cleared.";

    private final Model mealPlanner;

    public ClearMealPlannerCommand(Model<Day> mealPlanner) {
        this.mealPlanner = mealPlanner;
    }

    @Override
    public CommandResult execute(History history) {
        this.mealPlanner.resetList();
        mealPlanner.commitAppContent();
        return new CommandResult(String.format(MESSAGE_CLEAR_PLANNER_SUCCESS));
    }
}
