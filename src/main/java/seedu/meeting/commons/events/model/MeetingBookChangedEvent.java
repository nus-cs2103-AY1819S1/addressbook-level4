package seedu.meeting.commons.events.model;

import seedu.meeting.commons.events.BaseEvent;
import seedu.meeting.model.ReadOnlyMeetingBook;

/** Indicates the MeetingBook in the model has changed*/
public class MeetingBookChangedEvent extends BaseEvent {

    public final ReadOnlyMeetingBook data;

    public MeetingBookChangedEvent(ReadOnlyMeetingBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
