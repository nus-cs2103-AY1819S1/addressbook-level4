package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Tab Panel using the toggle command.
 */
public class TabPanelSelectionChangedEvent extends BaseEvent {

    public TabPanelSelectionChangedEvent() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
