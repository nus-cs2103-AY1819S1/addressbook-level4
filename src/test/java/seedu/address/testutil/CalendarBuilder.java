package seedu.address.testutil;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
    public static final int DEFAULT_START_DAY = 1;
    public static final int DEFAULT_START_HOUR = 0;
    public static final int DEFAULT_START_MIN = 0;
    public static final int DEFAULT_END_DAY = 2;
    public static final int DEFAULT_END_HOUR = 23;
    public static final int DEFAULT_END_MIN = 59;
    public static final String DEFAULT_TITLE = "New Year";
    public static final String DEFAULT_CATEGORY = "group13";
    public static final Uid DEFAULT_UID = new Uid("1");

    private Calendar calendar;
    private Map<Month, Integer> monthToConstantMap;

    public CalendarBuilder() {
        initCleanCalendar();
        this.monthToConstantMap = initializeMonthToStringMap();
    }

    /**
     * private method to initialize monthToConstantMap.
     */
    private Map<Month, Integer> initializeMonthToStringMap() {
        HashMap<Month, Integer> map = new HashMap<>();
        map.put(new Month("JAN"), java.util.Calendar.JANUARY);
        map.put(new Month("FEB"), java.util.Calendar.FEBRUARY);
        map.put(new Month("MAR"), java.util.Calendar.MARCH);
        map.put(new Month("APR"), java.util.Calendar.APRIL);
        map.put(new Month("MAY"), java.util.Calendar.MAY);
        map.put(new Month("JUN"), java.util.Calendar.JUNE);
        map.put(new Month("JUL"), java.util.Calendar.JULY);
        map.put(new Month("AUG"), java.util.Calendar.AUGUST);
        map.put(new Month("SEP"), java.util.Calendar.SEPTEMBER);
        map.put(new Month("OCT"), java.util.Calendar.OCTOBER);
        map.put(new Month("NOV"), java.util.Calendar.NOVEMBER);
        map.put(new Month("DEC"), java.util.Calendar.DECEMBER);
        return map;

    }

    /**
     * Creates a calendar with no event.
     */
    public void initCleanCalendar() {
        // Initialize the calendar
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(DEFAULT_PRODID));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        this.calendar = calendar;
    }

    /**
     * Adds an event into the calendar.
     */
    public CalendarBuilder addEvent(VEvent event) {
        this.calendar.getComponents().add(event);
        return this;
    }

    /**
     * Creates a default VEvent to be put inside the default calendar.
     */
    public VEvent defaultEvent() {
        // ----- Create Default event -----
        // Start Date
        java.util.Calendar sDate = new GregorianCalendar();
        sDate.set(java.util.Calendar.YEAR, Integer.parseInt(DEFAULT_YEAR.toString()));
        sDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(DEFAULT_MONTH));
        sDate.set(java.util.Calendar.DAY_OF_MONTH, DEFAULT_START_DAY);
        sDate.set(java.util.Calendar.HOUR_OF_DAY, DEFAULT_START_HOUR);
        sDate.set(java.util.Calendar.MINUTE, DEFAULT_START_HOUR);
        sDate.set(java.util.Calendar.SECOND, 0);

        // End Date
        java.util.Calendar eDate = new GregorianCalendar();
        eDate.set(java.util.Calendar.YEAR, Integer.parseInt(DEFAULT_YEAR.toString()));
        eDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(DEFAULT_MONTH));
        eDate.set(java.util.Calendar.DAY_OF_MONTH, DEFAULT_END_DAY);
        eDate.set(java.util.Calendar.HOUR_OF_DAY, DEFAULT_END_HOUR);
        eDate.set(java.util.Calendar.MINUTE, DEFAULT_END_MIN);
        eDate.set(java.util.Calendar.SECOND, 59);

        // Create the event
        DateTime start = new DateTime(sDate.getTime());
        DateTime end = new DateTime(eDate.getTime());
        VEvent defaultEvent = new VEvent(start, end, DEFAULT_TITLE);

        // Add Category Color to event
        Categories categories = new Categories();
        // light blue color in iCalendarAgenda
        categories.setValue(DEFAULT_CATEGORY);
        defaultEvent.getProperties().add(categories);

        // Add a UID for the event
        defaultEvent.getProperties().add(DEFAULT_UID);

        return defaultEvent;
    }

    /**
     * Creates a customized VEvent to be put inside the default calendar.
     */
    public VEvent createEvent(Year year, Month month, int startDate, int startHour, int startMin,
                               int endDate, int endHour, int endMin, String uid, String title) {
        // ----- Create event -----
        // Start Date
        java.util.Calendar sDate = new GregorianCalendar();
        sDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        sDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        sDate.set(java.util.Calendar.DAY_OF_MONTH, startDate);
        sDate.set(java.util.Calendar.HOUR_OF_DAY, startHour);
        sDate.set(java.util.Calendar.MINUTE, startMin);
        sDate.set(java.util.Calendar.SECOND, 0);

        // End Date
        java.util.Calendar eDate = new GregorianCalendar();
        eDate.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        eDate.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        eDate.set(java.util.Calendar.DAY_OF_MONTH, endDate);
        eDate.set(java.util.Calendar.HOUR_OF_DAY, endHour);
        eDate.set(java.util.Calendar.MINUTE, endMin);
        eDate.set(java.util.Calendar.SECOND, 59);

        // Create the event
        DateTime start = new DateTime(sDate.getTime());
        DateTime end = new DateTime(eDate.getTime());
        VEvent event = new VEvent(start, end, title);

        // Add Category Color to event
        Categories categories = new Categories();
        // light blue color in iCalendarAgenda
        categories.setValue(DEFAULT_CATEGORY);
        event.getProperties().add(categories);

        // Add a UID for the event
        event.getProperties().add(new Uid(uid));

        return event;
    }

    public Calendar build() {
        return calendar;
    }
}
