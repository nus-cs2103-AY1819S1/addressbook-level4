package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ExtraInformationDisplay;

/**
 * Represents the change in information presented.
 *
 */
public class ExtraInformationDisplayChangeEvent extends BaseEvent {
    private ExtraInformationDisplay toDisplay;

    public ExtraInformationDisplayChangeEvent(ExtraInformationDisplay toDisplay) {
        this.toDisplay = toDisplay;
    }

    public ExtraInformationDisplay toDisplay() {
        return toDisplay;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
