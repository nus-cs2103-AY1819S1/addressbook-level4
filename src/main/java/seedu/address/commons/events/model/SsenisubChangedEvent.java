package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlySsenisub;

/** Indicates Ssenisub in the model has changed*/
public class SsenisubChangedEvent extends BaseEvent {

    public final ReadOnlySsenisub data;

    public SsenisubChangedEvent(ReadOnlySsenisub data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
