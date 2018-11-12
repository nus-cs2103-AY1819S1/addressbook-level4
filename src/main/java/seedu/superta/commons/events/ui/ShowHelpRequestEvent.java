package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
