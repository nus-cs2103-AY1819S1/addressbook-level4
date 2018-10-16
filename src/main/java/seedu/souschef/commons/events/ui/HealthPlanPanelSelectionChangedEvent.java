package seedu.souschef.commons.events.ui;

import seedu.souschef.model.healthplan.HealthPlan;

/**
 *  handle the selection change event for health plan
 */
public class HealthPlanPanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<HealthPlan> {
    public HealthPlanPanelSelectionChangedEvent(HealthPlan newSelection) {
        super(newSelection);
    }
}
