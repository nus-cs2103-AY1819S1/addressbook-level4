package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyLibrary;

/** Indicates the Library in the model has changed*/
public class LibraryChangedEvent extends BaseEvent {

    public final ReadOnlyLibrary data;

    public LibraryChangedEvent(ReadOnlyLibrary data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of playlists " + data.getPlaylistList().size();
    }
}
