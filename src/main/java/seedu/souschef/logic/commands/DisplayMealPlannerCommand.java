package seedu.souschef.logic.commands;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.ui.Ui;

/**
 *
 */
public class DisplayMealPlannerCommand extends Command {
    public static final String COMMAND_WORD = "display";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Displays the current plans in the Meal Planner.";

    public static final String MESSAGE_DISPLAY_PLANNER_SUCCESS = "Current Meal Plans";

    private final Ui ui;

    public DisplayMealPlannerCommand(Ui ui) {
        this.ui = ui;
    }

    @Override
    public CommandResult execute(CommandHistory history) {
        ui.switchToMealPlanListPanel();
        return new CommandResult(String.format(MESSAGE_DISPLAY_PLANNER_SUCCESS));
    }
}
