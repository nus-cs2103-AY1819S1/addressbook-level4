package seedu.address.model.task;

import java.util.Calendar;
import java.util.function.Predicate;

/**
 * Predicate to check whether a task is in the given year/month.
 */
public class TaskInYearMonthPredicate implements Predicate<Task> {
    private int year;
    private int month;

    public TaskInYearMonthPredicate(int year, int month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public boolean test(Task task) {
        return task.getStartDateTime().calendar.get(Calendar.MONTH) == this.month
                && task.getStartDateTime().calendar.get(Calendar.YEAR) == this.year;
    }
}
