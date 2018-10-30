package seedu.thanepark.commons.events.model;

import seedu.thanepark.commons.events.BaseEvent;
import seedu.thanepark.model.ReadOnlyThanePark;

/** Indicates the ThanePark in the model has changed*/
public class ThaneParkChangedEvent extends BaseEvent {

    public final ReadOnlyThanePark data;

    public ThaneParkChangedEvent(ReadOnlyThanePark data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getRideList().size();
    }
}
