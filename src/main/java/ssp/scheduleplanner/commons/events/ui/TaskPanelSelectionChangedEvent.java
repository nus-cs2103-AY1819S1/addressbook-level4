package ssp.scheduleplanner.commons.events.ui;

import ssp.scheduleplanner.commons.events.BaseEvent;
import ssp.scheduleplanner.model.task.Task;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final Task newSelection;

    public TaskPanelSelectionChangedEvent(Task newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Task getNewSelection() {
        return newSelection;
    }
}
