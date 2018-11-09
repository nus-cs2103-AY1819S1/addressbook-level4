package seedu.restaurant.commons.events.ui.sales;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

//@@author HyperionNKJ
/**
 * Indicates a request to jump to the list of sales records
 */
public class JumpToRecordListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToRecordListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
