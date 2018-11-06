package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents an attempt to make a display change in the Event List Panel under Tab Panel, even if there is no
 * visible difference in the displayed event information in the Event List Panel.
 */
public class EventPanelDisplayChangedEvent extends BaseEvent {

    public EventPanelDisplayChangedEvent() {}

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
