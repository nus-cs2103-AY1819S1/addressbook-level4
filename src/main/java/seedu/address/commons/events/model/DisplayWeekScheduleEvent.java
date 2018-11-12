package seedu.address.commons.events.model;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.timetable.Date;

/**
 * Lists a weekly schedule.
 */
public class DisplayWeekScheduleEvent extends BaseEvent {

    private final String header;
    private final List<Date> dates;

    public DisplayWeekScheduleEvent(String header, List<Date> dates) {
        this.header = header;
        this.dates = dates;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public List<Date> getDisplayedWeekSchedule() {
        return dates;
    }

    public String getHeader() {
        return header;
    }
}
