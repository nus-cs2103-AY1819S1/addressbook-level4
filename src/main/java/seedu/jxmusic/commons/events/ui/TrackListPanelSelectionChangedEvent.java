package seedu.jxmusic.commons.events.ui;

import seedu.jxmusic.commons.events.BaseEvent;
import seedu.jxmusic.model.Track;

/**
 * Represents a selection change in the Track List Panel
 */
public class TrackListPanelSelectionChangedEvent extends BaseEvent {
    private final Track newSelection;

    public TrackListPanelSelectionChangedEvent(Track newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Track getNewSelection() {
        return newSelection;
    }
}
