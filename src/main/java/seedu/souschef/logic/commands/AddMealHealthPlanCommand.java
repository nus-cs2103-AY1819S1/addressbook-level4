package seedu.souschef.logic.commands;

import java.util.logging.Logger;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.BrowserUiChangedEvent;
import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.planner.Day;

/**
 * command to add indexed day into index healthplan
 */
public class AddMealHealthPlanCommand <T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "addDay";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the day identified by the index number used in the "
            + "displayed meal plan list to the specified health plan\n"
            + "Parameters: p/PLANINDEX d/DAYINDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " p/1 d/1";

    public static final String MESSAGE_SUCCESS = "Added day %2$s to plan %1$s";

    private static final Logger logger = LogsCenter.getLogger(AddMealHealthPlanCommand.class);

    private HealthPlan planToAddTo;
    private Day dayToAdd;
    private Model<HealthPlan> model;

    private int dayIndex;
    private int planIndex;

    public AddMealHealthPlanCommand(Model healthPlanModel, HealthPlan planToAddTo, Day dayToAdd, int dayIndex,
                                    int planIndex) {
        this.model = healthPlanModel;

        this.planToAddTo = planToAddTo;

        this.dayIndex = dayIndex;

        this.dayToAdd = dayToAdd;

        this.planIndex = planIndex;
    }

    @Override
    public CommandResult execute(History history) {


        this.planToAddTo.getMealPlans().add(dayToAdd);

        model.update(planToAddTo, planToAddTo);

        model.commitAppContent();

        EventsCenter.getInstance().post(new BrowserUiChangedEvent("healthplanDetails", this.planIndex));

        return new CommandResult(String.format(MESSAGE_SUCCESS, planIndex,
                dayIndex));
    }




}
