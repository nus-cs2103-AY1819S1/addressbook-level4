package seedu.souschef.commons.events.model;

import seedu.souschef.commons.events.BaseEvent;

/**
 * Event that is raised when the Meal Planner is cleared.
 */
public class MealPlannerClearedEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
