package seedu.parking.commons.events.ui;

import seedu.parking.commons.events.BaseEvent;
import seedu.parking.model.carpark.Carpark;

/**
 * Represents a selection change in the Car park List Panel
 */
public class CarparkPanelSelectionChangedEvent extends BaseEvent {

    private final Carpark newSelection;

    public CarparkPanelSelectionChangedEvent(Carpark newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Carpark getNewSelection() {
        return newSelection;
    }
}
