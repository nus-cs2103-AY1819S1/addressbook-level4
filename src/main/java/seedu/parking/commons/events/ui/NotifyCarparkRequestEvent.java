package seedu.parking.commons.events.ui;

import seedu.parking.commons.core.index.Index;
import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of car parks
 */
public class NotifyCarparkRequestEvent extends BaseEvent {

    public final int targetIndex;

    public NotifyCarparkRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
