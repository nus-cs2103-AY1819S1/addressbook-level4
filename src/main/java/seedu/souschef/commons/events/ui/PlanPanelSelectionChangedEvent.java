package seedu.souschef.commons.events.ui;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.healthplan.HealthPlan;


/**
 *  UI event when plan is changed
 */
public class PlanPanelSelectionChangedEvent extends BaseEvent {

    private final HealthPlan newSelection;

    public PlanPanelSelectionChangedEvent(HealthPlan newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public HealthPlan getNewSelection() {
        return newSelection;
    }


}
