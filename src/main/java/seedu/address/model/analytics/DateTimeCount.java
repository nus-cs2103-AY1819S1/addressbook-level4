package seedu.address.model.analytics;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import seedu.address.model.appointment.Date;

//@@author arsalanc-v2

/**
 * Computes occurrences for various date and time periods.
 */
public class DateTimeCount {

    public DateTimeCount() {

    }

    /**
     *
     * @param dates
     * @return
     */
    public static int today(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).isEqual(todayDate))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     *
     * @param dates
     * @return
     */
    public static int currentWeek(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> isCurrentWeek(date))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     *
     * @param dates
     * @return
     */
    public static int currentMonth(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).getMonth().equals(todayDate.getMonth()))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     *
     * @param dates
     * @return
     */
    public static int currentYear(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).getYear() == todayDate.getYear())
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     *
     * @param dates
     * @return
     */
    public static Map<String, Integer> eachDayOfCurrentWeek(List<Date> dates) {
        return dates.stream()
            .filter(date -> isCurrentWeek(date))
            .map(date -> date.toString())
            .collect(groupingBy(Function.identity(), summingInt(date -> 1)));
    }

    /**
     *
     * @param dates A list of {@code Date} objects.
     * @return A Map of the number of dates that occur for each month of the current year.
     */
    public static Map<String, Integer> eachMonthOfCurrentYear(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();

        return dates.stream()
            .map(date -> getLocalDate(date))
            .filter(localDate -> localDate.getYear() == todayDate.getYear())
            .map(localDate -> localDate.getMonth().toString())
            .collect(groupingBy(Function.identity(), summingInt(date -> 1)));
    }

    /**
     * Checks if this {@code Date} falls in the current real life week.
     * @return {@code true} if date is after or equals to current week's Monday AND before next week's monday.
     * {@code false} otherwise.
     */
    public static boolean isCurrentWeek(Date toCheck) {
        LocalDate today = LocalDate.now();
        // get this week's Monday's date
        LocalDate currentWeekMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // get next week's Monday's date
        LocalDate nextWeekMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate target = getLocalDate(toCheck);

        return (target.isEqual(currentWeekMonday)) || (target.isAfter(currentWeekMonday)
            && target.isBefore(nextWeekMonday));
    }

    public static LocalDate getLocalDate(Date date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
    }
}
