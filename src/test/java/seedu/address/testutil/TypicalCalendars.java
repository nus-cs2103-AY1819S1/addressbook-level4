package seedu.address.testutil;

import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;

import net.fortuna.ical4j.model.Calendar;

//@@author GilgameshTC
/**
 * A utility class containing a list of {@code net.fortuna.ical4j.model.Calendar} objects to be used in tests.
 */
public class TypicalCalendars {

    public static final Calendar DEFAULT_CALENDAR = new CalendarBuilder().build();
    public static final String DEFAULT_CALENDAR_NAME = DEFAULT_MONTH + "-" + DEFAULT_YEAR;

    // prevents instantiation
    private TypicalCalendars() {
    }

}
