package seedu.parking.commons.events.ui;

import seedu.parking.commons.events.BaseEvent;
import seedu.parking.model.carpark.Carpark;

/**
 * Represents a selection change in the Car park List Panel after a Find command is ran.
 */
public class FindResultChangedEvent extends BaseEvent {

    private final Carpark[] returnList;

    public FindResultChangedEvent(Carpark[] newSelection) {
        this.returnList = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Carpark[] getReturnList() {
        return returnList;
    }
}
