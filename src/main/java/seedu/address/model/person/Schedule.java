package seedu.address.model.person;

/**
 * @author adjscent
 */
public class Schedule {

    public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "Schedule should be in 0 or 1s";
    /**
     * Example
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     */


    // 7 days 24 hours
    public final int[][] value;

    public Schedule() {
        value = new int[7][24];
    }

    public Schedule(String schedule) {
        value = new int[7][24];
        int counter = 0;
        int totalcount = 7 * 24;
        assert (schedule.length() == totalcount);

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                value[i][j] = Integer.parseInt(schedule.charAt(counter) + "");
                counter += 1;
            }
        }
    }

    public static boolean isValidSchedule(String trimmedSchedule) {
        return true;
    }

    /**
     * @param day
     * @param time
     * @return
     */
    public boolean getTimeDay(String day, String time) {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day, dayNum);

        int timeNum = -1;

        timeNum = Integer.parseInt(time.substring(0, 1));


        return value[dayNum][timeNum] == 1 ? true : false;
    }

    /**
     * @param day
     * @param time
     * @param occupied
     */
    public void setTimeDay(String day, String time, boolean occupied) {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day, dayNum);

        int timeNum = -1;

        timeNum = Integer.parseInt(time.substring(0, 2));


        value[dayNum][timeNum] = occupied ? 1 : 0;
    }

    private int getDayNum(String day, int dayNum) {
        switch (day.toLowerCase()) {
        case "monday":
            dayNum = 0;
            break;
        case "tuesday":
            dayNum = 1;
            break;
        case "wednesday":
            dayNum = 2;
            break;
        case "thursday":
            dayNum = 3;
            break;
        case "friday":
            dayNum = 4;
            break;
        case "saturday":
            dayNum = 5;
            break;
        case "sunday":
            dayNum = 6;
            break;
        default:
            break;
        }
        return dayNum;
    }


    /** Allow schedule array to be stored as a string
     * @return
     */
    public String valueToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                sb.append(value[i][j]);
            }
        }
        return sb.toString();
    }
}
