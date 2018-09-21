package seedu.address.model.medicine;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

//@@author snajef
/**
 * Test driver class for Duration POJO class functionality.
 *
 * @author Darien Chong
 *
 */
public class DurationTest {
    private Calendar startDate;
    private Calendar oneDayEndDate;
    private Calendar twoDaysEndDate;
    private Calendar oneWeekEndDate;
    private Calendar oneWeekThreeDaysEndDate;
    private Calendar oneMonthEndDate;
    private Calendar oneMonthTwoWeeksEndDate;
    private Calendar oneYearEndDate;
    private Calendar oneYearTenMonthsTwoWeeksOneDayEndDate;
    private Calendar tenYearsEndDate;

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
    public void setUp() {
        double oneDay = 1;
        double oneWeek = Duration.DAYS_PER_WEEK * oneDay;
        double oneMonth = Duration.AVERAGE_DAYS_PER_MONTH * oneDay;
        double oneYear = Duration.AVERAGE_DAYS_PER_YEAR * oneDay;

        startDate = Calendar.getInstance();

        this.oneDay = new Duration(oneDay);
        oneDayEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneDay, oneDayEndDate);

        this.twoDays = new Duration(2 * oneDay);
        twoDaysEndDate = Calendar.getInstance();
        calibrateEndDate(this.twoDays, twoDaysEndDate);

        this.oneWeek = new Duration(oneWeek);
        oneWeekEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneWeek, oneWeekEndDate);

        this.oneWeekThreeDays = new Duration(oneWeek + 3 * oneDay);
        oneWeekThreeDaysEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneWeekThreeDays, oneWeekThreeDaysEndDate);

        this.oneMonth = new Duration(oneMonth);
        oneMonthEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneMonth, oneMonthEndDate);

        this.oneMonthTwoWeeks = new Duration(oneMonth + 2 * oneWeek);
        oneMonthTwoWeeksEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneMonthTwoWeeks, oneMonthTwoWeeksEndDate);

        this.oneYear = new Duration(oneYear);
        oneYearEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneYear, oneYearEndDate);

        this.oneYearTenMonthsTwoWeeksOneDay = new Duration(oneYear + 10 * oneMonth + 2 * oneWeek + 1 * oneDay);
        oneYearTenMonthsTwoWeeksOneDayEndDate = Calendar.getInstance();
        calibrateEndDate(this.oneYearTenMonthsTwoWeeksOneDay, oneYearTenMonthsTwoWeeksOneDayEndDate);

        this.tenYears = new Duration(10 * oneYear);
        tenYearsEndDate = Calendar.getInstance();
        calibrateEndDate(this.tenYears, tenYearsEndDate);
    }

    @Test
    public void isCorrectStringRepresentDuration() {
        org.junit.Assert.assertEquals(periodToString(startDate, oneDayEndDate) + " [1 day(s)]", oneDay.toString());
        org.junit.Assert.assertEquals(periodToString(startDate, twoDaysEndDate) + " [2 day(s)]", twoDays.toString());

        org.junit.Assert.assertEquals(periodToString(startDate, oneWeekEndDate) + " [1 week(s)]", oneWeek.toString());
        org.junit.Assert.assertEquals(periodToString(startDate, oneWeekThreeDaysEndDate) + " [1 week(s), 3 day(s)]",
                oneWeekThreeDays.toString());

        org.junit.Assert.assertEquals(periodToString(startDate, oneMonthEndDate) + " [1 month(s)]",
                oneMonth.toString());
        org.junit.Assert.assertEquals(periodToString(startDate, oneMonthTwoWeeksEndDate) + " [1 month(s), 2 week(s)]",
                oneMonthTwoWeeks.toString());

        org.junit.Assert.assertEquals(periodToString(startDate, oneYearEndDate) + " [1 year(s)]", oneYear.toString());
        org.junit.Assert.assertEquals(periodToString(startDate, oneYearTenMonthsTwoWeeksOneDayEndDate)
                + " [1 year(s), 10 month(s), 2 week(s), 1 day(s)]", oneYearTenMonthsTwoWeeksOneDay.toString());
        org.junit.Assert.assertEquals(periodToString(startDate, tenYearsEndDate) + " [10 year(s)]",
                tenYears.toString());

    }

    private void calibrateEndDate(Duration d, Calendar endDate) {
        endDate.add(Calendar.MILLISECOND, (int) d.getDurationInMilliseconds());
    }

    /**
     * Helper method to convert a date range into a String representation.
     *
     * @param startDate
     *            The start date.
     * @param endDate
     *            The end date.
     * @return A String representation as follows: "from {startDate} to {endDate}",
     *         in the format dd-MM-yyyy
     */
    private String periodToString(Calendar startDate, Calendar endDate) {
        StringBuilder sb = new StringBuilder();

        String formattedStartDate = new SimpleDateFormat("dd-MM-yyyy").format(startDate.getTime());
        String formattedEndDate = new SimpleDateFormat("dd-MM-yyyy").format(endDate.getTime());

        sb.append("from ").append(formattedStartDate).append(" to ").append(formattedEndDate);

        return sb.toString();
    }
}
