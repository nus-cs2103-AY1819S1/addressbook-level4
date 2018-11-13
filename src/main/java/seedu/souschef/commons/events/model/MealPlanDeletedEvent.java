package seedu.souschef.commons.events.model;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.model.planner.Day;

/**
 * Event that is raised whenever a Day object is deleted from the mealPlannerModel. Contains the day which is deleted.
 */
public class MealPlanDeletedEvent extends BaseEvent {

    public final Day day;
    public final Context context;

    public MealPlanDeletedEvent(Day day, Context context) {
        this.day = day;
        this.context = context;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + day.getDate().toString() + "]";
    }
}
