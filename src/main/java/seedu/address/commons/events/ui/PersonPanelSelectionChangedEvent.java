package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.word.Word;

/**
 * Represents a selection change in the Word List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Word newSelection;

    public PersonPanelSelectionChangedEvent(Word newSelection) {
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
