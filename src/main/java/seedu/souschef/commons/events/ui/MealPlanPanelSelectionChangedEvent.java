package seedu.souschef.commons.events.ui;

import seedu.souschef.model.planner.Day;

/**
 * Represents a selection change in the Recipe List Panel
 */
public class MealPlanPanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<Day> {

    public MealPlanPanelSelectionChangedEvent(Day newSelection) {
        super(newSelection);
    }
}
