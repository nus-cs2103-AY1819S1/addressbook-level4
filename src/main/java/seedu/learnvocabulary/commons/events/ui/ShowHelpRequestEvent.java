package seedu.learnvocabulary.commons.events.ui;

import seedu.learnvocabulary.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
