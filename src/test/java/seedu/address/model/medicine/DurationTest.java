package seedu.address.model.medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author snajef
/**
 * Test driver class for Duration POJO class functionality.
 *
 * @author Darien Chong
 *
 */
public class DurationTest {
    private LocalDate today;
    private LocalDate oneDayEndDate;
    private LocalDate twoDaysEndDate;
    private LocalDate oneWeekEndDate;
    private LocalDate oneWeekThreeDaysEndDate;
    private LocalDate oneMonthEndDate;
    private LocalDate oneMonthTwoWeeksEndDate;
    private LocalDate oneYearEndDate;
    private LocalDate oneYearTenMonthsTwoWeeksOneDayEndDate;
    private LocalDate tenYearsEndDate;

    private Duration oneDay;
    private Duration twoDays;
    private Duration oneWeek;
    private Duration oneWeekThreeDays;
    private Duration oneMonth;
    private Duration oneMonthTwoWeeks;
    private Duration oneYear;
    private Duration oneYearTenMonthsTwoWeeksOneDay;
    private Duration tenYears;

    @Before
    public void setUp() throws IllegalValueException {
        int oneDay = 1;
        int oneWeek = Duration.DAYS_PER_WEEK * oneDay;
        int oneMonth = Duration.DAYS_PER_MONTH * oneDay;
        int oneYear = Duration.DAYS_PER_YEAR * oneDay;

        today = LocalDate.now();

        this.oneDay = new Duration(oneDay);
        oneDayEndDate = today.plus(oneDay - 1, ChronoUnit.DAYS);

        this.twoDays = new Duration(2 * oneDay);
        twoDaysEndDate = today.plus(2 * oneDay - 1, ChronoUnit.DAYS);

        this.oneWeek = new Duration(oneWeek);
        oneWeekEndDate = today.plus(oneWeek - 1, ChronoUnit.DAYS);

        this.oneWeekThreeDays = new Duration(oneWeek + 3 * oneDay);
        oneWeekThreeDaysEndDate = today.plus(oneWeek + 3 * oneDay - 1, ChronoUnit.DAYS);

        this.oneMonth = new Duration(oneMonth);
        oneMonthEndDate = today.plus(oneMonth - 1, ChronoUnit.DAYS);

        this.oneMonthTwoWeeks = new Duration(oneMonth + 2 * oneWeek);
        oneMonthTwoWeeksEndDate = today.plus(oneMonth + 2 * oneWeek - 1, ChronoUnit.DAYS);

        this.oneYear = new Duration(oneYear);
        oneYearEndDate = today.plus(oneYear - 1, ChronoUnit.DAYS);

        this.oneYearTenMonthsTwoWeeksOneDay = new Duration(oneYear + 10 * oneMonth + 2 * oneWeek + 1 * oneDay);
        oneYearTenMonthsTwoWeeksOneDayEndDate = today.plus(oneYear + 10 * oneMonth + 2 * oneWeek + 1 * oneDay - 1,
            ChronoUnit.DAYS);

        this.tenYears = new Duration(10 * oneYear);
        tenYearsEndDate = today.plus(10 * oneYear - 1, ChronoUnit.DAYS);
    }

    @Test
    public void constructor_negativeIntegerDuration_throwsIllegalValueException() {
        seedu.address.testutil.Assert.assertThrows(IllegalValueException.class,
            Duration.MESSAGE_DURATION_MUST_BE_POSITIVE, () -> new Duration(-1));
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        assertTrue(oneDay.equals(oneDay));
    }

    @Test
    public void equals_durationAndDefensiveCopy_returnsTrue() throws IllegalValueException {
        assertTrue(oneDay.equals(new Duration(oneDay)));
    }

    @Test
    public void equals_differentTypes_returnsTrue() {
        assertFalse(oneDay.equals(1));
    }

    @Test
    public void isCorrectStringRepresentDuration() {
        org.junit.Assert.assertEquals(periodToString(today, oneDayEndDate) + " [1 day(s)]", oneDay.toString());
        org.junit.Assert.assertEquals(periodToString(today, twoDaysEndDate) + " [2 day(s)]", twoDays.toString());

        org.junit.Assert.assertEquals(periodToString(today, oneWeekEndDate) + " [1 week(s)]", oneWeek.toString());
        org.junit.Assert.assertEquals(periodToString(today, oneWeekThreeDaysEndDate) + " [1 week(s), 3 day(s)]",
            oneWeekThreeDays.toString());

        org.junit.Assert.assertEquals(periodToString(today, oneMonthEndDate) + " [1 month(s)]",
            oneMonth.toString());
        org.junit.Assert.assertEquals(periodToString(today, oneMonthTwoWeeksEndDate) + " [1 month(s), 2 week(s)]",
            oneMonthTwoWeeks.toString());

        org.junit.Assert.assertEquals(periodToString(today, oneYearEndDate) + " [1 year(s)]", oneYear.toString());
        org.junit.Assert.assertEquals(periodToString(today, oneYearTenMonthsTwoWeeksOneDayEndDate)
            + " [1 year(s), 10 month(s), 2 week(s), 1 day(s)]", oneYearTenMonthsTwoWeeksOneDay.toString());
        org.junit.Assert.assertEquals(periodToString(today, tenYearsEndDate) + " [10 year(s)]",
            tenYears.toString());
    }

    @Test
    public void shiftDateRange_startAndEndDatesAreCorrectlyShifted() throws IllegalValueException {
        Duration aCopyOfTwoDays = new Duration(twoDays);
        LocalDate oneDayIntoFuture = today.plus(1, ChronoUnit.DAYS);
        LocalDate twoDaysIntoFuture = today.plus(2, ChronoUnit.DAYS);

        aCopyOfTwoDays.shiftDateRange(oneDayIntoFuture);

        assertEquals(aCopyOfTwoDays.getStartDateAsString(), Duration.DATE_FORMAT.format(oneDayIntoFuture));
        assertEquals(aCopyOfTwoDays.getEndDateAsString(), Duration.DATE_FORMAT.format(twoDaysIntoFuture));
    }

    /**
     * Helper method to convert a date range into a String representation.
     *
     * @param startDate The start date.
     * @param endDate The end date.
     * @return A String representation as follows: "from {startDate} to {endDate}",
     *         in the format dd-MM-yyyy
     */
    private String periodToString(LocalDate startDate, LocalDate endDate) {
        StringBuilder sb = new StringBuilder();

        String formattedStartDate = startDate.format(Duration.DATE_FORMAT);
        String formattedEndDate = endDate.format(Duration.DATE_FORMAT);

        sb.append("from ").append(formattedStartDate).append(" to ").append(formattedEndDate);

        return sb.toString();
    }
}
