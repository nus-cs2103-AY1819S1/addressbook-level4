package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.healthplan.HealthPlan;



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
