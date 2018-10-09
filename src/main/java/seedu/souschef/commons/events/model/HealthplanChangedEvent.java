package seedu.souschef.commons.events.model;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.ReadOnlyAppContent;

/**
 *  model change event
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
