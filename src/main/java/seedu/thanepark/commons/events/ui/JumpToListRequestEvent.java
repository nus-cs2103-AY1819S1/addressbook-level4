package seedu.thanepark.commons.events.ui;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of rides
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
