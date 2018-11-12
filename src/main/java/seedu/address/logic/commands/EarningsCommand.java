package seedu.address.logic.commands;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Fees;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Finds and lists the tuition fee of a person in address book
 */
public class EarningsCommand extends Command {

    public static final String COMMAND_WORD = "earnings";

    public static final String MESSAGE_SUCCESS = "You've earned a total of $%.2f between %s and %s";

    public static final String MESSAGE_USEAGE_WARNING = "\nThe date(s) that you have entered is before the current"
            + " system date.\n"
            + "Please note that the given figure will be inaccurate should there been any prior changes to"
            + " any students' time slot.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the total tuition fees earned "
            + "between a range of date, inclusive of the beginning date and ending date.\n"
            + "The dates should be in the format \"ddmm\", separated by a whitespace in between.\n"
            + "Example: " + COMMAND_WORD + " 0305 1911 ";

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Contains an array which consists of the number of days of a week between the given range of date.
     * Array begins from monday at index 0, through sunday at index 6.
     * i.e. daysOfWeek[0] stores number of Mondays between startDate and endDate,
     * daysOfWeek[1] stores number of Tuesdays between startDate and endDate, and so on.
     */
    private int[] daysOfWeek;

    public EarningsCommand(LocalDate inputStartDate, LocalDate inputEndDate) {
        requireNonNull(inputStartDate);
        requireNonNull(inputEndDate);

        assert(!inputStartDate.isAfter(inputEndDate));

        startDate = inputStartDate;
        endDate = inputEndDate;
        daysOfWeek = setDaysOfWeek();

    }

    /**
     * Returns the number of a day of a week between the given range of dates.
     * For example, calculateNumberOfDaysOfWeek(Thursday) returns the number
     * of Thursdays between startDate and endDate.
     */
    private int calculateNumberOfDaysOfWeek (DayOfWeek dayOfWeek) {
        LocalDate dateIterator = startDate.with(nextOrSame(dayOfWeek));
        if (dateIterator.isAfter(endDate)) {
            return 0;
        }

        int numberOfDayOfWeek = 1;

        while (dateIterator.isBefore(endDate)) {
            dateIterator = dateIterator.with(next(dayOfWeek));
            if (dateIterator.isBefore(endDate) || dateIterator.isEqual(endDate)) {
                numberOfDayOfWeek++;
            }
        }

        return numberOfDayOfWeek;
    }

    /**
     * Initializes daysOfWeek array with the number of days of a week between startDate and endDate.
     */
    private int[] setDaysOfWeek() {
        int[] daysOfWeek = new int[7];

        for (int i = 0; i < 6; i++) {
            daysOfWeek[i] = calculateNumberOfDaysOfWeek(DayOfWeek.of(i + 1));
        }

        return daysOfWeek;
    }

    /**
     * Converts day of week from student into DayOfWeek enum in java.time.DayOfWeek
     */
    private DayOfWeek convertDayOfWeek(Time.Day studentDay) {
        switch (studentDay) {
        case Sunday:
            return DayOfWeek.SUNDAY;

        case Monday:
            return DayOfWeek.MONDAY;

        case Tuesday:
            return DayOfWeek.TUESDAY;

        case Wednesday:
            return DayOfWeek.WEDNESDAY;

        case Thursday:
            return DayOfWeek.THURSDAY;

        case Friday:
            return DayOfWeek.FRIDAY;

        default:
            return DayOfWeek.SATURDAY;
        }
    }

    /**
     * Returns the tuition fee earned in a day of a week
     * multiplied by the number of day of the week in the range of date
     */
    private double calculateFeesEarnedEachDayOfWeek(ArrayList<Pair<Fees, Time>> timeslots, DayOfWeek day) {
        return daysOfWeek[day.ordinal()] * timeslots.stream()
                .filter(p -> convertDayOfWeek(p.getValue().getDay()).equals(day))
                .mapToDouble(p -> p.getValue().getTuitionHours() * p.getKey().getFeesValue())
                .sum();
    }

    /**
     * Returns the sum of tuition fees earned over a range of date
     */
    private double calculateTotalFeesEarned(ArrayList<Pair<Fees, Time>> timeslots) {
        double sumOfFees = 0.00;
        for (DayOfWeek day : DayOfWeek.values()) {
            sumOfFees += calculateFeesEarnedEachDayOfWeek(timeslots, day);
        }
        return sumOfFees;
    }

    /**
     * Returns an ArrayList containing the collated time slots from all students in tutorPal
     */
    public ArrayList<Pair<Fees, Time>> getAllTimeSlots(List<Person> persons) {
        ArrayList<Pair<Fees, Time>> timeSlots = new ArrayList<>();

        for (Person p : persons) {
            if (p.hasGraduated()) {
                continue;
            }
            for (Time t: p.getTime()) {
                timeSlots.add(new Pair<>(p.getFees(), t));
            }
        }
        return timeSlots;
    }

    /**
     * Returns a boolean variable which indicated whether any part of the range of dates is in the past
     */
    public Boolean isRangeOfDateInThePast() {
        return !startDate.isAfter(LocalDate.now()) || !endDate.isAfter(LocalDate.now());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Person> persons = model.getInternalList();
        ArrayList<Pair<Fees, Time>> timeSlotOfAllStudents = getAllTimeSlots(persons);
        double totalFeesEarned = calculateTotalFeesEarned(timeSlotOfAllStudents);

        if (isRangeOfDateInThePast()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS + MESSAGE_USEAGE_WARNING, totalFeesEarned,
                    startDate.toString(), endDate.toString()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, totalFeesEarned,
                startDate.toString(), endDate.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof EarningsCommand)
                && (startDate.equals(((EarningsCommand) other).startDate))
                && (endDate.equals(((EarningsCommand) other).endDate))
                && (Arrays.equals(daysOfWeek, ((EarningsCommand) other).daysOfWeek)));
    }
}
