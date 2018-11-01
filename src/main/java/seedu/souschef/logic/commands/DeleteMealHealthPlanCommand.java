package seedu.souschef.logic.commands;

import java.util.logging.Logger;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.BrowserUiChangedEvent;
import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.healthplan.HealthPlan;

/**
 * command extension to delete meal plans from health plan
 */
public class DeleteMealHealthPlanCommand <T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "deleteDay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the day identified by the index number used in the "
            + "displayed meal plan list to the specified health plan\n"
            + "Parameters: p/PLANINDEX d/DAYINDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " p/1 d/1";

    public static final String MESSAGE_SUCCESS = "Removed day %2$s from plan %1$s";

    private static final Logger logger = LogsCenter.getLogger(AddMealHealthPlanCommand.class);

    private HealthPlan plan;

    private Model<HealthPlan> model;

    private int planIndex;

    private int dayIndex;

    public DeleteMealHealthPlanCommand(Model healthPlanModel, HealthPlan plan, int planIndex, int dayIndex) {
        this.model = healthPlanModel;

        this.planIndex = planIndex;

        this.dayIndex = dayIndex;

        this.plan = plan;
    }

    @Override
    public CommandResult execute(History history) {

        plan.getMealPlans().remove(dayIndex - 1);
        model.update(plan, plan);
        model.commitAppContent();

        EventsCenter.getInstance().post(new BrowserUiChangedEvent("healthplanDetails", planIndex));

        return new CommandResult(String.format(MESSAGE_SUCCESS, planIndex,
                dayIndex));
    }

}
