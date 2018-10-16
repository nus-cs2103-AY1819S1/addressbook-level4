package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to close the trivia test page.
 */
public class CloseTriviaTestViewEvent extends BaseEvent {

    /** For navigation to the matching test page. */
    public CloseTriviaTestViewEvent() {}

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
