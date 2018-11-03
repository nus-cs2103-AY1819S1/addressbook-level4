package seedu.address.testutil;

import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * A utility class containing a list of {@code net.fortuna.ical4j.model.Calendar} objects to be used in tests.
 */
public class TypicalCalendars {

    public static final CalendarBuilder EMPTY_CALENDAR_BUILDER = new CalendarBuilder();

    public static final VEvent DEFAULT_EVENT = EMPTY_CALENDAR_BUILDER.defaultEvent();
    public static final VEvent CHRISTMAS_EVENT = EMPTY_CALENDAR_BUILDER.createEvent(new Year("2018"), new Month("DEC"),
            25, 0, 0, 25, 23, 59, "2", "Christmas Day");
    public static final VEvent THREE_DAY_CAMP = EMPTY_CALENDAR_BUILDER.createEvent(new Year("2018"), new Month("AUG"),
            23, 8, 0, 26, 17, 0, "3", "RHOC");

    public static final Calendar DEFAULT_CALENDAR = new CalendarBuilder().addEvent(DEFAULT_EVENT).build();
    public static final String DEFAULT_CALENDAR_NAME = DEFAULT_MONTH + "-" + DEFAULT_YEAR;


    // prevents instantiation
    private TypicalCalendars() {
    }

}
