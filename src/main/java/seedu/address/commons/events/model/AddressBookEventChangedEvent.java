package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

/** Indicates the list of events in the AddressBook in the model has changed*/
public class AddressBookEventChangedEvent extends BaseEvent {

    public final ObservableList<Event> data;

    public AddressBookEventChangedEvent(ObservableList<Event> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.size();
    }
}
