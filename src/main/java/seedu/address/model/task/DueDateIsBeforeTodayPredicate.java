package seedu.address.model.task;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Task}'s {@code DueDate} is before the end of today.
 */
public class DueDateIsBeforeTodayPredicate implements Predicate<Task> {

    @Override
    public boolean test(Task person) {
        /** Get today's date, 2359 */
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return person.getDueDate().valueDate.before(date.getTime());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDateIsBeforeTodayPredicate; // instanceof handles nulls

    }

}
