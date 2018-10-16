package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.CalendarModel;

//@@author GilgameshTC
/** Indicates a calendar has been created*/
public class CalendarCreatedEvent extends BaseEvent {

    public final CalendarModel data;

    public CalendarCreatedEvent(CalendarModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of calendars " + data.size();
    }
}
