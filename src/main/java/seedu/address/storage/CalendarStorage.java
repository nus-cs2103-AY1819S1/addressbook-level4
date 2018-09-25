package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import net.fortuna.ical4j.model.Calendar;

//@@author GilgameshTC
/**
 * Represents a storage for Calendar.
 */
public interface CalendarStorage {

    /**
     * Returns the file path of the Calendar directory.
     */
    Path getCalendarPath();

    /**
     * Creates the given Calendar to the storage.
     * @param calendar cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void createCalendar(Calendar calendar) throws IOException;

}
