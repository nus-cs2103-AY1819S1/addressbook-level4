package seedu.planner.commons.events.ui;

import seedu.planner.commons.events.BaseEvent;

/**
 * An event that moves the current ui view to the period
 * where the module(s) is/are added.
 */
public class AddEvent extends BaseEvent {

    public final int index;

    public AddEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
