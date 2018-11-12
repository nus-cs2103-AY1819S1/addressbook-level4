package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.model.event.Date;
import seedu.address.model.event.Time;

/**
 * Utility methods related to Date
 */
public class DateTimeUtil {

    public static final int UPCOMING_EVENT = 0;
    public static final int ONGOING_EVENT = 1;
    public static final int COMPLETED_EVENT = 2;
    public static final int INVALID_STATUS = 3;

    public static final String[] STATUS = { "Upcoming", "Ongoing", "Completed" };

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

    public static int getEventStatus(Date startDate, Time startTime, Date endDate, Time endTime) {
        SimpleDateFormat inf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            java.util.Date start = inf.parse(startDate + " " + startTime);
            java.util.Date end = inf.parse(endDate + " " + endTime);
            java.util.Date now = new java.util.Date();

            if (now.compareTo(start) < 0) {
                return UPCOMING_EVENT;
            } else if (now.compareTo(start) >= 0 && now.compareTo(end) <= 0) {
                return ONGOING_EVENT;
            } else {
                return COMPLETED_EVENT;
            }

        } catch (ParseException e) {
            return INVALID_STATUS;
        }
    }
}
