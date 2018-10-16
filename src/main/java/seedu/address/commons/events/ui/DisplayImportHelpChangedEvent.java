package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents the visibility change in the import help display.
 *
 */
public class DisplayImportHelpChangedEvent extends BaseEvent {
    private boolean visibility;

    public DisplayImportHelpChangedEvent(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isImportHelpDisplayVisible() {
        return visibility;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
