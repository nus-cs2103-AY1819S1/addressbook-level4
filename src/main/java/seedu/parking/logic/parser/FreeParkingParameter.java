package seedu.parking.logic.parser;

import java.util.Date;

/**
 * Packages the arguments of the Free Parking flag into an object.
 */
public class FreeParkingParameter {

    private final String day;
    private final Date start;
    private final Date end;

    public FreeParkingParameter(String day, Date start, Date end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public String getDay() {
        return day;
    }

    public Date getStartTime() {
        return start;
    }

    public Date getEndTime() {
        return end;
    }
}
