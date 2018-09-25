package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final Person newSelection;

    public TaskPanelSelectionChangedEvent(Person newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }
}
