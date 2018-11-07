package seedu.clinicio.model.analytics;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import seedu.clinicio.model.appointment.Date;

//@@author arsalanc-v2

/**
 * Contains date and time utility methods for computing occurrences and retrieving date and time values.
 */
public class DateTimeUtil {

    /**
     * @return a list of the number of dates that occur in various time periods.
     */
    public static List<Integer> todayWeekMonthYear(List<Date> dates) {
        return Arrays.asList(today(dates), currentWeek(dates), currentMonth(dates), currentYear(dates));
    }

    /**
     * @return the number of dates that match today's real life date.
     */
    public static int today(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).isEqual(todayDate))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates occurring in the current real life week.
     */
    public static int currentWeek(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> isCurrentWeek(date))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates occurring in the current real life month.
     */
    public static int currentMonth(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).getMonth().equals(todayDate.getMonth()))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates occurring in the current real life year.
     */
    public static int currentYear(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).getYear() == todayDate.getYear())
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates that match each date in the current week.
     * Returns {@code Date} for flexibility as the day of the week can be derived from it.
     */
    public static Map<Date, Integer> eachDateOfCurrentWeekCount(List<Date> dates) {
        Map<Date, Integer> currentWeekCount = new HashMap<>();
        for (Date currentWeekDate : getCurrentWeekDates()) {
            currentWeekCount.put(currentWeekDate, 0);
            for (Date date : dates) {
                if (currentWeekDate.equals(date)) {
                    currentWeekCount.replace(currentWeekDate, currentWeekCount.get(currentWeekDate) + 1);
                }
            }
        }

        return currentWeekCount;
    }

    /**
     * @return the number of dates that match each day in the next week.
     * Returns {@code Date} for flexibility as the day of the week can be derived from it.
     */
    public static Map<Date, Integer> eachDateOfNextWeekCount(List<Date> dates) {
        Map<Date, Integer> nextWeekCount = new HashMap<>();
        for (Date nextWeekDate : getNextWeekDates()) {
            nextWeekCount.put(nextWeekDate, 0);
            for (Date date : dates) {
                if (nextWeekDate.equals(date)) {
                    nextWeekCount.replace(nextWeekDate, nextWeekCount.get(nextWeekDate) + 1);
                }
            }
        }

        return nextWeekCount;
    }

    /**
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

        return (target.isEqual(currentWeekMonday) || target.isAfter(currentWeekMonday))
            && target.isBefore(nextWeekMonday);
    }

    /**
     * Checks if this {@code Date} falls in the real life week after the current one.
     * @return {@code true} if date is after or equals to next week's Monday AND before next next week's monday.
     * {@code false} otherwise.
     */
    public static boolean isNextWeek(Date toCheck) {
        LocalDate today = LocalDate.now();
        // get next week's Monday's date
        LocalDate nextWeekMonday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        // get next next week's Monday's date
        LocalDate nextNextWeekMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(
            TemporalAdjusters.next(DayOfWeek.MONDAY)
        );

        LocalDate target = getLocalDate(toCheck);
        return (target.isEqual(nextWeekMonday) || target.isAfter(nextWeekMonday))
            && target.isBefore(nextNextWeekMonday);
    }

    /**
     * @return A list of each date in the current week.
     */
    public static List<Date> getCurrentWeekDates() {
        List<Date> thisWeekDates = new ArrayList<>();
        LocalDate thisMonday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        for (DayOfWeek day : getDaysOfWeek()) {
            LocalDate nextWeekDate = thisMonday.with(TemporalAdjusters.next(day));
            thisWeekDates.add(getDate(nextWeekDate));
        }

        return thisWeekDates;
    }


    /**
     * @return A list of each date in the next week.
     */
    public static List<Date> getNextWeekDates() {
        List<Date> nextWeekDates = new ArrayList<>();
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        for (DayOfWeek day : getDaysOfWeek()) {
            LocalDate nextWeekDate = nextMonday.with(TemporalAdjusters.nextOrSame(day));
            nextWeekDates.add(getDate(nextWeekDate));
        }

        return nextWeekDates;
    }

    /**
     * @return a list of each {@code DayOfWeek}
     */
    public static List<DayOfWeek> getDaysOfWeek() {
        return Arrays.asList(DayOfWeek.values());
    }

    /**
     * Get the day of the week enumerated value based on a {@code Date}.
     */
    public static DayOfWeek getDayFromDate(Date date) {
        LocalDate localDate = getLocalDate(date);
        return localDate.getDayOfWeek();
    }

    /**
     * @return A {@code LocalDate} from a {@code Date}.
     */
    public static LocalDate getLocalDate(Date date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
    }

    /**
     * @return A {@code Date} from a {@code LocalDate}.
     */
    public static Date getDate(LocalDate localDate) {
        return new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
}
