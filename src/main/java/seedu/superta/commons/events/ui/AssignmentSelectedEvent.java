package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * Event for Assignment selection changed.
 */
public class AssignmentSelectedEvent extends BaseEvent {
    public final TutorialGroup tutorialGroup;
    public final Assignment assignment;

    public AssignmentSelectedEvent(TutorialGroup tutorialGroup, Assignment assignment) {
        this.tutorialGroup = tutorialGroup;
        this.assignment = assignment;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
