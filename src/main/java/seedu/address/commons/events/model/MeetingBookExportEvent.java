package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyMeetingBook;


/** Indicates the MeetingBook in the model has triggered for export*/
public class MeetingBookExportEvent extends BaseEvent {

    public final ReadOnlyMeetingBook data;
    public final Path path;

    public MeetingBookExportEvent(ReadOnlyMeetingBook data, Path path) {
        this.data = data;
        this.path = path;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
