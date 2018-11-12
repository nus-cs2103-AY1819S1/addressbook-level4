package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;
import seedu.superta.model.student.Student;

/**
 * Represents a selection change in the Student List Panel
 */
public class StudentPanelSelectionChangedEvent extends BaseEvent {
    private final Student newSelection;

    public StudentPanelSelectionChangedEvent(Student newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Student getNewSelection() {
        return newSelection;
    }
}
