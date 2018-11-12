package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyHealthBook;

/** Indicates the HealthBook in the model has changed*/
public class HealthBookChangedEvent extends BaseEvent {

    public final ReadOnlyHealthBook data;

    public HealthBookChangedEvent(ReadOnlyHealthBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
