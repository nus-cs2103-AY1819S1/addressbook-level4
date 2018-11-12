package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.timetable.exceptions.DateNotFoundException;
import seedu.address.model.timetable.exceptions.DuplicateDateException;
import seedu.address.model.user.Username;

/**
 * Represents a user's schedule of busy dates in the MakanBook.
 * Guarantees: data values are validated and immutable.
 */
public class UniqueSchedule {

    // Identity fields
    private final Username username;

    // Data fields
    private final HashMap<Week, List<Date>> internalSchedule;

    public UniqueSchedule(Username username) {
        requireNonNull(username);
        this.username = username;

        // initialising the HashMap with the weeks.
        this.internalSchedule = new HashMap<>() {
            {
                put(new Week("1"), new ArrayList<Date>());
                put(new Week("2"), new ArrayList<Date>());
                put(new Week("3"), new ArrayList<Date>());
                put(new Week("4"), new ArrayList<Date>());
                put(new Week("5"), new ArrayList<Date>());
                put(new Week("6"), new ArrayList<Date>());
                put(new Week("recess"), new ArrayList<Date>());
                put(new Week("7"), new ArrayList<Date>());
                put(new Week("8"), new ArrayList<Date>());
                put(new Week("9"), new ArrayList<Date>());
                put(new Week("10"), new ArrayList<Date>());
                put(new Week("11"), new ArrayList<Date>());
                put(new Week("12"), new ArrayList<Date>());
                put(new Week("13"), new ArrayList<Date>());
                put(new Week("reading"), new ArrayList<Date>());
                put(new Week("14"), new ArrayList<Date>());
                put(new Week("15"), new ArrayList<Date>());
            }
        };
    }

    /**
     * Returns true if the list contains an equivalent Date as the given argument.
     */
    public boolean contains(Date toCheck) {
        requireNonNull(toCheck);
        // get the list of busy dates for that week.
        List<Date> weekDates = internalSchedule.get(toCheck.getWeek());
        return weekDates.stream().anyMatch(date -> date.equals(toCheck));

    }

