package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Indicates a calendar has been removed from the local storage and the existing calendars in model
 * should be updated.
 */
public class RemoveExistingCalendarInModelEvent extends BaseEvent {

    public final Month month;
    public final Year year;

    public RemoveExistingCalendarInModelEvent(Month month, Year year) {
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "calendar \"" + month + "-" + year + "\" removed from local storage";
    }
}
