package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.meeting.Meeting;

/**
 * Represents a selection change in the {@code MeetingListPanel}
 * {@author jeffreyooi}
 */
public class MeetingPanelSelectionChangedEvent extends BaseEvent {

    private final Meeting newSelection;

    public MeetingPanelSelectionChangedEvent(Meeting newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Meeting getNewSelection() {
        return newSelection;
    }
}
