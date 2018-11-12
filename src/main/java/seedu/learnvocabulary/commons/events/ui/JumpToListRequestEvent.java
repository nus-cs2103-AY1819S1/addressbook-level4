package seedu.learnvocabulary.commons.events.ui;

import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of words
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
