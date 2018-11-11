package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a refreshing of the browser panel if necessary.
 */
public class RefreshModuleBrowserEvent extends BaseEvent {

    public RefreshModuleBrowserEvent() {}

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
