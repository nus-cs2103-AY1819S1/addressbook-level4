package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Updates the AppState in model when user clicks
 */

public class TabChangeEvent extends BaseEvent {
    private String updatedTab;

    public TabChangeEvent(String newValue) {
        updatedTab = newValue;
    }

    public String getUpdatedTab() {
        return updatedTab;
    }

    @Override
    public String toString() {
        return ("Tab changed to is " + updatedTab + "/n");
    }
}
