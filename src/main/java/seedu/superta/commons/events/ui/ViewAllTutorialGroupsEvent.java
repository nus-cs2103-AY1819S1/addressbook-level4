package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;

// @@author Caephler
/**
 * An event to tell that the view panel should return to
 * displaying all tutorial groups.
 */
public class ViewAllTutorialGroupsEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
