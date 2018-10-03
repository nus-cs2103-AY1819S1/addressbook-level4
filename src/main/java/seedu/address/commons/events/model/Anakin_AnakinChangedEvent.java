package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Anakin_ReadOnlyAnakin;

/** Indicates Anakin in the model has changed*/
public class Anakin_AnakinChangedEvent extends BaseEvent {

    public final Anakin_ReadOnlyAnakin data;

    public Anakin_AnakinChangedEvent(Anakin_ReadOnlyAnakin data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of decks " + data.getDeckList().size();
    }
}
