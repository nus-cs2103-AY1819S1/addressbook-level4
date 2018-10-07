package seedu.jxmusic.commons.events.ui;

import seedu.jxmusic.commons.events.BaseEvent;
import seedu.jxmusic.model.Playlist;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Playlist newSelection;

    public PersonPanelSelectionChangedEvent(Playlist newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Playlist getNewSelection() {
        return newSelection;
    }
}
