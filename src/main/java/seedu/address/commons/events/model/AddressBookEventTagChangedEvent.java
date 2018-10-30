package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

/** Indicates the list of event tags in the AddressBook in the model has changed*/
public class AddressBookEventTagChangedEvent extends BaseEvent {

    public final ObservableList<Tag> data;

    public AddressBookEventTagChangedEvent(ObservableList<Tag> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of event tags " + data.size();
    }
}
