package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class ListPickerSelectionChangedEvent extends BaseEvent {

    private final int newSelection;

    public ListPickerSelectionChangedEvent(int newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public int getNewSelection() {
        return newSelection;
    }
}
