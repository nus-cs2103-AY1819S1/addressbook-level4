package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.SortCommand.SortOrder;

/**
 * Indicates a request to sort the current panel view.
 */
public class SortPanelViewEvent extends BaseEvent {
    private final SortOrder order;
    private final int[] colIdx;

    public SortPanelViewEvent(SortOrder order, int... colIdx) {
        this.order = order;
        this.colIdx = colIdx;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public int[] getColIdx() {
        return colIdx;
    }

    public SortOrder getOrder() {
        return order;
    }
}
