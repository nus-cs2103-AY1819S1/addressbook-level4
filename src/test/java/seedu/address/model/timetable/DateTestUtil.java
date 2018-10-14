package seedu.address.model.timetable;

/**
 * Contains helper final Strings for testing Dates and its constituents: Time, Week, Day.
 */
public class DateTestUtil {
    /** Valid Strings */
    // Time fields
    public static final String VALID_TIME_START = "0000";
    public static final String VALID_TIME_MID = "1430";
    public static final String VALID_TIME_END = "2330";

    // Week fields
    public static final String VALID_WEEK_STUDY = "1";
    public static final String VALID_WEEK_RECESS = "recess";

    // Day fields
    public static final String VALID_DAY_WED = "wEd";
    public static final String VALID_DAY_THU = "THU";

    /** Invalid Strings **/
    // Time fields
    public static final String INVALID_TIME_END = "2400";
    public static final String INVALID_TIME_MID = "1445";

    // Week fields
    public static final String INVALID_WEEK_STUDY = "one";
    public static final String INVALUD_WEEK_RECESS = "Recess";

    // Day fields
    public static final String INVALID_DAY_WED = "3";
    public static final String INVALID_DAY_THU = "Thur";


}
