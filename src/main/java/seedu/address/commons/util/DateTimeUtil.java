package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.model.event.Date;
import seedu.address.model.event.Time;

/**
 * Utility methods related to Date
 */
public class DateTimeUtil {

    /**
     * Returns a friendly date string of an Event Date object.
     */
    public static String getFriendlyDateFromEventDate(Date date) {
        SimpleDateFormat inf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outf = new SimpleDateFormat("EEE, d MMM yy");

        try {
            return outf.format(inf.parse(date.value));
        } catch (ParseException e) {
            return date.value;
        }
    }

    /**
     * Returns a friendly time string of an Event Time object.
     */
    public static String getFriendlyTimeFromEventTime(Time time) {
        SimpleDateFormat inf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat outf = new SimpleDateFormat("K:mma");

        try {
            return outf.format(inf.parse(time.value));
        } catch (ParseException e) {
            return time.value;
        }
    }
}
