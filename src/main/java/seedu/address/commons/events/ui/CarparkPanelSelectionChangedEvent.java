package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.carpark.Carpark;

/**
 * Represents a selection change in the Carpark List Panel
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
