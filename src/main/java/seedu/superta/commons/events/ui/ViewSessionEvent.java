package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * Event that is triggered when a new session is selected.
 */
public class ViewSessionEvent extends BaseEvent {
    private Session session;
    private TutorialGroup tutorialGroup;

    public ViewSessionEvent(Session session, TutorialGroup tutorialGroup) {
        this.session = session;
        this.tutorialGroup = tutorialGroup;
    }

    public Session getSession() {
        return session;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
