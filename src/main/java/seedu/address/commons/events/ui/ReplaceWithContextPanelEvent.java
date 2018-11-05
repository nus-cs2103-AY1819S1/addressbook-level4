package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a change to current context panel.
 */
public class ReplaceWithContextPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
