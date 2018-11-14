package seedu.parking.commons.events.ui;

import seedu.parking.commons.core.index.Index;
import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of car parks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
