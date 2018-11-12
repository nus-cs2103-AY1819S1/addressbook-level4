package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of items.
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final String type;

    public JumpToListRequestEvent(String type, Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
        this.type = type;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
