package seedu.learnvocabulary.commons.events.ui;

import seedu.learnvocabulary.commons.events.BaseEvent;
import seedu.learnvocabulary.model.word.Word;

/**
 * Represents a selection change in the Word List Panel
 */
public class WordPanelSelectionChangedEvent extends BaseEvent {


    private final Word newSelection;

    public WordPanelSelectionChangedEvent(Word newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Word getNewSelection() {
        return newSelection;
    }
}
