package seedu.address.model;

import java.io.IOException;
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

    public CalendarModel(CalendarStorage calendarStorage, Map<Year, Set<Month>> existingCalendar) {
        this.calendarStorage = calendarStorage;
        this.existingCalendar = existingCalendar;
        this.loadedCalendar = null;

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

    /** Setter method for loadedCalendar field. */
    private void setLoadedCalendar(Calendar calendar) {
        this.loadedCalendar = calendar;

    }

    public Calendar getLoadedCalendar() {
        return this.loadedCalendar;

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
