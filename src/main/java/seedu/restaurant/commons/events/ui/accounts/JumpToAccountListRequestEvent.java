package seedu.restaurant.commons.events.ui.accounts;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

//@@author AZhiKai

/**
 * Indicates a request to jump to the list of accounts
 */
public class JumpToAccountListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToAccountListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
