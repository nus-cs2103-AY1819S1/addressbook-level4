package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyArchiveList;

/** Indicates the AddressBook in the model has changed*/
public class ArchivedListChangedEvent extends BaseEvent {

    public final ReadOnlyArchiveList data;

    public ArchivedListChangedEvent(ReadOnlyArchiveList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
