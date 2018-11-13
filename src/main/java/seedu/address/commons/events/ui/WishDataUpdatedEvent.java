package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.wish.Wish;

/**
 * Represents a selection change in the Wish List Panel
 */
public class WishDataUpdatedEvent extends BaseEvent {

    private final Wish newData;

    public WishDataUpdatedEvent(Wish newData) {
        this.newData = newData;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Wish getNewData() {
        return newData;
    }
}
