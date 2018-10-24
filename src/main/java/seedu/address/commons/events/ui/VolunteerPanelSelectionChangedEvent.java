package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.volunteer.Volunteer;

/**
 * Represents a selection change in the Volunteer List Panel
 */
public class VolunteerPanelSelectionChangedEvent extends BaseEvent {


    private final Volunteer newSelection;

    public VolunteerPanelSelectionChangedEvent(Volunteer newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Volunteer getNewSelection() {
        return newSelection;
    }
}
