package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Task}'s {@code Date} matches the current system date.
 */
public class DateWeekSamePredicate implements Predicate<Task> {
    private final List<String> date;

    public DateWeekSamePredicate(List<String> date) {
        this.date = date;
    }

    @Override
    public boolean test(Task task) {
        //return date.stream()
          //      .anyMatch(date -> equals((task.getDate().value)));
        return date.stream()
                .anyMatch(date -> StringUtil.containsWordIgnoreCase(task.getDate().value, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateWeekSamePredicate // instanceof handles nulls
                && date.equals(((DateWeekSamePredicate) other).date)); // state check
    }

}
