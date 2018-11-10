package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author GilgameshTC
/**
 * Indicates a calendar is no longer in the local storage and should load a calendar not found page
 * onto the UI.
 */
public class CalendarNotFoundEvent extends BaseEvent {

    public final String message = "Error: Calendar \"%s\" cannot be found";

    public final String calendarName;

    public CalendarNotFoundEvent(String calendarName) {
        this.calendarName = calendarName;
    }

    @Override
    public String toString() {
        return String.format(message, calendarName);
    }
}
