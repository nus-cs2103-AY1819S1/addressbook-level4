package seedu.parking.commons.events.model;

import seedu.parking.commons.events.BaseEvent;
import seedu.parking.model.ReadOnlyCarparkFinder;

/**
 * Indicates the CarparkFinder in the model has changed
 */
public class CarparkFinderChangedEvent extends BaseEvent {

    public final ReadOnlyCarparkFinder data;

    public CarparkFinderChangedEvent(ReadOnlyCarparkFinder data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of car parks " + data.getCarparkList().size();
    }
}
