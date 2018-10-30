package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {

    private final ToDoListEvent newSelection;

    public TaskPanelSelectionChangedEvent(ToDoListEvent newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public ToDoListEvent getNewSelection() {
        return newSelection;
    }
}
