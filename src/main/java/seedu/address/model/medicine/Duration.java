package seedu.address.model.medicine;

//@@author snajef
/**
 * POJO class to hold durations.
 * @author Darien Chong
 *
 */
public class Duration {
    private static final double EQUALITY_TOLERANCE = Math.pow(10, -6);
    private static final double MILLISECONDS_IN_A_DAY = 8.64 * Math.pow(10, 7);
    private static final double MILLISECONDS_IN_A_WEEK = 7 * MILLISECONDS_IN_A_DAY;
    private static final double MILLISECONDS_IN_A_MONTH = 4 * MILLISECONDS_IN_A_WEEK;
    private static final double MILLISECONDS_IN_A_YEAR = 12 * MILLISECONDS_IN_A_MONTH;

    private double durationInMilliseconds;

    public Duration(int days) {
        durationInMilliseconds = MILLISECONDS_IN_A_DAY * days;
    }

    public double getAsDays() {
        return durationInMilliseconds / MILLISECONDS_IN_A_DAY;
    }

    public double getAsWeeks() {
        return durationInMilliseconds / MILLISECONDS_IN_A_WEEK;
    }

    public double getAsMonths() {
        return durationInMilliseconds / MILLISECONDS_IN_A_MONTH;
    }

    public double getAsYears() {
        return durationInMilliseconds / MILLISECONDS_IN_A_YEAR;
    }

    @Override
    public String toString() {
        if (getAsYears() > 1) {
            return getAsYears() + " years";
        } else if (getAsMonths() > 1) {
            return getAsMonths() + " months";
        } else if (getAsWeeks() > 1) {
            return getAsWeeks() + " weeks";
        } else {
            return getAsDays() + " days";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Duration) {
            return Math.abs(durationInMilliseconds - ((Duration) o).durationInMilliseconds) < EQUALITY_TOLERANCE;
        }

        return false;
    }
}
