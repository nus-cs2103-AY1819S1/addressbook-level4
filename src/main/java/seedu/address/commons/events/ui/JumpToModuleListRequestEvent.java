package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author waytan
/**
 * Indicates a request to jump to the list of modules
 */
public class JumpToModuleListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToModuleListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
