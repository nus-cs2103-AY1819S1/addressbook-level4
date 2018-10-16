package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents the visibility change in the browser information.
 *
 */
public class DisplayBrowserEventChangedEvent extends BaseEvent {
    private boolean visibility;

    public DisplayBrowserEventChangedEvent(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isBrowserVisible() {
        return visibility;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
