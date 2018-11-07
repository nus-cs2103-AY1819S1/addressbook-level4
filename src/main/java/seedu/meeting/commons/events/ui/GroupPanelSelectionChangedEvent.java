package seedu.meeting.commons.events.ui;

import seedu.meeting.commons.events.BaseEvent;
import seedu.meeting.model.group.Group;

/**
 * Represents a selection change in the Group List Panel
 * {@author jeffreyooi}
 */
public class GroupPanelSelectionChangedEvent extends BaseEvent {

    private final Group newSelection;

    public GroupPanelSelectionChangedEvent(Group newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Group getNewSelection() {
        return newSelection;
    }
}
