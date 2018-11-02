package seedu.thanepark.model.ride;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

/**
 * Represents the last maintenance date, stored in Maintenance
 */
public class Date {
    private LocalDate date;

    public Date(int daysSinceMaintenance) {
        date = LocalDate.now().minusDays((long) daysSinceMaintenance);
    }

    public int getDays() {
        long days = date.until(LocalDate.now(), DAYS);
        return (int) days;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.isEqual(((Date) other).date)); // state check
    }

}
