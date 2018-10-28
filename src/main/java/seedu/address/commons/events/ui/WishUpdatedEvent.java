package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Wish List Panel
 */
public class WishUpdatedEvent extends BaseEvent {

    public WishUpdatedEvent() {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
