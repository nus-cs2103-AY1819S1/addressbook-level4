package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.occasion.Occasion;

/**
 * Represents a change Occasion Browser Panel
 */
public class OccasionBrowserChangeEvent extends BaseEvent {

    private final Occasion currSelection;

    public OccasionBrowserChangeEvent(Occasion currSelection) {
        this.currSelection = currSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Occasion getCurrSelection() {
        return currSelection;
    }
}
