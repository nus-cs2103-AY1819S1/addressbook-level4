package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the List Panel
 */

public class PanelSelectionChangedEvent<T> extends BaseEvent {
    private final T newSelection;

    public PanelSelectionChangedEvent(T newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public T getNewSelection() {
        return newSelection;
    }
}

