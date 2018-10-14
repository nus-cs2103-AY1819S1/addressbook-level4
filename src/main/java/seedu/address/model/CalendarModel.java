package seedu.address.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.FixedUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.storage.CalendarStorage;

//@@author GilgameshTC
/**
 * Wraps Calendar Data
 */
public class CalendarModel {

    private CalendarStorage calendarStorage;
    private Map<Year, Set<Month>> existingCalendar;
    // Field to store calendar loaded by user if any.
    // User can only load at most one calendar at any point of time.
    private Calendar loadedCalendar;
    private Map<Month, Integer> monthToConstantMap;

    public CalendarModel(CalendarStorage calendarStorage, Map<Year, Set<Month>> existingCalendar) {
        this.calendarStorage = calendarStorage;
        this.existingCalendar = existingCalendar;
        this.loadedCalendar = null;
        this.monthToConstantMap = initializeMonthToStringMap();

    }

    /** Provide a mapping between Month objects and java.util.Calendar int constants. */
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

    /** Checks if calendar to be created already exists. */
    public boolean isExistingCalendar(Year year, Month month) {
        Set<Month> yearExists = existingCalendar.get(year);
        if (yearExists != null) {
            for (Month existingMonths : yearExists) {
                if (month.equals(existingMonths)) {
                    return true;
                }
            }
        }

        return false;
    }

    /** Checks if date is valid in a particular month. */
    public boolean isValidDate(Year year, Month month, int date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, Integer.parseInt(year.toString()));
        cal.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        int maximumDate = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        if (date > maximumDate) {
            return false;
        }
        return true;

    }

    /** Setter method for loadedCalendar field. */
    private void setLoadedCalendar(Calendar calendar) {
        this.loadedCalendar = calendar;

    }

    /** Creates the calendar file. */
    public void createCalendar(Year year, Month month) throws IOException {
        // Initialize the calendar
        String calendarName = month + "-" + year;
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        // Dummy Event
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 25);

        // Initialise as an all day event
        VEvent christmas = new VEvent(new net.fortuna.ical4j.model.Date(cal.getTime()), "Christmas Day");
        // Generate a UID for the event
        UidGenerator ug = new FixedUidGenerator("1");
        christmas.getProperties().add(ug.generateUid());
        calendar.getComponents().add(christmas);

        // Create the calendar
        calendarStorage.createCalendar(calendar, calendarName);

        // Update existing calendar map
        Set<Month> yearOfCal = existingCalendar.get(year);
        if (yearOfCal == null) {
            Set<Month> newYearOfCal = new HashSet<>();
            newYearOfCal.add(month);
            existingCalendar.put(year, newYearOfCal);
        } else {
            yearOfCal.add(month);
        }
    }

    /** Load and parse the requested calendar file. */
    public void loadCalendar(Year year, Month month) throws IOException, ParserException {
        String calendarName = month + "-" + year;
        Calendar calendarToBeLoaded = calendarStorage.loadCalendar(calendarName);
        setLoadedCalendar(calendarToBeLoaded);

    }

    /** Create a new event in loaded Calendar. */
    public void createAllDayEvent(Year year, Month month, int date, String title) throws IOException, ParserException {
        // Load the calendar
        loadCalendar(year, month);

        // Set the Date
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.MONTH, monthToConstantMap.get(month));
        cal.set(java.util.Calendar.DAY_OF_MONTH, date);

        // Initialise as an all day event
        VEvent newEvent = new VEvent(new net.fortuna.ical4j.model.Date(cal.getTime()), title);
        // Generate a UID for the event
        UidGenerator ug = new FixedUidGenerator("1");
        newEvent.getProperties().add(ug.generateUid());
        loadedCalendar.getComponents().add(newEvent);

        String calendarName = month + "-" + year;
        // Save the updated calendar to storage
        calendarStorage.createCalendar(loadedCalendar, calendarName);
    }

    /** Returns the updated Map: existingCalendar. */
    public Map<Year, Set<Month>> updateExistingCalendar() {
        return existingCalendar;
    }

    /** Returns the number of calendar in hard disk. */
    public int size() {
        int size = 0;
        for (Year year : existingCalendar.keySet()) {
            Set<Month> monthsOfYear = existingCalendar.get(year);
            if (monthsOfYear != null) {
                size += monthsOfYear.size();
            }
        }
        return size;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CalendarModel)) { //this handles null as well.
            return false;
        }

        CalendarModel o = (CalendarModel) other;

        return calendarStorage.equals(o.calendarStorage)
                && existingCalendar.equals(o.existingCalendar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendarStorage, existingCalendar);
    }
}
