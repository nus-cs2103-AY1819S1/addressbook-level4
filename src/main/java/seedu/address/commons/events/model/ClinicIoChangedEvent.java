package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the ClinicIO in the model has changed*/
public class ClinicIoChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public ClinicIoChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
