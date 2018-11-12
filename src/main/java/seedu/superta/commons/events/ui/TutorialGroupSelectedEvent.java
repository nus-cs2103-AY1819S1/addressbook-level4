package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * Represents a selection in the TutorialGroup panel.
 * Also can be triggered by command line input.
 */
public class TutorialGroupSelectedEvent extends BaseEvent {
    private final TutorialGroup selected;

    public TutorialGroupSelectedEvent(TutorialGroup selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public TutorialGroup getSelection() {
        return selected;
    }
}
