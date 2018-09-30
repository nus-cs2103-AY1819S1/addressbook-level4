package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAppContent;

/**
 * Event when health plan is changed
 */
public class HealthplanChangedEvent extends BaseEvent {

    public final ReadOnlyAppContent data;

    public HealthplanChangedEvent(ReadOnlyAppContent data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of plans " + data.getHealthPlanList().size();
    }



}
