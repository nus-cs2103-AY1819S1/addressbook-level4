package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author chivent
/**
 * An event that notifies HistoryListPanel on a change of in total transformations performed
 */
public class TransformationEvent extends BaseEvent {

    public final boolean isRemove;
    public final String transformation;

    /**
     * Constructor for TransformationEvent on Undo/Redo Command
     * @param isRemove indicates true if undo has been invoked, false if redo has been called
     */
    public TransformationEvent(boolean isRemove) {
        this.transformation = "";
        this.isRemove = isRemove;
    }

    /**
     * Constructor for TransformationEvent on Redo Command or Add Command
     * @param transformation Contains transformation to add to stored list
     */
    public TransformationEvent(String transformation) {
        this.transformation = transformation;
        this.isRemove = false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
