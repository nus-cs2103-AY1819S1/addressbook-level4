package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ihwk1996

/**
 * An event that notifies HistoryListPanel on a change of in all transformations for undo-all and redo-all
 */
public class AllTransformationEvent extends BaseEvent {

    public final boolean isRemove;

    /**
     * Constructor for AllTransformationEvent on UndoAll/RedoAll Command
     * @param isRemove indicates true if undo-all has been invoked, false if redo-all has been called
     */
    public AllTransformationEvent(boolean isRemove) {
        this.isRemove = isRemove;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
