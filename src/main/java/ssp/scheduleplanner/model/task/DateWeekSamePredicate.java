package ssp.scheduleplanner.model.task;

import java.util.List;
import java.util.function.Predicate;

import ssp.scheduleplanner.commons.util.StringUtil;

/**
 * Tests that a {@code Task}'s {@code Date} matches the current list of system date.
 */
public class DateWeekSamePredicate implements Predicate<Task> {
    private final List<String> dates;

    public DateWeekSamePredicate(List<String> date) {
        this.dates = date;
    }

    @Override
    public boolean test(Task task) {
        return dates.stream()
                .anyMatch(date -> StringUtil.containsWordIgnoreCase(task.getDate().value, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateWeekSamePredicate // instanceof handles nulls
                && dates.equals(((DateWeekSamePredicate) other).dates)); // state check
    }

}
