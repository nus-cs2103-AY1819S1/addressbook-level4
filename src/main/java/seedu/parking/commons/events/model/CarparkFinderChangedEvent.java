package seedu.parking.commons.events.model;

import seedu.parking.commons.events.BaseEvent;
import seedu.parking.model.ReadOnlyCarparkFinder;

/** Indicates the CarparkFinder in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyCarparkFinder data;

    public AddressBookChangedEvent(ReadOnlyCarparkFinder data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of carparks " + data.getCarparkList().size();
    }
}