    /**
     * Adds a Date to the user's BusyDate schedule.
     * The date must not already exist in the list.
     */
    public void add(Date toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDateException();
        }
        // modify the key value of the hashmap.
        List<Date> weekDates = internalSchedule.get(toAdd.getWeek());
        weekDates.add(toAdd);
    }

    /**
     * Adds all the uniqueBusySchedule dates from another schedule into the current schedule.
     */
    public void addAll(UniqueSchedule otherSchedule) {
        // change all the reference pointers to point to otherSchedule's reference.
        List<Date> allDates = otherSchedule.getAllBlockedDatesOnSchedule();
        allDates.stream().forEach(this::add);
    }

    /**
     * Removes the equivalent Date from the user's busy schedule.
     * The date must exist in the list.
     */
    public void remove(Date toRemove) {
        requireNonNull(toRemove);
        List<Date> weekDates = internalSchedule.get(toRemove.getWeek());
        // remove the date from the list.
        if (!weekDates.remove(toRemove)) {
            throw new DateNotFoundException();
        }
        weekDates.remove(toRemove);
    }

    public Username getUsername() {
        return this.username;
    }

    /**
     * Provides an immutable sorted date list for the NUS week, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * @param week THE NUS Week.
     * @return an immutable date list.
     */
    public List<Date> getBusyDatesForWeek(Week week) {
        // the TreeSet<Date> for the week.
        List<Date> weekDates = internalSchedule.get(week);
        Collections.sort(weekDates);
        return Collections.unmodifiableList(weekDates);
    }

    /**
     * Returns an immutable sorted blocked date list for the NUS week, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     * @return the immutable list of all dates.
     */
    public List<Date> getAllBlockedDatesOnSchedule() {
        List<List<Date>> listOfWeekDates = new ArrayList<>(internalSchedule.values());
        List<Date> allDates = listOfWeekDates.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Collections.sort(allDates);
        return Collections.unmodifiableList(allDates);
    }

    /**
     * Returns an immutable sorted free date list for the NUS week, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * @return the immutable list of all dates.
     */
    public List<Date> getFreeDatesForWeek(Week week) {
        List<Date> allBlockedDatesForWeek = getBusyDatesForWeek(week);
        List<Date> freeDatesForWeek = UniqueSchedule.getFreeDatesFromBusyWeekDates(allBlockedDatesForWeek, week);

        return Collections.unmodifiableList(freeDatesForWeek);

    }

    /**
     * Returns an immutable sorted free date list for the NUS week, given a list of dates that are blocked.
     * @return the immutable list of all dates.
     */
    private static List<Date> getFreeDatesFromBusyWeekDates(List<Date> blockedDates, Week week) {
        List<Date> fullScheduleForWeek = generateFullScheduleForWeek(week);

        // remove all the dates that the user is busy.
        fullScheduleForWeek.removeAll(blockedDates);
        Collections.sort(fullScheduleForWeek);
        return Collections.unmodifiableList(fullScheduleForWeek);
    }

    /**
     * Generates an immutable completely free schedule. The default is taken to be reading week.
     * @return the immutable list of all free dates for reading week.
     */
    public static List<Date> generateDefaultWeekSchedule() {
        return generateFullScheduleForWeek(new Week("11"));
    }

    /**
     * Returns a full list of dates for the NUS week. Utility method to help getAllFreeDatesOnSchedule.
     * @return a full list of dates for the NUS week.
     */
    public static List<Date> generateFullScheduleForWeek(Week week) {
        List<Date> allWeekDates = new ArrayList<>();
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Mon")));
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Tue")));
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Wed")));
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Thu")));
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Fri")));
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Sat")));
        allWeekDates.addAll(generateFullScheduleForDay(week, new Day("Sun")));

        return allWeekDates;
    }

    /**
     * Returns a list of free dates to meet among all the schedules provided, for a certain NUS week.
     * @return an immutable list of free dates to meet for the NUS week.
     */
    public static List<Date> findFreeDatesAmongSchedulesForWeek(List<UniqueSchedule> schedules, Week week) {
        Set<Date> uniqueBlockedDates = new HashSet<>();
        List<Date> allBlockedDates = new ArrayList<>();

        // retrieving the dates for each week.
        schedules.stream().forEach(schedule ->
                allBlockedDates.addAll(schedule.getBusyDatesForWeek(week)));

        uniqueBlockedDates.addAll(allBlockedDates);
        List<Date> freeDatesForWeek = getFreeDatesFromBusyWeekDates(new ArrayList<>(allBlockedDates), week);

        return Collections.unmodifiableList(freeDatesForWeek);
    }


    /**
     * Returns a full list of dates for the day. The week that the day exists in must also be given.
     * Utility method to help getAllFreeDatesOnSchedule.
     * @return a full list of dates for the day.
     */
    private static List<Date> generateFullScheduleForDay(Week week, Day day) {
        // loop through the day.
        List<Date> dayDateList = new ArrayList<>();
        for (int hour = 6; hour <= 23; hour++) {
            for (int minute = 0; minute <= 59; minute += 30) {
                DecimalFormat formatter = new DecimalFormat("00");
                String hourFormatted = formatter.format(hour);
                String minuteFormatted = formatter.format(minute);
                Time time = new Time(hourFormatted + minuteFormatted);
                Date toAdd = new Date(week, day, time);
                dayDateList.add(toAdd);
            }
        }

        return dayDateList;
    }

    /**
     * Returns true if there aren't any busy dates stored thus far.
     */
    public boolean isEmpty() {
        return internalSchedule.values()
                .stream()
                .map(lst -> lst.isEmpty())
                .anyMatch(test -> test == false);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Timetable:\n");

        this.internalSchedule.forEach((key, dateList) ->
                dateList.forEach(builder::append));

        return builder.toString();
    }

}
