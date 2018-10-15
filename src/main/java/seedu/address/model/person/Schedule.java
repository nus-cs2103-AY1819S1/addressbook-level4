package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * @author adjscent
 */
public class Schedule {
    /**
     * Example
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     */

    public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "Schedule should be in 0 or 1s";
    private static final int DAY = 7;
    private static final int HOUR = 48;
    private static final int TOTAL = DAY * HOUR;

    // 7 days 24 hours - 48 30mins
    public final int[][] value;

    public Schedule() {
        value = new int[DAY][HOUR];
    }

    public Schedule(String schedule) {
        requireNonNull(schedule);
        assert (schedule.length() == TOTAL);
        value = new int[DAY][HOUR];

        for (int i = 0, counter = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                value[i][j] = Integer.parseInt(schedule.charAt(counter) + "");
                counter += 1;
            }
        }
    }

    public static boolean isValidSchedule(String trimmedSchedule) {
        return trimmedSchedule.length() == TOTAL;
    }

    /**
     * @param day
     * @param time
     * @return
     */
    public boolean getTimeDay(String day, String time) throws ParseException {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day);


        int hourNum = Integer.parseInt(time.substring(0, 2));

        int minNum = Integer.parseInt(time.substring(2, 4));

        int timeNum = hourNum * 2;

        if (minNum >= 30) {
            return value[dayNum][timeNum + 1] == 1 ? true : false;
        } else {
            return value[dayNum][timeNum] == 1 ? true : false;
        }


    }

    /**
     * @param day
     * @param time
     * @param occupied
     */
    public void setTimeDay(String day, String time, boolean occupied) throws ParseException {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day);

        int timeNum = -1;

        timeNum = Integer.parseInt(time.substring(0, 2)) * 2;

        int minNum = Integer.parseInt(time.substring(2, 4));

        if (minNum >= 30) {
            value[dayNum][timeNum + 1] = occupied ? 1 : 0;
        } else {
            value[dayNum][timeNum] = occupied ? 1 : 0;
        }

    }

    private int getDayNum(String day) throws ParseException {
        int dayNum = -1;
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
            throw new ParseException("Invalid Schedule Input");
        }
        return dayNum;
    }

    private String getNumDay(int dayNum) throws ParseException {
        String day;
        switch (dayNum) {
        case 0:
            day = "monday";
            break;
        case 1:
            day = "tuesday";
            break;
        case 2:
            day = "wednesday";
            break;
        case 3:
            day = "thursday";
            break;
        case 4:
            day = "friday";
            break;
        case 5:
            day = "saturday";
            break;
        case 6:
            day = "sunday";
            break;
        default:
            throw new ParseException("Invalid Schedule Input");
        }
        return day;
    }

    /**
     * Allow schedule array to be stored as a string
     *
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

    /**
     * Maxs all possible schedules supplied as parameter
     *
     * @param schedules
     * @return
     */
    public static Schedule maxSchedule(Schedule... schedules) {
        Schedule newSchedule = new Schedule();

        // using for each loop to display contents of a
        for (Schedule s : schedules) {
            newSchedule.union(s);
        }

        return newSchedule;
    }

    /**
     * ORs the Schedules
     *
     * @param schedule
     */
    private void union(Schedule schedule) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] = (this.value[i][j] | schedule.value[i][j]);
            }
        }
    }

    /**
     * Use the updateschedule as a bit flipper
     */
    public void xor(Schedule updateSchedule) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] ^= (updateSchedule.value[i][j]);
            }
        }
    }

    /**
     * @return
     * @throws ParseException
     */
    public ArrayList<Slot> getFreeTime() throws ParseException {
        ArrayList<Slot> slots = new ArrayList<Slot>();

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                if (this.value[i][j] == 0) {
                    Slot slot = new Slot();
                    slot.day = getNumDay(i);
                    if (j / 2 > 9) {
                        slot.time = "" + j / 2 + ((j % 2 == 1) ? "30" : "00");
                    } else {
                        slot.time = "0" + j / 2 + ((j % 2 == 1) ? "30" : "00");
                    }
                    slots.add(slot);
                }
            }
        }

        return slots;
    }

    /**
     * @return
     * @throws ParseException
     */
    public String freeTimeToString() throws ParseException {
        StringBuilder sb = new StringBuilder();
        getFreeTime().forEach((slot) -> sb.append(slot.day + "," + slot.time + ";"));
        return sb.toString().trim();
    }


    /**
     *
     */
    class Slot {
        private String day;
        private String time;

        /**
         * @return
         */
        public String getDay() {
            return day;
        }

        /**
         * @param day
         */
        public void setDay(String day) {
            this.day = day;
        }

        /**
         * @return
         */
        public String getTime() {
            return time;
        }

        /**
         * @param time
         */
        public void setTime(String time) {
            this.time = time;
        }
    }
}
