package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.model.volunteer.Birthday;

/**
 * Utility methods related to Birthday
 */
public class BirthdayUtil {

    /**
     * Returns a friendly date string of a Volunteer Birthday object.
     */
    public static String getFriendlyDateFromVolunteerBirthday(Birthday birthday) {
        SimpleDateFormat inf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outf = new SimpleDateFormat("d MMMMM yyyy");

        try {
            return outf.format(inf.parse(birthday.value));
        } catch (ParseException e) {
            return birthday.value;
        }
    }
}
