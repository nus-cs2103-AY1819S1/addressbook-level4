package seedu.address.logic.commands;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.ObservableList;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the total tuition fees earned "
            + "between a range of date, inclusive of the beginning date and ending date.\n"
            + "The dates should be in the format \"ddmm\", separated by a whitespace in between.\n"
            + "Example: " + COMMAND_WORD + " 0305 1911 ";

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Contains an array which consists of the number of days of a week between the given range of date.
     * Array begins from monday at index 0, through sunday at index 6.
     */
    private int[] daysOfWeek;

    public EarningsCommand(LocalDate inputStartDate, LocalDate inputEndDate) {
        requireNonNull(inputStartDate);
        requireNonNull(inputEndDate);

        startDate = inputStartDate;
        endDate = inputEndDate;
        daysOfWeek = setDaysOfWeek();

    }

    /**
     * Returns the number of a day of a week between the given
     * range of date
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
     * Sets the daysOfWeek array with each of the number of days of a week given
     * between the range of dates.
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

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        ObservableList<Person> persons = model.getAddressBook().getPersonList();
        ArrayList<Pair<Fees, Time>> timeslotOfAllStudents = new ArrayList<>();

        for (Person p : persons) {
            for (Object t : p.getTime()) {
                timeslotOfAllStudents.add(new Pair<>(p.getFees(), (Time) t));
            }
        }

        double totalFeesEarned = calculateTotalFeesEarned(timeslotOfAllStudents);

        return new CommandResult(String.format(MESSAGE_SUCCESS, totalFeesEarned,
                startDate.toString(), endDate.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof EarningsCommand)
                && (startDate.equals(((EarningsCommand) other).startDate))
                && (endDate.equals(((EarningsCommand) other).endDate)));
    }
}
