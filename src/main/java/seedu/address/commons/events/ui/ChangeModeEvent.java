package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event changing the current stylesheet / day or night mode of the app
 */
public class ChangeModeEvent extends BaseEvent {
    private boolean toggleValue;

    public ChangeModeEvent() {
        toggleValue = true;
    }

    public boolean getToggleValue() {
        return toggleValue;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
