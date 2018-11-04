package seedu.restaurant.commons.events.ui.menu;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

//@@author yican95
/**
 * Indicates a request to jump to the list of items
 */
public class JumpToItemListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToItemListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
