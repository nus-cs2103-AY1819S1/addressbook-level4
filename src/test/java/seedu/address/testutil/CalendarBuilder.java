package seedu.address.testutil;

import java.util.GregorianCalendar;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * A utility class to help with building net.fortuna.ical4j.model.Calendar objects.
 */
public class CalendarBuilder {

    public static final String DEFAULT_PRODID = "-//Ben Fortuna//iCal4j 1.0//EN";
    public static final Year DEFAULT_YEAR = new Year("2018");
    public static final Month DEFAULT_MONTH = new Month("JAN");
    public static final int DEFAULT_MONTH_VALUE = java.util.Calendar.JANUARY;
    public static final int DEFAULT_DAY = 1;
    public static final String DEFAULT_SUMMARY = "New Year";
    public static final String DEFAULT_CATEGORY = "group13";
    public static final Uid DEFAULT_UID = new Uid("1");

    private Calendar calendar;

    public CalendarBuilder() {
        this.calendar = defaultCalendar();
    }

    public CalendarBuilder(Calendar calendarToCopy) {
        this.calendar = calendarToCopy;
    }

    /**
     * Creates a default calendar for testing.
     */
    private Calendar defaultCalendar() {
        // Initialize the calendar
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(DEFAULT_PRODID));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getComponents().add(defaultEvent());

        return calendar;
    }

    /**
     * Creates a default VEvent to be put inside the default calendar.
     */
    private VEvent defaultEvent() {
        // ----- Create Default event -----
        // Start Date
        java.util.Calendar sDate = new GregorianCalendar();
        sDate.set(java.util.Calendar.YEAR, Integer.parseInt(DEFAULT_YEAR.toString()));
        sDate.set(java.util.Calendar.MONTH, DEFAULT_MONTH_VALUE);
        sDate.set(java.util.Calendar.DAY_OF_MONTH, DEFAULT_DAY);
        sDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
        sDate.set(java.util.Calendar.MINUTE, 0);
        sDate.set(java.util.Calendar.SECOND, 0);

        // End Date
        java.util.Calendar eDate = new GregorianCalendar();
        eDate.set(java.util.Calendar.YEAR, Integer.parseInt(DEFAULT_YEAR.toString()));
        eDate.set(java.util.Calendar.MONTH, DEFAULT_MONTH_VALUE);
        eDate.set(java.util.Calendar.DAY_OF_MONTH, DEFAULT_DAY);
        eDate.set(java.util.Calendar.HOUR_OF_DAY, 23);
        eDate.set(java.util.Calendar.MINUTE, 59);
        eDate.set(java.util.Calendar.SECOND, 59);

        // Create the event
        DateTime start = new DateTime(sDate.getTime());
        DateTime end = new DateTime(eDate.getTime());
        VEvent defaultEvent = new VEvent(start, end, DEFAULT_SUMMARY);

        // Add Category Color to event
        Categories categories = new Categories();
        // light blue color in iCalendarAgenda
        categories.setValue(DEFAULT_CATEGORY);
        defaultEvent.getProperties().add(categories);

        // Add a UID for the event
        defaultEvent.getProperties().add(DEFAULT_UID);

        return defaultEvent;
    }

    public Calendar build() {
        return calendar;
    }
}
