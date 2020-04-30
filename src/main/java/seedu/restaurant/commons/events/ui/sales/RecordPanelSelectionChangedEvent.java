package seedu.restaurant.commons.events.ui.sales;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.sales.SalesRecord;

//@@author HyperionNKJ
/**
 * Represents a selection change in the Record List Panel
 */
public class RecordPanelSelectionChangedEvent extends BaseEvent {

    private final SalesRecord newSelection;

    public RecordPanelSelectionChangedEvent(SalesRecord newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    /**
     * Returns newSelection in this changed event
     */
    public SalesRecord getNewSelection() {
        return newSelection;
    }
}
