package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.events.BaseEvent;

//@@author j-lum
/**
 * A simple event that notifies the history panel to refresh.
 */

public class HistoryUpdateEvent extends BaseEvent {

    public final ArrayList<String> list;

    /**
     * Constructor for a HistoryUpdateEvent
     * @param toChange - List of past operations to display
     */
    public HistoryUpdateEvent(ArrayList<String> toChange) {
        this.list = requireNonNull(toChange);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
