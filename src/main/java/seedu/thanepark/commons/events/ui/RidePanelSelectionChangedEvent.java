package seedu.thanepark.commons.events.ui;

import seedu.thanepark.commons.events.BaseEvent;
import seedu.thanepark.model.ride.Ride;

/**
 * Represents a selection change in the Ride List Panel
 */
public class RidePanelSelectionChangedEvent extends BaseEvent {


    private final Ride newSelection;

    public RidePanelSelectionChangedEvent(Ride newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Ride getNewSelection() {
        return newSelection;
    }
}
