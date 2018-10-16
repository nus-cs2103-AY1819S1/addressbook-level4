package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTriviaBundle;

/** Indicates the AddressBook in the model has changed*/
public class TriviaBundleChangedEvent extends BaseEvent {
    public final ReadOnlyTriviaBundle data;

    public TriviaBundleChangedEvent(ReadOnlyTriviaBundle data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of cards " + data.getCardList().size();
    }
}
