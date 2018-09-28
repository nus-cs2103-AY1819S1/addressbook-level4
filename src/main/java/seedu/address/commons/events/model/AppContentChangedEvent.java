package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAppContent;

/** Indicates the AppContent in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAppContent data;

    public AddressBookChangedEvent(ReadOnlyAppContent data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of recipes " + data.getRecipeList().size();
    }
}
