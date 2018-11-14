package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.occasion.Occasion;

/**
 * Represents a selection change in the Occasion List Panel
 * @author xueyantian
 */
public class OccasionPanelSelectionChangedEvent extends BaseEvent {


    private final Occasion newSelection;

    public OccasionPanelSelectionChangedEvent(Occasion newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Occasion getNewSelection() {
        return newSelection;
    }
}
