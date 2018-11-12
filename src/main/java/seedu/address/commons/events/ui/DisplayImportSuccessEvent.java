package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents the visibility change in the {@link seedu.address.ui.home.InfoPanel} upon a successful import.
 */
public class DisplayImportSuccessEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
