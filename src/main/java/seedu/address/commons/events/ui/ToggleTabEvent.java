package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to toggle a tab
 */
public class ToggleTabEvent extends BaseEvent {
    private String toToggleTo;

    public ToggleTabEvent(String tabToGo) {
        toToggleTo = tabToGo;
    }

    public String getToToggleTo() {
        return toToggleTo;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
