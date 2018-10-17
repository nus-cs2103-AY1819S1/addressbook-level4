package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to sort the current panel view.
 */
public class SortPanelViewEvent extends BaseEvent {

    public final int[] colIdx;

    public SortPanelViewEvent(int... colIdx) {
        this.colIdx = colIdx;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public int[] getColIdx() {
        return colIdx;
    }

}
