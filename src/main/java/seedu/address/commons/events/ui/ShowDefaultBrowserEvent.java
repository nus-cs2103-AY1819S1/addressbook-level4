package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event representing a request to show the default browser panel.
 */
public class ShowDefaultBrowserEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
